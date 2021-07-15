package com.mindhub.homebanking.service;

import com.mindhub.homebanking.models.Account;
import java.util.List;

public interface AccountService {

    public Account getAccountById(long id);

    void saveAcc(Account account);

    public List<Account> getAccounts();

    public Account getAccByNumber(String number);

    public List<Account> getAccByStatus(boolean status);

}
