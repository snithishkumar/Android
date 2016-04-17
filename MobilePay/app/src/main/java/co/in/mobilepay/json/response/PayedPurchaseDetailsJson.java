package co.in.mobilepay.json.response;


import co.in.mobilepay.entity.PurchaseEntity;
import co.in.mobilepay.enumeration.DeliveryOptions;

public class PayedPurchaseDetailsJson {

    private String purchaseId;
    private String productDetails;
    private String amountDetails;
    private DeliveryOptions deliveryOptions;
    private long paymetTime;
    private AddressJson addressJson;
    private String addressGuid;

    public PayedPurchaseDetailsJson() {

    }

    public PayedPurchaseDetailsJson(PurchaseEntity purchaseEntity) {
        this.purchaseId = purchaseEntity.getPurchaseGuid();
        this.productDetails = purchaseEntity.getProductDetails();
        this.amountDetails = purchaseEntity.getAmountDetails();
        this.deliveryOptions = purchaseEntity.getDeliveryOptions();
        this.paymetTime = purchaseEntity.getLastModifiedDateTime();
        if(purchaseEntity.getAddressEntity()  != null){
            if(purchaseEntity.getAddressEntity().isSynced()){
                this.addressGuid = purchaseEntity.getAddressEntity().getAddressUUID();
            }else{
                addressJson = new AddressJson(purchaseEntity.getAddressEntity());
            }
        }

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

    public DeliveryOptions getDeliveryOptions() {
        return deliveryOptions;
    }

    public void setDeliveryOptions(DeliveryOptions deliveryOptions) {
        this.deliveryOptions = deliveryOptions;
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

    @Override
    public String toString() {
        return "PayedPurchaseDetailsJson [purchaseId=" + purchaseId + ", productDetails=" + productDetails
                + ", amountDetails=" + amountDetails + ", deliveryOptions=" + deliveryOptions + ", paymetTime="
                + paymetTime + "]";
    }


}
