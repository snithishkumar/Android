package co.in.mobilepay.view.model;


import co.in.mobilepay.enumeration.DiscountType;

public class AmountDetailsJson {

    private float taxAmount;
    private double discountAmount;
    private double deliveryAmount;
    private DiscountType discountType;
    private double minimumAmount;

    public float getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(float taxAmount) {
        this.taxAmount = taxAmount;
    }

    public double getDiscountAmount() {
        return discountAmount > 0 ? discountAmount : 0;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
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

    public double getMinimumAmount() {
        return minimumAmount > 0 ? minimumAmount : 0;
    }

    public void setMinimumAmount(double minimumAmount) {
        this.minimumAmount = minimumAmount;
    }

    @Override
    public String toString() {
        return "AmountDetailsJson{" +
                "taxAmount=" + taxAmount +
                ", discountAmount=" + discountAmount +
                ", deliveryAmount=" + deliveryAmount +
                ", discountType=" + discountType +
                ", minimumAmount=" + minimumAmount +
                '}';
    }
}
