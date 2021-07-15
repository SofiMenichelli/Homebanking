package com.mindhub.homebanking.dtos;


public class LoanApplicationDTO {
    private long id;
    private float amount;
    private int payments;
    private String accountDestiny;

    public LoanApplicationDTO() {
    }

    public LoanApplicationDTO(long id, float amount, int payments, String accountDestiny) {
        this.id = id;
        this.amount = amount;
        this.payments = payments;
        this.accountDestiny = accountDestiny;
    }

    public long getId() {
        return id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public String getAccountDestiny() {
        return accountDestiny;
    }

    public void setAccountDestiny(String accountDestiny) {
        this.accountDestiny = accountDestiny;
    }
}

