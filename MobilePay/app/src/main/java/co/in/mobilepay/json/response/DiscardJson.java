package co.in.mobilepay.json.response;

import java.util.ArrayList;
import java.util.List;

import co.in.mobilepay.entity.DiscardEntity;
import co.in.mobilepay.entity.PurchaseEntity;
import co.in.mobilepay.entity.TransactionalDetailsEntity;
import co.in.mobilepay.enumeration.DiscardBy;

public class DiscardJson {
	
	private String purchaseGuid;
	private String reason;
	private long createDateTime;
	private String discardUUID;
	private DiscardBy discardBy;
	private List<TransactionalDetailsEntity> transactions = new ArrayList<>();

	private String productDetails;
	private String amountDetails;
	private String calculatedAmounts;

	public DiscardJson(){

	}

	public DiscardJson(DiscardEntity discardEntity,PurchaseEntity  purchaseEntity){
		this.reason = discardEntity.getReason();
		this.purchaseGuid = purchaseEntity.getPurchaseGuid();
		this.createDateTime = discardEntity.getCreatedDateTime();
		this.productDetails = purchaseEntity.getProductDetails();
		this.amountDetails = purchaseEntity.getAmountDetails();
		this.calculatedAmounts = purchaseEntity.getCalculatedAmountDetails();
	}


	public DiscardBy getDiscardBy() {
		return discardBy;
	}

	public void setDiscardBy(DiscardBy discardBy) {
		this.discardBy = discardBy;
	}

	public long getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(long createDateTime) {
		this.createDateTime = createDateTime;
	}

	public String getDiscardUUID() {
		return discardUUID;
	}

	public void setDiscardUUID(String discardUUID) {
		this.discardUUID = discardUUID;
	}

	public String getPurchaseGuid() {
		return purchaseGuid;
	}

	public void setPurchaseGuid(String purchaseGuid) {
		this.purchaseGuid = purchaseGuid;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public List<TransactionalDetailsEntity> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<TransactionalDetailsEntity> transactions) {
		this.transactions = transactions;
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

	public String getCalculatedAmounts() {
		return calculatedAmounts;
	}

	public void setCalculatedAmounts(String calculatedAmounts) {
		this.calculatedAmounts = calculatedAmounts;
	}

	@Override
	public String toString() {
		return "DiscardJson{" +
				"purchaseGuid='" + purchaseGuid + '\'' +
				", reason='" + reason + '\'' +
				", createDateTime=" + createDateTime +
				", discardUUID='" + discardUUID + '\'' +
				", discardBy=" + discardBy +
				", transactions=" + transactions +
				", productDetails='" + productDetails + '\'' +
				", amountDetails='" + amountDetails + '\'' +
				", calculatedAmounts='" + calculatedAmounts + '\'' +
				'}';
	}
}
