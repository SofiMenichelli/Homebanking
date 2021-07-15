package com.mindhub.homebanking.models;

import com.mindhub.homebanking.Utils.CardsUtils;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @Enumerated(EnumType.STRING)
    private CardType cardType;
    @Enumerated(EnumType.STRING)
    private CardColor cardColor;

    private String number;
    private int cvv;
    private LocalDate thruDate;
    private LocalDate fromDate;
    private String cardHolder;
    private boolean status;
    //fetch eager(descarada) --> cuando es algo mas chico(trae todos)
    //fetch lazy(vaga)
    //mapped by en el one, mismo nombre que la variable del many
    //cascadeType.All --> si borro una camada,
    // se borra t odo lo relacionado con ella
    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    //el id va del lado del many
    //
    private Client client;

    public Card() {
    }

    public Card(CardType cardType, CardColor cardColor, Client client) {
        this.cardType = cardType;
        this.cardColor = cardColor;
        this.number = CardsUtils.cardNumber();
        this.cvv = CardsUtils.cvvNumber();
        this.fromDate = LocalDate.now();
        this.thruDate = this.fromDate.plusYears(5);
        this.cardHolder = client.getFirstName() + " " + client.getLastName();
        this.client = client;
        this.status = true;
    }

    public long getId() { return id; }

    public CardType getCardType() { return cardType; }

    public void setCardType(CardType cardType) { this.cardType = cardType; }

    public CardColor getCardColor() { return cardColor; }

    public void setCardColor(CardColor cardColor) { this.cardColor = cardColor; }

    public String getNumber() { return number; }

    public void setNumber(String number) { this.number = number; }

    public int getCvv() { return cvv; }

    public void setCvv(int cvv) { this.cvv = cvv; }

    public LocalDate getThruDate() { return thruDate; }

    public void setThruDate(LocalDate thruDate) { this.thruDate = thruDate; }

    public LocalDate getFromDate() { return fromDate; }

    public void setFromDate(LocalDate fromDate) { this.fromDate = fromDate; }

    public String getCardHolder() { return cardHolder; }

    public void setCardHolder(String cardHolder) { this.cardHolder = cardHolder; }

    public Client getClient() { return client; }

    public void setClient(Client client) { this.client = client; }

    public boolean isStatus() { return status; }

    public void setStatus(boolean status) { this.status = status; }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", cardType=" + cardType +
                ", cardColor=" + cardColor +
                ", number='" + number + '\'' +
                ", cvv=" + cvv +
                ", thruDate=" + thruDate +
                ", fromDate=" + fromDate +
                ", cardHolder='" + cardHolder + '\'' +
                ", status=" + status +
                ", client=" + client +
                '}';
    }
}
