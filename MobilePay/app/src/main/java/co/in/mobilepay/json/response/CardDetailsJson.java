package co.in.mobilepay.json.response;

/**
 * Created by Nithish on 04-02-2016.
 */
public class CardDetailsJson {
    private int cardId;
    private String cardNumber;
    private String cardGuid;
    private String cardType;
    private String name;
    private String expiryDate;
    private String cvvNumber;

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCardGuid() {
        return cardGuid;
    }

    public void setCardGuid(String cardGuid) {
        this.cardGuid = cardGuid;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
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

    public String getCvvNumber() {
        return cvvNumber;
    }

    public void setCvvNumber(String cvvNumber) {
        this.cvvNumber = cvvNumber;
    }

    @Override
    public String toString() {
        return "CardDetailsJson{" +
                "cardId=" + cardId +
                ", cardNumber='" + cardNumber + '\'' +
                ", cardGuid='" + cardGuid + '\'' +
                ", cardType='" + cardType + '\'' +
                ", name='" + name + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", cvvNumber='" + cvvNumber + '\'' +
                '}';
    }
}
