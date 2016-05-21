package co.in.mobilepay.entity;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import co.in.mobilepay.enumeration.DeviceType;
import co.in.mobilepay.enumeration.PaymentStatus;


@DatabaseTable(tableName = "TransactionalDetails")
public class TransactionalDetailsEntity {
	public static final String TRANSACTION_ID = "TransactionId";
	public static final String PAYMENT_DATE = "paymentDate";
	public static final String AMOUNT = "amount";
    public static final String DEVICE_TYPE = "deviceType";
	public static final String TRANSACTION_UUID = "transactionUUID";
	public static final String PURCHASE_ENTITY = "purchaseEntity";
	public static final String IMEI_NUMBER = "imeiNumber";
	public static final String PAYMENT_STATUS = "paymentStatus";
	public static final String REASON = "reason";
    public static final String IS_SYNC = "IsSync";


	@DatabaseField(columnName = TRANSACTION_ID,generatedId = true,index = true)
	private int transactionId;
	@DatabaseField(columnName = PAYMENT_DATE)
	private long paymentDate;
    @DatabaseField(columnName = AMOUNT)
	private double amount;
    @DatabaseField(columnName = TRANSACTION_UUID)
	private String transactionUUID;
    @DatabaseField(columnName = DEVICE_TYPE)
	private DeviceType deviceType;
	@DatabaseField(columnName = PURCHASE_ENTITY,foreign = true,foreignAutoRefresh =  true)
	private PurchaseEntity purchaseEntity;
    @DatabaseField(columnName = IMEI_NUMBER)
	private String imeiNumber;
    @DatabaseField(columnName = PAYMENT_STATUS)
	private PaymentStatus paymentStatus;
    @DatabaseField(columnName = REASON)
	private String reason;
    @DatabaseField(columnName = IS_SYNC)
    private boolean isSync;
	

	public String getTransactionUUID() {
		return transactionUUID;
	}

	public void setTransactionUUID(String transactionUUID) {
		this.transactionUUID = transactionUUID;
	}

	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public long getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(long paymentDate) {
		this.paymentDate = paymentDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public DeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	public PurchaseEntity getPurchaseEntity() {
		return purchaseEntity;
	}

	public void setPurchaseEntity(PurchaseEntity purchaseEntity) {
		this.purchaseEntity = purchaseEntity;
	}

	public String getImeiNumber() {
		return imeiNumber;
	}

	public void setImeiNumber(String imeiNumber) {
		this.imeiNumber = imeiNumber;
	}

	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}


    public boolean isSync() {
        return isSync;
    }

    public void setSync(boolean sync) {
        isSync = sync;
    }

    @Override
	public String toString() {
		return "TransactionalDetailsEntity [transactionId=" + transactionId + ", paymentDate=" + paymentDate
				+ ", amount=" + amount + ", deviceType=" + deviceType + ", purchaseEntity=" + purchaseEntity
				+ ", imeiNumber=" + imeiNumber + ", paymentStatus=" + paymentStatus + ", reason=" + reason + "]";
	}

}
