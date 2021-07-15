package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.Utils.AccountUtils;
import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    ClientService clientService;

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts() {
        return this.accountService.getAccounts().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
    }//Busco todas las cuentas

    /*    @GetMapping("/accounts")
    public List<AccountDTO> getAccountsValid() {
        return this.accountRepository.findByStatus(true).stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());
    } //Busco las cuentas activas*/

    @GetMapping("/accounts/{id}" )
    public AccountDTO getAccountById(@PathVariable Long id) {

        if(this.accountService.getAccountById(id) == null) {
            return null;
        }
        Account account = this.accountService.getAccountById(id);
        return new AccountDTO(account);
    }

    @PostMapping(path = "/clients/current/accounts")
    public ResponseEntity<Object> addAcc(
            @RequestParam AccountType accountType,
            Authentication authentication) {

        Optional<Client> clientOptional = clientService.getClientEmail(authentication.getName());
        if(!clientOptional.isPresent()) {
            return new ResponseEntity<>("No esta registrado", HttpStatus.UNAUTHORIZED);
        }

        if(clientOptional.get().getAccounts().stream().filter(Account :: isStatus).collect(Collectors.toSet()).size() == 3) {
            return new ResponseEntity<>("No puedes tener mas cuentas", HttpStatus.FORBIDDEN);
        }
        if(accountType == null) {
            return new ResponseEntity<>("Debe seleccionar un tipo de cuenta", HttpStatus.FORBIDDEN);
        }
        Client client = clientOptional.get();

    accountService.saveAcc(new Account(AccountUtils.accountNumber(),LocalDateTime.now(), 0, accountType, client));
    return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PatchMapping(path = "/accounts")
    public ResponseEntity<Object> removeAcc (@RequestParam long id, Authentication authentication){

        Account account = accountService.getAccountById(id);
        Optional<Client> clientOptional = clientService.getClientEmail(authentication.getName());
        if(!clientOptional.isPresent()) {
            return new ResponseEntity<>("No esta registrado", HttpStatus.UNAUTHORIZED);
        }

        Client client = clientOptional.get();

        if(!client.getAccounts().contains(account)) {
            return new ResponseEntity<>("La Cuenta no existe", HttpStatus.FORBIDDEN);
        }

        //accountRepository.delete(account);
        account.setStatus(false);
        accountService.saveAcc(account);
        return new ResponseEntity<>("La Cuenta fue eliminada", HttpStatus.ACCEPTED);
    }

}
