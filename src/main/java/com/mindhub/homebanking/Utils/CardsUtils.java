package com.mindhub.homebanking.Utils;

public final class CardsUtils {
    private CardsUtils() {
    }
    public static String cardNumber() {
         String cardNumber = (int) ((Math.random() * (9999 - 1000)) + 1000) + " " +
                (int) ((Math.random() * (9999 - 1000)) + 1000) + " " +
                (int) ((Math.random() * (9999 - 1000)) + 1000) + " " +
                (int) ((Math.random() * (9999 - 1000)) + 1000);
         return cardNumber;
    };//create card number random
    public static int cvvNumber() {
        return (int) ((Math.random() * (999 - 100)) + 100);
    }; //create cvv number random
}
