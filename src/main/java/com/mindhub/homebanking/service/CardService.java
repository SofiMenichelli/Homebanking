package com.mindhub.homebanking.service;

import com.mindhub.homebanking.models.Card;
import java.util.List;

public interface CardService {

    Card getCard(long id);
   List<Card> getCards();
    void saveCard(Card card);
    List<Card> getByStatus (boolean status);

}
