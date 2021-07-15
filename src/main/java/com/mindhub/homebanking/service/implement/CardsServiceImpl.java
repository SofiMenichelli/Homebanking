package com.mindhub.homebanking.service.implement;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardsServiceImpl implements CardService {

    @Autowired
    CardRepository cardRepository;

    @Override
    public Card getCard(long id) { return cardRepository.findById(id); }

    @Override
    public List<Card> getCards(){ return cardRepository.findAll(); }

    @Override
    public void saveCard(Card card) {
        cardRepository.save(card);
    }

    @Override
    public List<Card> getByStatus(boolean status){
        return cardRepository.findByStatus(status);
    }
}
