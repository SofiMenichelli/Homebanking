package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    Card findById(long id);
    List<Card> findByStatus (boolean status);
    Card findByNumber(String number);
}
