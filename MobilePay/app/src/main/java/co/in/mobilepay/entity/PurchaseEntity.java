package co.in.mobilepay.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

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
    public static final String IS_PAYED = "isPayed";
    public static final String IS_EDITABLE = "isEditable";
    public static final String IS_DELIVERABLE = "isDeliverable";
    public static final String UN_MODIFIED_PURCHASE_DATA = "UnModifiedPurchaseData";
    public static final String UN_MODIFIED_AMOUNT_DATA = "UnModifiedAmountDetails";
    public static final String UPDATED_DATE_TIME = "UpdatedDateTime";
    public static final String MERCHANT_ID = MerchantEntity.MERCHANT_ID;
    public static final String USER_ID = UserEntity.USER_ID;

    @DatabaseField(columnName = PURCHASE_ID,generatedId = true,index = true)
    private int purchaseId;
    @DatabaseField(columnName = PURCHASE_GUID,unique = true)
    private String purchaseGuid;
    @DatabaseField(columnName = PURCHASE_DATE_TIME)
    private long purchaseDateTime;
    @DatabaseField(columnName = BILL_NUMBER)
    private int billNumber;
    @DatabaseField(columnName = PRODUCT_DETAILS)
    private String productDetails;
    @DatabaseField(columnName = AMOUNT_DETAILS)
    private String amountDetails;
    @DatabaseField(columnName = IS_PAYED)
    private boolean isPayed;
    @DatabaseField(columnName = IS_EDITABLE)
    private boolean isEditable;
    @DatabaseField(columnName = IS_DELIVERABLE)
    private boolean isDeliverable;
    @DatabaseField(columnName = UN_MODIFIED_PURCHASE_DATA)
    private String unModifiedPurchaseData;
    @DatabaseField(columnName = UN_MODIFIED_AMOUNT_DATA)
    private String unModifiedAmountDetails;
    @DatabaseField(columnName = UPDATED_DATE_TIME)
    private long updatedDateTime;
    @DatabaseField(columnName = MERCHANT_ID,foreign = true)
    private MerchantEntity merchantEntity;

    @DatabaseField(columnName = USER_ID,foreign = true)
    private UserEntity userEntity;


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

    public int getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(int billNumber) {
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

    public boolean isPayed() {
        return isPayed;
    }

    public void setIsPayed(boolean isPayed) {
        this.isPayed = isPayed;
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

    public String getUnModifiedPurchaseData() {
        return unModifiedPurchaseData;
    }

    public void setUnModifiedPurchaseData(String unModifiedPurchaseData) {
        this.unModifiedPurchaseData = unModifiedPurchaseData;
    }

    public String getUnModifiedAmountDetails() {
        return unModifiedAmountDetails;
    }

    public void setUnModifiedAmountDetails(String unModifiedAmountDetails) {
        this.unModifiedAmountDetails = unModifiedAmountDetails;
    }

    public long getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(long updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }

    public MerchantEntity getMerchantEntity() {
        return merchantEntity;
    }

    public void setMerchantEntity(MerchantEntity merchantEntity) {
        this.merchantEntity = merchantEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    @Override
    public String toString() {
        return "PurchaseEntity{" +
                "purchaseId=" + purchaseId +
                ", purchaseGuid='" + purchaseGuid + '\'' +
                ", purchaseDateTime=" + purchaseDateTime +
                ", billNumber=" + billNumber +
                ", productDetails='" + productDetails + '\'' +
                ", amountDetails='" + amountDetails + '\'' +
                ", isPayed=" + isPayed +
                ", isEditable=" + isEditable +
                ", isDeliverable=" + isDeliverable +
                ", unModifiedPurchaseData='" + unModifiedPurchaseData + '\'' +
                ", unModifiedAmountDetails='" + unModifiedAmountDetails + '\'' +
                ", updatedDateTime=" + updatedDateTime +
                '}';
    }
}
