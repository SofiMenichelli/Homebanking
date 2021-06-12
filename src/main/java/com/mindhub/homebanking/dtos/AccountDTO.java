package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import java.time.LocalDateTime;

public class AccountDTO {
    private long id;
    private String number;
    private LocalDateTime creationDate;
    private double balance;
    private String typeOfAccount;
    private String abbreviationAccount;

    public AccountDTO() {
    }
    public AccountDTO(Account account) {
        this.id = account.getId();
        this.number = account.getNumber ();
        this.creationDate = account.getCreationDate ();
        this.balance = account.getBalance ();
        this.typeOfAccount = account.getTypeOfAccount();
        this.abbreviationAccount = account.getAbbreviationAccount();
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getNumber() { return number; }

    public void setNumber(String number) { this.number = number; }

    public LocalDateTime getCreationDate() { return creationDate; }

    public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }

    public double getBalance() { return balance; }

    public void setBalance(double balance) { this.balance = balance; }

    public String getTypeOfAccount() { return typeOfAccount; }

    public void setTypeOfAccount(String typeOfAccount) { this.typeOfAccount = typeOfAccount; }

    public String getAbbreviationAccount() { return abbreviationAccount; }

    public void setAbbreviationAccount(String abbreviationAccount) { this.abbreviationAccount = abbreviationAccount; }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", creationDate=" + creationDate +
                ", balance=" + balance +
                ", typeOfAccount='" + typeOfAccount + '\'' +
                ", abbreviationAccount='" + abbreviationAccount + '\'' +
                '}';
    }
}
