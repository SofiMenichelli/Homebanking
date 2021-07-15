package com.mindhub.homebanking.service.implement;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public Account getAccountById(long id) {
     if(!accountRepository.findById(id).isEmpty()) {
         Account account = accountRepository.findById(id).get();
         return account;
     }
         return null;
    }

    @Override
    public void saveAcc(Account account) {
        accountRepository.save(account);
    }

    @Override
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account getAccByNumber(String number) { return accountRepository.findByNumber(number); }

    @Override
    public List<Account> getAccByStatus(boolean status) { return accountRepository.findByStatus(status); }
}
