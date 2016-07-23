package co.in.mobilepay.view.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Nithish on 30-01-2016.
 */
public class ProductDetailsModel{
    private int itemNo;
    private String description;
    private int quantity;
    private String amount;
    private String totalAmount;
    private float rating = 0;

    public ProductDetailsModel(){

    }

  /*  public ProductDetailsModel(Parcel parcel){
this.itemNo = parcel.readInt();
        this.description = parcel.readString();
        this.quantity = parcel.readInt();
        this.amount = parcel.readString();
        this.rating = parcel.readInt();
    }*/

    public int getItemNo() {
        return itemNo;
    }

    public void setItemNo(int itemNo) {
        this.itemNo = itemNo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getTotalAmount() {
        return amount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    /* @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(itemNo);
        dest.writeString(description);
        dest.writeInt(quantity);
        dest.writeString(amount);
        dest.writeInt(rating);
    }

    // Method to recreate a Question from a Parcel
    public static Creator<ProductDetailsModel> CREATOR = new Creator<ProductDetailsModel>() {

        @Override
        public ProductDetailsModel createFromParcel(Parcel source) {
            return new ProductDetailsModel(source);
        }

        @Override
        public ProductDetailsModel[] newArray(int size) {
            return new ProductDetailsModel[size];
        }

    };*/
}
