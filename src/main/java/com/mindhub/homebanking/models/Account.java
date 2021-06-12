package com.mindhub.homebanking.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Account{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")

    private long id;
    private String number;
    private LocalDateTime creationDate;
    private double balance;
    private String typeOfAccount;
    private String abbreviationAccount;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Client client;

    public Account() { }

    public Account(String number, LocalDateTime creationDate, double balance, String typeOfAccount, String abbreviationAccount, Client client) {
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
        this.client = client;
        this.typeOfAccount = typeOfAccount;
        this.abbreviationAccount = abbreviationAccount;
    }

    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

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

    public String getTypeOfAccount() { return typeOfAccount; }

    public void setTypeOfAccount(String typeOfAccount) { this.typeOfAccount = typeOfAccount; }

    public String getAbbreviationAccount() { return abbreviationAccount; }

    public void setAbbreviationAccount(String abbreviationAccount) { this.abbreviationAccount = abbreviationAccount; }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", creationDate=" + creationDate +
                ", balance=" + balance +
                ", typeOfAccount='" + typeOfAccount + '\'' +
                ", abbreviationAccount='" + abbreviationAccount + '\'' +
                ", client=" + client +
                '}';
    }
}
