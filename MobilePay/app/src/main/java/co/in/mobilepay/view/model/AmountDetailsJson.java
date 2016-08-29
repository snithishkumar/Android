package co.in.mobilepay.view.model;


import co.in.mobilepay.enumeration.DiscountType;

public class AmountDetailsJson {

    private float taxAmount;
    private double discount;
    private double deliveryAmount;
    private DiscountType discountType;
    private double discountMiniVal;

    public float getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(float taxAmount) {
        this.taxAmount = taxAmount;
    }

    public double getDiscount() {
        return discount > 0 ? discount : 0;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getDeliveryAmount() {
        return deliveryAmount > 0 ? deliveryAmount : 0;
    }

    public void setDeliveryAmount(double deliveryAmount) {
        this.deliveryAmount = deliveryAmount;
    }

    public DiscountType getDiscountType() {
        return discountType;
    }

    public void setDiscountType(DiscountType discountType) {
        this.discountType = discountType;
    }

    public double getDiscountMiniVal() {
        return discountMiniVal > 0 ? discountMiniVal : 0;
    }

    public void setDiscountMiniVal(double discountMiniVal) {
        this.discountMiniVal = discountMiniVal;
    }

    @Override
    public String toString() {
        return "AmountDetailsJson{" +
                "taxAmount=" + taxAmount +
                ", discount=" + discount +
                ", deliveryAmount=" + deliveryAmount +
                ", discountType=" + discountType +
                ", discountMiniVal=" + discountMiniVal +
                '}';
    }
}
