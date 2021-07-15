package com.mindhub.homebanking.Utils;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Transaction;
import java.util.Set;

public final class TransactionUtils {
    public TransactionUtils() {
    }

    public static double calcularBalance(Set<Transaction> transactions, Account accountOrigin) {
        double balance = accountOrigin.getBalance();
        for (Transaction transaction : transactions) {
            balance += transaction.getAmount();
        }
        return balance;
    }
}
