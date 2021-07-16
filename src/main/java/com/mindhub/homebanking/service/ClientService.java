package com.mindhub.homebanking.service;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.Client;
import java.util.List;
import java.util.Optional;

public interface ClientService {

    List<Client> getClients();
    Optional<Client> getClient(long id);
    void saveClient(Client client);
    Optional<Client> getClientEmail(String email);
    Client getByCards(Card card);
}
