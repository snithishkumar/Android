package co.in.mobilepay.json.response;

import co.in.mobilepay.enumeration.CardType;

/**
 * Created by Nithish on 04-02-2016.
 */
public class CardDetailsJson {
    private String number;
    private String name;
    private String expiryDate;
    private CardType cardType;
    private String cardCvv;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public String getCardCvv() {
        return cardCvv;
    }

    public void setCardCvv(String cardCvv) {
        this.cardCvv = cardCvv;
    }

    @Override
    public String toString() {
        return "CardDetailsJson{" +
                "number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", cardType=" + cardType +
                '}';
    }
}
