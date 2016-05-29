package co.in.mobilepay.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import co.in.mobilepay.enumeration.DeliveryOptions;
import co.in.mobilepay.enumeration.OrderStatus;
import co.in.mobilepay.enumeration.PaymentStatus;
import co.in.mobilepay.json.response.PurchaseJson;

/**
 * Created by Nithish on 24-01-2016.
 */
@DatabaseTable(tableName = "PurchaseEntity")
public class PurchaseEntity {

    public static final String PURCHASE_ID = "PurchaseId";
    public static final String PURCHASE_GUID = "PurchaseGuid";
    public static final String PURCHASE_DATE_TIME = "PurchaseDateTime";
    public static final String BILL_NUMBER = "BillNumber";
    public static final String PRODUCT_DETAILS = "ProductDetails";
    public static final String AMOUNT_DETAILS = "AmountDetails";
    public static final String PAYMENT_STATUS = "PaymentStatus";
    public static final String IS_EDITABLE = "isEditable";
    public static final String CATEGORY = "category";
    public static final String IS_DELIVERABLE = "isDeliverable";
    public static final String UPDATED_DATE_TIME = "UpdatedDateTime";
    public static final String MERCHANT_ID = MerchantEntity.MERCHANT_ID;
    public static final String USER_ID = UserEntity.USER_ID;
    public static final String SERVER_DATE_TIME = "ServerDateTime";
    public static final String ORDER_STATUS = "OrderStatus";
    public static final String DELIVERY_OPTIONS = "DeliveryOptions";
    public static final String IS_DISCARD = "IsDiscard";
    public static final String IS_SYNC = "IsSync";
    public static final String TOTAL_AMOUNT = "TotalAmount";
    public static final String ADDRESS_ENTITY = "AddressId";

    @DatabaseField(columnName = PURCHASE_ID,generatedId = true,index = true)
    private int purchaseId;
    @DatabaseField(columnName = PURCHASE_GUID,unique = true)
    private String purchaseGuid;
    @DatabaseField(columnName = PURCHASE_DATE_TIME)
    private long purchaseDateTime;
    @DatabaseField(columnName = BILL_NUMBER)
    private String billNumber;
    @DatabaseField(columnName = PRODUCT_DETAILS)
    private String productDetails;
    @DatabaseField(columnName = AMOUNT_DETAILS)
    private String amountDetails;
    @DatabaseField(columnName = CATEGORY)
    private String category;

    @DatabaseField(columnName = PAYMENT_STATUS)
    private PaymentStatus paymentStatus;

    @DatabaseField(columnName = IS_EDITABLE)
    private boolean isEditable;
    @DatabaseField(columnName = IS_DELIVERABLE)
    private boolean isDeliverable;

    @DatabaseField(columnName = UPDATED_DATE_TIME)
    private long lastModifiedDateTime;
    @DatabaseField(columnName = MERCHANT_ID,foreign = true,foreignAutoRefresh =  true)
    private MerchantEntity merchantEntity;
    @DatabaseField(columnName = SERVER_DATE_TIME)
    private long serverDateTime;

    @DatabaseField(columnName = USER_ID,foreign = true)
    private UserEntity userEntity;
    @DatabaseField(columnName = IS_DISCARD)
    private boolean  isDiscard;
    @DatabaseField(columnName = ORDER_STATUS)
    private OrderStatus orderStatus;
    @DatabaseField(columnName = DELIVERY_OPTIONS)
    private DeliveryOptions deliveryOptions;

    @DatabaseField(columnName = IS_SYNC)
    private boolean isSync = false;

    @DatabaseField(columnName = TOTAL_AMOUNT)
    private String totalAmount;

    @DatabaseField(columnName = ADDRESS_ENTITY,foreign = true,foreignAutoRefresh =  true)
    private AddressEntity addressEntity;


    public PurchaseEntity(){

    }

    public PurchaseEntity(PurchaseJson purchaseJson){
        toClone(purchaseJson);

    }

    public void toClone(PurchaseJson purchaseJson){
        this.productDetails = purchaseJson.getProductDetails();
        this.purchaseGuid = purchaseJson.getPurchaseId();
        this.purchaseDateTime =purchaseJson.getPurchaseDate();
        this.billNumber =purchaseJson.getBillNumber();
        this.amountDetails = purchaseJson.getAmountDetails();
        this.isEditable =  purchaseJson.isEditable();
        this.isDeliverable = purchaseJson.isDelivered();
        this.lastModifiedDateTime = purchaseJson.getLastModifiedDateTime();
        this.category = purchaseJson.getCategory();
        this.serverDateTime = purchaseJson.getServerDateTime();
        this.isDiscard = purchaseJson.isDiscard();
        this.paymentStatus = purchaseJson.getPaymentStatus();
        this.orderStatus = purchaseJson.getOrderStatus();
        this.deliveryOptions = purchaseJson.getDeliveryOptions();
        this.totalAmount = purchaseJson.getTotalAmount();
    }

    public int getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(int purchaseId) {
        this.purchaseId = purchaseId;
    }

    public String getPurchaseGuid() {
        return purchaseGuid;
    }

    public void setPurchaseGuid(String purchaseGuid) {
        this.purchaseGuid = purchaseGuid;
    }

    public long getPurchaseDateTime() {
        return purchaseDateTime;
    }

    public void setPurchaseDateTime(long purchaseDateTime) {
        this.purchaseDateTime = purchaseDateTime;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }



    public boolean isEditable() {
        return isEditable;
    }

    public void setIsEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }

    public boolean isDeliverable() {
        return isDeliverable;
    }

    public void setIsDeliverable(boolean isDeliverable) {
        this.isDeliverable = isDeliverable;
    }

    public long getLastModifiedDateTime() {
        return lastModifiedDateTime;
    }

    public void setLastModifiedDateTime(long lastModifiedDateTime) {
        this.lastModifiedDateTime = lastModifiedDateTime;
    }

    public MerchantEntity getMerchantEntity() {
        return merchantEntity;
    }

    public void setMerchantEntity(MerchantEntity merchantEntity) {
        this.merchantEntity = merchantEntity;
    }

    public long getServerDateTime() {
        return serverDateTime;
    }

    public void setServerDateTime(long serverDateTime) {
        this.serverDateTime = serverDateTime;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }


    public boolean isDiscard() {
        return isDiscard;
    }

    public void setIsDiscard(boolean isDiscard) {
        this.isDiscard = isDiscard;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public DeliveryOptions getDeliveryOptions() {
        return deliveryOptions;
    }

    public void setDeliveryOptions(DeliveryOptions deliveryOptions) {
        this.deliveryOptions = deliveryOptions;
    }

    public boolean isSync() {
        return isSync;
    }

    public void setIsSync(boolean isSync) {
        this.isSync = isSync;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public AddressEntity getAddressEntity() {
        return addressEntity;
    }

    public void setAddressEntity(AddressEntity addressEntity) {
        this.addressEntity = addressEntity;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {
        return "PurchaseEntity{" +
                "purchaseId=" + purchaseId +
                ", purchaseGuid='" + purchaseGuid + '\'' +
                ", purchaseDateTime=" + purchaseDateTime +
                ", billNumber='" + billNumber + '\'' +
                ", productDetails='" + productDetails + '\'' +
                ", amountDetails='" + amountDetails + '\'' +
                ", category='" + category + '\'' +
                ", paymentStatus=" + paymentStatus +
                ", isEditable=" + isEditable +
                ", isDeliverable=" + isDeliverable +
                ", lastModifiedDateTime=" + lastModifiedDateTime +
                ", merchantEntity=" + merchantEntity +
                ", serverDateTime=" + serverDateTime +
                ", userEntity=" + userEntity +
                ", isDiscard=" + isDiscard +
                ", orderStatus='" + orderStatus + '\'' +
                ", deliveryOptions=" + deliveryOptions +
                ", isSync=" + isSync +
                ", totalAmount='" + totalAmount + '\'' +
                ", addressEntity=" + addressEntity +
                '}';
    }
}
