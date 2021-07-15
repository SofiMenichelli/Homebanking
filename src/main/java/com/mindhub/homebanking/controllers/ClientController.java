package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.Utils.AccountUtils;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountService accountService;

    @GetMapping("/clients")
    public List<ClientDTO> getClients() {
        return this.clientService.getClients().stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
    }

    @GetMapping("/clients/{id}")
    public ClientDTO getClient(@PathVariable Long id) {
        return this.clientService.getClient(id).map(client -> new ClientDTO(client)).orElse(null);
        /*Client client = this.clientRepository.findById(id).get();
        if(client != null) {
            ClientDTO clientDTO = new ClientDTO(client);
            return clientDTO;
        }
        return new ClientDTO();*/
    }

    @PostMapping(path = "/clients")
    public ResponseEntity<Object> register(

            @RequestParam String firstName, @RequestParam String lastName,
            @RequestParam String email, @RequestParam String password) {

        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }
        if (!clientService.getClientEmail(email).isEmpty()) {
            return new ResponseEntity<>("Name already in use", HttpStatus.FORBIDDEN);
        }

        Client client = new Client(firstName, lastName, email, passwordEncoder.encode(password));
        clientService.saveClient(client);
        accountService.saveAcc(new Account(AccountUtils.accountNumber(),LocalDateTime.now(), 0, AccountType.cajaDeAhorroEnPesos, client)); //---> para agregar una cuenta en backEnd cuando se crea un cliente.
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/clients/current")
    public ResponseEntity<?> getClient(Authentication authentication) {

        Optional<Client> clientOptional = clientService.getClientEmail(authentication.getName());
        if(!clientOptional.isPresent()){
            return new ResponseEntity<>("Error, no estas autenticado", HttpStatus.UNAUTHORIZED);
        }
        Client client = clientOptional.get();
        Set<Account> account = client.getAccounts().stream().filter(Account::isStatus).collect(Collectors.toSet());
        client.setAccounts(account);
        Set<Card> card = client.getCards().stream().filter(Card::isStatus).collect(Collectors.toSet());
        client.setCards(card);

        return new ResponseEntity<>(new ClientDTO(client),HttpStatus.CREATED);
    }
}


