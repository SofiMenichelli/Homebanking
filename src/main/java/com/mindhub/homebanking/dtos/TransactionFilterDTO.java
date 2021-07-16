package com.mindhub.homebanking.dtos;

import java.time.LocalDate;

public class TransactionFilterDTO {
    private LocalDate fromDate;
    private LocalDate toDate;
    private String accountNumber;

    public TransactionFilterDTO() {
    }

    public TransactionFilterDTO(LocalDate fromDate, LocalDate toDate, String accountNumber) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.accountNumber = accountNumber;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
