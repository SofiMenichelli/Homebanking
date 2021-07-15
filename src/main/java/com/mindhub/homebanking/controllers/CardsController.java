package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.CardDTO;
import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.service.CardService;
import com.mindhub.homebanking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardsController {
    @Autowired
    CardService cardService;

    @Autowired
    ClientService clientService;

/*    @GetMapping("/cards")
    public List<CardDTO> getCards() {
        return this.cardRepository.findAll().stream().map(card -> new CardDTO(card)).collect(Collectors.toList());
    }*/
    @GetMapping("/cards")
    public List<CardDTO> getCardsValid() {
        return this.cardService.getByStatus(true).stream().map(card -> new CardDTO(card)).collect(Collectors.toList());
    }    //Busco las tarjetas que estan activas

    @PostMapping(path = "/clients/current/cards")
    public ResponseEntity<Object> createCard(

            @RequestParam CardColor color, @RequestParam CardType type,
            Authentication authentication) {

        Optional<Client> clientOptional = clientService.getClientEmail(authentication.getName());

        if(!clientOptional.isPresent()) {
            return new ResponseEntity<>("El cliente no esta registrado", HttpStatus.UNAUTHORIZED);
        }
        Client client = clientOptional.get();

        if(client.getCards().stream().filter(card -> card.getCardType().equals(type.toString())).count() == 3){
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
        }

        cardService.saveCard(new Card(type, color, client));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @RequestMapping(path = "/cards")
    public ResponseEntity<Object> removeCard (@RequestParam long id, Authentication authentication){

        Card card = cardService.getCard(id);
        Optional<Client> clientOptional = clientService.getClientEmail(authentication.getName());
        if(!clientOptional.isPresent()) {
            return new ResponseEntity<>("El cliente no esta registrado", HttpStatus.UNAUTHORIZED);
        }
        Client client = clientOptional.get();

        if(!client.getCards().contains(card)) {
            return new ResponseEntity<>("La tarjeta no existe", HttpStatus.FORBIDDEN);
        }

        //cardRepository.delete(card);
        card.setStatus(false);
        cardService.saveCard(card);
        return new ResponseEntity<>("La tarjeta fue eliminada", HttpStatus.ACCEPTED);
    }
}
