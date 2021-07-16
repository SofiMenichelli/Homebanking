package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.PaymentApplicationDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.CardService;
import com.mindhub.homebanking.service.ClientService;
import com.mindhub.homebanking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Set;

@RestController
@Transactional
@RequestMapping("/api")
public class PaymentController {
    @Autowired
    ClientService clientService;

    @Autowired
    TransactionService transactionService;

    @Autowired
    AccountService accountService;

    @Autowired
    CardService cardService;

    @PostMapping(value="/payments")
    public ResponseEntity<?> addPayment(@RequestBody PaymentApplicationDTO paymentApplicationDTO) {

        Card card = cardService.getCardByNumber(paymentApplicationDTO.getNumber());
        Client client = clientService.getByCards(card);
        Account account = client.getAccounts().stream().findAny().get();

        if(paymentApplicationDTO.getAmount() == 0 || paymentApplicationDTO.getCvv() == 0 || paymentApplicationDTO.getNumber().isEmpty() || paymentApplicationDTO.getDescription().isEmpty()) {
            return new ResponseEntity<>("Falta ingresar datos", HttpStatus.FORBIDDEN);
        }
        if (!card.isStatus()){
            return new ResponseEntity<>("La tarjeta esta vencida", HttpStatus.FORBIDDEN);
        }
        if(account.getBalance() < paymentApplicationDTO.getAmount()) {
            return new ResponseEntity<>("El monto es insuficiente", HttpStatus.FORBIDDEN);
        }
        if(card.getCvv() != paymentApplicationDTO.getCvv()) {
            return new ResponseEntity<>("El codigo de seguridad es incorrecto", HttpStatus.FORBIDDEN);
        }
        transactionService.saveTransaction(new Transaction(-paymentApplicationDTO.getAmount(), paymentApplicationDTO.getDescription() + " " + account.getNumber(), LocalDateTime.now(), TransactionType.DEBIT, account, account.getBalance()- paymentApplicationDTO.getAmount()));
        account.setBalance(account.getBalance() - paymentApplicationDTO.getAmount());
        accountService.saveAcc(account);

        return new ResponseEntity<>("Created", HttpStatus.CREATED);
    }
}
