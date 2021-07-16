package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.dtos.TransactionFilterDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.ClientService;
import com.mindhub.homebanking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Transactional
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @Autowired
    AccountService accountService;

    @Autowired
    ClientService clientService;

    @PostMapping(path ="/clients/current/accounts/transactions")
    // o@RequestMapping(path ="/clients/current/accounts/transactions", method = RequestMethod.POST)
    public ResponseEntity<Object> createTransaction(
            @RequestParam double amount, @RequestParam String description,
            @RequestParam String accOrigin, @RequestParam String accDestiny,
            Authentication authentication) {

        Optional<Client> clientOptional = clientService.getClientEmail(authentication.getName());
        if(!clientOptional.isPresent()) {
            return new ResponseEntity<>("El cliente no esta registrado", HttpStatus.UNAUTHORIZED);
        }
        Client client = clientOptional.get();

        Account accountOrigin = accountService.getAccByNumber(accOrigin);
        Account accountDestiny = accountService.getAccByNumber(accDestiny);

        if (amount == 0 || description.isEmpty() || accOrigin.isEmpty() || accDestiny.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if(accountOrigin == accountDestiny || accountOrigin.getBalance() < amount) {
            System.out.println(accountOrigin.getBalance());
            return new ResponseEntity<>("La informacion ingresada no es correcta", HttpStatus.FORBIDDEN);
        }
        if(accountOrigin.getBalance() <= 0 || !client.getAccounts().contains(accountOrigin)){
            return new ResponseEntity<>("El monto o la Cuenta son invalidos", HttpStatus.FORBIDDEN);
        }
        if(accountDestiny == null || accountOrigin == null) {
            return new ResponseEntity<>("Falta informacion", HttpStatus.FORBIDDEN);
        }

        transactionService.saveTransaction(new Transaction(-amount,description + " " + accOrigin, LocalDateTime.now(), TransactionType.DEBIT, accountOrigin, accountOrigin.getBalance() - amount));
        accountOrigin.setBalance(accountOrigin.getBalance() - amount);
        accountService.saveAcc(accountOrigin);

        transactionService.saveTransaction(new Transaction(amount,description + " " + accDestiny, LocalDateTime.now(), TransactionType.CREDIT, accountDestiny, accountDestiny.getBalance() + amount));
        accountDestiny.setBalance(accountDestiny.getBalance() + amount);
        accountService.saveAcc(accountDestiny);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/transactions")
    public ResponseEntity<?> ListTransactionsDTO(Authentication authentication, @RequestBody TransactionFilterDTO transactionFilterDTO) {
        Optional<Client> clientOptional = clientService.getClientEmail(authentication.getName());
        if (!clientOptional.isPresent()) {
            return new ResponseEntity<>("Error, no estas autenticado", HttpStatus.UNAUTHORIZED);
        }
        Client client = clientOptional.get();
        Account account = accountService.getAccByNumber(transactionFilterDTO.getAccountNumber());

        if (!client.getAccounts().contains(account)) {
            return new ResponseEntity<>("La cuenta no existe", HttpStatus.FORBIDDEN);
        }
        if (transactionFilterDTO.getToDate().equals((LocalDate.now()))) {
            transactionFilterDTO.setToDate(LocalDate.now().plusDays(1));
        }

        List<LocalDate> listOfDates = transactionFilterDTO.getFromDate().datesUntil(transactionFilterDTO.getToDate()).collect(Collectors.toList());
        List<TransactionDTO> transactionsToDTOS= account.getTransactions().stream().map(TransactionDTO::new).collect(Collectors.toList());
        List<TransactionDTO> transactionDTOList = transactionsToDTOS.stream().filter(e -> listOfDates.contains(e.getDate().toLocalDate())).collect(Collectors.toList());
        return new ResponseEntity<>(transactionDTOList, HttpStatus.CREATED);
    }
}
