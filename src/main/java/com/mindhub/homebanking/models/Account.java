package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String number;
    private LocalDateTime creationDate;
    private double balance;
    private AccountType typeOfAccount;
    private boolean status;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    //el id del 1 pasa al muchos
    //la relacion la mapea la propiedad que tienen en el muchos
    @OneToMany (mappedBy="account", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Transaction> transactions = new HashSet<>();

    public Account() { }

    public Account(String number, LocalDateTime creationDate, double balance, AccountType typeOfAccount, Client client) {
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
        this.typeOfAccount = typeOfAccount;
        this.client = client;
        this.status = true;
    }

    public long getId() { return id; }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public double getBalance() { return balance; }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Client getClient() { return client; }

    public AccountType getTypeOfAccount() { return typeOfAccount; }

    public void setTypeOfAccount(AccountType typeOfAccount) { this.typeOfAccount = typeOfAccount; }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

    public boolean isStatus() { return status; }

    public void setStatus(boolean status) { this.status = status; }

}
