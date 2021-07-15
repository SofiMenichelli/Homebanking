package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.service.ClientService;
import com.mindhub.homebanking.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Transactional
@RequestMapping("/api")
public class LoanController {

    @Autowired
    ClientLoanRepository clientLoanRepository;

    @Autowired
    ClientService clientService;

    @Autowired
    LoanService loanService;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    @PostMapping(value="/loans")
    public ResponseEntity<?> addLoan(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication) {
    //ResponseEntity<?> // ? devuelve algo generico
        Optional<Client> clientOptional = clientService.getClientEmail(authentication.getName());
        if(!clientOptional.isPresent()) {
            return new ResponseEntity<>("El cliente no esta registrado", HttpStatus.UNAUTHORIZED);
        }
        Client client = clientOptional.get();

        Loan loan = loanService.getLoan(loanApplicationDTO.getId());
        Account account = accountRepository.findByNumber(loanApplicationDTO.getAccountDestiny());

        if(loanApplicationDTO.getAmount() == 0 || loanApplicationDTO.getPayments() == 0){
            return new ResponseEntity<>("Ingreso un valor incorrecto", HttpStatus.FORBIDDEN);
        }
        if(loan == null){
            return new ResponseEntity<>("Ese prestamo no existe", HttpStatus.FORBIDDEN);
        }
        if(loan.getMaxAmount() < loanApplicationDTO.getAmount()){
            return new ResponseEntity<>("El monto excede el máximo", HttpStatus.FORBIDDEN);
        }
        if(loan.getPayments().contains(loanApplicationDTO.getPayments()) == false){
            return new ResponseEntity<>("La cantidad de cuotas no es válida", HttpStatus.FORBIDDEN);
        }
        if(client.getAccounts().contains(loanApplicationDTO.getAccountDestiny())){
            return new ResponseEntity<>("La cuenta es incorrecta", HttpStatus.FORBIDDEN);
        }

        ClientLoan clientLoan = new ClientLoan(loanApplicationDTO.getAmount() + (loanApplicationDTO.getAmount() * loan.getFee()), loanApplicationDTO.getPayments(), client, loan);
        clientLoanRepository.save(clientLoan);
        transactionRepository.save(new Transaction(loanApplicationDTO.getAmount(),loan.getName() + " " +  "loan approved", LocalDateTime.now(), TransactionType.CREDIT, account, account.getBalance() + loanApplicationDTO.getAmount()));
        account.setBalance(account.getBalance() + loanApplicationDTO.getAmount());
        accountRepository.save(account);
        return new ResponseEntity<>("Created", HttpStatus.CREATED);
    }

    @GetMapping("/loans")
    public List<LoanDTO> getLoans() {
        return this.loanService.getLoans().stream().map(LoanDTO::new).collect(Collectors.toList());
    }
    @RequestMapping(path = "/loan")
    public ResponseEntity<Object> addLoan (@RequestParam String name,
                                              @RequestParam float maxAmount,
                                              @RequestParam List<Integer> payments,
                                              @RequestParam double fee,
                                              Authentication authentication){

        Optional<Client> clientOptional = clientService.getClientEmail(authentication.getName());
        if(!clientOptional.isPresent()) {
            return new ResponseEntity<>("El cliente no esta registrado", HttpStatus.UNAUTHORIZED);
        }
        Client client = clientOptional.get();

        if(client.getEmail() != "admin@mindhub.com") {
            return new ResponseEntity<>("Usted no tiene permiso", HttpStatus.FORBIDDEN);
        }

        loanService.saveLoan(new Loan(name, maxAmount, payments, fee));
        return new ResponseEntity<>("Se ha creado una nueva opcion de prestamo", HttpStatus.ACCEPTED);
    }

}

