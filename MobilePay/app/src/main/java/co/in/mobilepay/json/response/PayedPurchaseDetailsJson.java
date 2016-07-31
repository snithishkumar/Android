package co.in.mobilepay.json.response;


import java.util.ArrayList;
import java.util.List;

import co.in.mobilepay.entity.PurchaseEntity;
import co.in.mobilepay.entity.TransactionalDetailsEntity;
import co.in.mobilepay.enumeration.DeliveryOptions;
import co.in.mobilepay.enumeration.OrderStatus;

public class PayedPurchaseDetailsJson {

    private String purchaseId;
    private String productDetails;
    private String amountDetails;
    private DeliveryOptions userDeliveryOptions;
    private long paymetTime;
    private AddressJson addressJson;
    private String addressGuid;
    private String totalAmount;
    private OrderStatus orderStatus;
    private List<TransactionalDetailsEntity> transactions = new ArrayList<>();

    public PayedPurchaseDetailsJson() {

    }

    public PayedPurchaseDetailsJson(PurchaseEntity purchaseEntity) {
        this.purchaseId = purchaseEntity.getPurchaseGuid();
        this.productDetails = purchaseEntity.getProductDetails();
        this.amountDetails = purchaseEntity.getAmountDetails();
        this.userDeliveryOptions = purchaseEntity.getUserDeliveryOptions();
        this.paymetTime = purchaseEntity.getLastModifiedDateTime();
        this.totalAmount = purchaseEntity.getTotalAmount();
        this.orderStatus = purchaseEntity.getOrderStatus();
        if(purchaseEntity.getAddressEntity()  != null){
            if(purchaseEntity.getAddressEntity().isSynced()){
                this.addressGuid = purchaseEntity.getAddressEntity().getAddressUUID();
            }else{
                addressJson = new AddressJson(purchaseEntity.getAddressEntity());
            }
        }

    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }

    public String getAmountDetails() {
        return amountDetails;
    }

    public void setAmountDetails(String amountDetails) {
        this.amountDetails = amountDetails;
    }

    public DeliveryOptions getUserDeliveryOptions() {
        return userDeliveryOptions;
    }

    public void setUserDeliveryOptions(DeliveryOptions userDeliveryOptions) {
        this.userDeliveryOptions = userDeliveryOptions;
    }

    public long getPaymetTime() {
        return paymetTime;
    }

    public void setPaymetTime(long paymetTime) {
        this.paymetTime = paymetTime;
    }

    public AddressJson getAddressJson() {
        return addressJson;
    }

    public void setAddressJson(AddressJson addressJson) {
        this.addressJson = addressJson;
    }

    public String getAddressGuid() {
        return addressGuid;
    }

    public void setAddressGuid(String addressGuid) {
        this.addressGuid = addressGuid;
    }

    public List<TransactionalDetailsEntity> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<TransactionalDetailsEntity> transactions) {
        this.transactions = transactions;
    }

    @Override
    public String toString() {
        return "PayedPurchaseDetailsJson{" +
                "purchaseId='" + purchaseId + '\'' +
                ", productDetails='" + productDetails + '\'' +
                ", amountDetails='" + amountDetails + '\'' +
                ", userDeliveryOptions=" + userDeliveryOptions +
                ", paymetTime=" + paymetTime +
                ", addressJson=" + addressJson +
                ", addressGuid='" + addressGuid + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", transactions=" + transactions +
                '}';
    }
}
