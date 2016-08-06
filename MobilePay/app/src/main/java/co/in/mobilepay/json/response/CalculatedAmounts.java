package co.in.mobilepay.json.response;

/**
 * Created by Nithishkumar on 8/6/2016.
 */
public class CalculatedAmounts {

    private double purchasedAmount;
    private double tax;
    private double discount;
    private double delivery;
    private double totalAmount;


    public double getPurchasedAmount() {
        return purchasedAmount;
    }

    public void setPurchasedAmount(double purchasedAmount) {
        this.purchasedAmount = purchasedAmount;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getDelivery() {
        return delivery;
    }

    public void setDelivery(double delivery) {
        this.delivery = delivery;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "CalculatedAmounts{" +
                "purchasedAmount=" + purchasedAmount +
                ", tax=" + tax +
                ", discount=" + discount +
                ", delivery=" + delivery +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
