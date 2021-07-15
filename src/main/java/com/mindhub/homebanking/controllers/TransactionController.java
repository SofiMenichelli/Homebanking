package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.service.ClientService;
import com.mindhub.homebanking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@Transactional
@RequestMapping("/api")
public class TransactionController {
    @Autowired
    TransactionService transactionService;

    @Autowired
    AccountRepository accountRepository;

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

        Account accountOrigin = accountRepository.findByNumber(accOrigin);
        Account accountDestiny = accountRepository.findByNumber(accDestiny);

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
        accountRepository.save(accountOrigin);

        transactionService.saveTransaction(new Transaction(amount,description + " " + accDestiny, LocalDateTime.now(), TransactionType.CREDIT, accountDestiny, accountDestiny.getBalance() + amount));
        accountDestiny.setBalance(accountDestiny.getBalance() + amount);
        accountRepository.save(accountDestiny);

        return new ResponseEntity<>(HttpStatus.CREATED);
        }

}
