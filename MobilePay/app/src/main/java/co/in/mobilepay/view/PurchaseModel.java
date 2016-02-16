package co.in.mobilepay.view;

import co.in.mobilepay.entity.PurchaseEntity;

/**
 * Created by Nithish on 30-01-2016.
 */
public class PurchaseModel {

    private int purchaseId;
    private String name;
    private String area;
    private String contactNumber;
    private String billNumber;
    private String dateTime;
    private String category;
    private String noOfItems;
    private String totalAmount;

    public PurchaseModel(PurchaseEntity purchaseEntity){
        this.purchaseId = purchaseEntity.getPurchaseId();
        this.name = purchaseEntity.getMerchantEntity().getMerchantName();
        this.area = purchaseEntity.getMerchantEntity().getArea();
        this.contactNumber = String.valueOf(purchaseEntity.getMerchantEntity().getMobileNumber());
        this.billNumber = String.valueOf(purchaseEntity.getBillNumber());
        this.category = purchaseEntity.getCategory();

    }

    public PurchaseModel(int purchaseId, String name, String area, String contactNumber, String billNumber, String dateTime, String category, String noOfItems, String totalAmount) {
        this.purchaseId = purchaseId;
        this.name = name;
        this.area = area;
        this.contactNumber = contactNumber;
        this.billNumber = billNumber;
        this.dateTime = dateTime;
        this.category = category;
        this.noOfItems = noOfItems;
        this.totalAmount = totalAmount;
    }

    public int getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNoOfItems() {
        return noOfItems;
    }

    public void setNoOfItems(String noOfItems) {
        this.noOfItems = noOfItems;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "PurchaseModel{" +
                "purchaseId=" + purchaseId +
                ", name='" + name + '\'' +
                ", area='" + area + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", billNumber='" + billNumber + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", category='" + category + '\'' +
                ", noOfItems='" + noOfItems + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                '}';
    }
}
