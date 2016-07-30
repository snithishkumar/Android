package co.in.mobilepay.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import co.in.mobilepay.enumeration.DeliveryConditons;
import co.in.mobilepay.enumeration.DeliveryOptions;

@DatabaseTable(tableName = "DiscardDetails")
public class HomeDeliveryOptionsEntity {
	
	public static final String DELIVERY_OPTIONS_ID = "DeliveryOptionsId";
	public static final String DELIVERY_OPTIONS = "DeliveryOptions";
	public static final String DELIVERY_CONDITIONS = "DeliveryConditions";
	public static final String MIN_AMOUNT = "MinAmount";
	public static final String MAX_DISTANCE = "MaxDistance";
	public static final String AMOUNT = "Amount";
	public static final String PURCHASE_ID =  "PurchaseId";

	@DatabaseField(columnName = DELIVERY_OPTIONS_ID)
	private int deliveryOptionsId;

	@DatabaseField(columnName = PURCHASE_ID,foreign = true,foreignAutoRefresh =  true)
	private PurchaseEntity purchaseEntity;

	@DatabaseField(columnName = DELIVERY_OPTIONS_ID)
	private DeliveryOptions deliveryOptions;

	@DatabaseField(columnName = DELIVERY_OPTIONS_ID)
	private DeliveryConditons deliveryConditons;
	@DatabaseField(columnName = DELIVERY_OPTIONS_ID)
	private float minAmount;
	@DatabaseField(columnName = DELIVERY_OPTIONS_ID)
	private float maxDistance;
	@DatabaseField(columnName = DELIVERY_OPTIONS_ID)
	private float amount;

	public int getDeliveryOptionsId() {
		return deliveryOptionsId;
	}

	public void setDeliveryOptionsId(int deliveryOptionsId) {
		this.deliveryOptionsId = deliveryOptionsId;
	}


	public DeliveryOptions getDeliveryOptions() {
		return deliveryOptions;
	}

	public void setDeliveryOptions(DeliveryOptions deliveryOptions) {
		this.deliveryOptions = deliveryOptions;
	}

	public DeliveryConditons getDeliveryConditons() {
		return deliveryConditons;
	}

	public void setDeliveryConditons(DeliveryConditons deliveryConditons) {
		this.deliveryConditons = deliveryConditons;
	}

	public float getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(float minAmount) {
		this.minAmount = minAmount;
	}

	public float getMaxDistance() {
		return maxDistance;
	}

	public void setMaxDistance(float maxDistance) {
		this.maxDistance = maxDistance;
	}

	public float getAmount() {
		return amount;
	}

	public void setAmount(float amount) {
		this.amount = amount;
	}

	public PurchaseEntity getPurchaseEntity() {
		return purchaseEntity;
	}

	public void setPurchaseEntity(PurchaseEntity purchaseEntity) {
		this.purchaseEntity = purchaseEntity;
	}

	@Override
	public String toString() {
		return "HomeDeliveryOptionsEntity{" +
				"deliveryOptionsId=" + deliveryOptionsId +
				", purchaseEntity=" + purchaseEntity +
				", deliveryOptions=" + deliveryOptions +
				", deliveryConditons=" + deliveryConditons +
				", minAmount=" + minAmount +
				", maxDistance=" + maxDistance +
				", amount=" + amount +
				'}';
	}
}
