package co.in.mobilepay.view.model;

import java.util.ArrayList;
import java.util.List;

import co.in.mobilepay.entity.PurchaseEntity;

/**
 * Created by Nithish on 30-01-2016.
 */
public class PurchaseDetailsModel {
    private int purchaseId;
    private String merchantName;
    private String merchantAddress;
    private String merchantArea;
    private String mobileNumber;
    private String contactNumber;
    private List<ProductDetailsModel> productDetailsModelList = new ArrayList<>();
    private String productAmount;
    private String taxAmount;
    private String totalAmount;

    public PurchaseDetailsModel(PurchaseEntity purchaseEntity){
        this.purchaseId = purchaseEntity.getPurchaseId();
        this.merchantName = purchaseEntity.getMerchantEntity().getMerchantName();
        this.merchantAddress = purchaseEntity.getMerchantEntity().getMerchantAddress();
        this.merchantArea = purchaseEntity.getMerchantEntity().getArea();
        this.mobileNumber = String.valueOf(purchaseEntity.getMerchantEntity().getMobileNumber());
        this.contactNumber = String.valueOf(purchaseEntity.getMerchantEntity().getLandLineNumber());


    }

    public int getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public String getMerchantAddress() {
        return merchantAddress;
    }

    public void setMerchantAddress(String merchantAddress) {
        this.merchantAddress = merchantAddress;
    }

    public String getMerchantArea() {
        return merchantArea;
    }

    public void setMerchantArea(String merchantArea) {
        this.merchantArea = merchantArea;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public List<ProductDetailsModel> getProductDetailsModelList() {
        return productDetailsModelList;
    }

    public void setProductDetailsModelList(List<ProductDetailsModel> productDetailsModelList) {
        this.productDetailsModelList = productDetailsModelList;
    }

    public String getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(String productAmount) {
        this.productAmount = productAmount;
    }

    public String getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(String taxAmount) {
        this.taxAmount = taxAmount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
