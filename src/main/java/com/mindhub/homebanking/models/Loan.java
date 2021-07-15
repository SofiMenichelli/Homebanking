package com.mindhub.homebanking.models;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    private String name;
    private float maxAmount;
    private double fee;

    @ElementCollection
    private List<Integer> payments =  new ArrayList<Integer>();

    @OneToMany(mappedBy = "loan", fetch=FetchType.EAGER)
    private Set<ClientLoan> clientsLoans = new HashSet<>();

    public Loan() {
    }

    public Loan(String name, float maxAmount, List<Integer> payments, double fee) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;
        this.fee = fee;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(float maxAmount) {
        this.maxAmount = maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }

    public Set<ClientLoan> getClientsLoans() {
        return clientsLoans;
    }

    public void setClientsLoans(Set<ClientLoan> clientsLoans) {
        this.clientsLoans = clientsLoans;
    }

    public List<Client> getClients(){
        return clientsLoans.stream().map(client -> client.getClient()).collect(Collectors.toList());
    }

    public double getFee() { return fee; }

    public void setFee(double fee) { this.fee = fee; }
}
