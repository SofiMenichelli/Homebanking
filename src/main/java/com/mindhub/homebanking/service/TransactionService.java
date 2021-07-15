package com.mindhub.homebanking.service;

import com.mindhub.homebanking.models.Transaction;
import java.util.List;

public interface TransactionService {

    List<Transaction> getTransactions();
    void saveTransaction(Transaction transaction);

}
