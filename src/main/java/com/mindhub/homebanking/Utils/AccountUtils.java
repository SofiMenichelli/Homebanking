package com.mindhub.homebanking.Utils;

public class AccountUtils {
    public AccountUtils() {
    }
    public static String accountNumber() {
        String accountNumber = ("VIN" + (int) ((Math.random() * (99999 - 10000) + 10000)));
        return accountNumber;
    };//create account number random
}
