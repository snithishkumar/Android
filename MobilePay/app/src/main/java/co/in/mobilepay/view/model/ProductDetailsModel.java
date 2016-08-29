package co.in.mobilepay.view.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Nithish on 30-01-2016.
 */
public class ProductDetailsModel{
    private int itemNo;
    private String name;
    private int quantity;
    private double unitPrice;
    private double amount;
    private float rating;

    public int getItemNo() {
        return itemNo > 0 ? itemNo : 0;
    }

    public void setItemNo(int itemNo) {
        this.itemNo = itemNo;
    }

    public String getName() {
        return name != null ? name : " ";
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity > 0 ? quantity : 0;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getUnitPrice() {
        return unitPrice > 0 ? unitPrice : 0;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getAmount() {
        return amount > 0 ? amount : 0;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "ProductDetailsModel{" +
                "itemNo=" + itemNo +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", amount=" + amount +
                ", rating=" + rating +
                '}';
    }
}
