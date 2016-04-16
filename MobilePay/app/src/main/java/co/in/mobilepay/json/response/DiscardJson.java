package co.in.mobilepay.json.response;

import co.in.mobilepay.entity.DiscardEntity;
import co.in.mobilepay.entity.PurchaseEntity;

public class DiscardJson extends TokenJson {
	
	private String purchaseGuid;
	private String reason;
	private long createDateTime;

	public DiscardJson(){

	}

	public DiscardJson(DiscardEntity discardEntity,PurchaseEntity  purchaseEntity){
		this.reason = discardEntity.getReason();
		this.purchaseGuid = purchaseEntity.getPurchaseGuid();
		this.createDateTime = discardEntity.getCreatedDateTime();
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

	@Override
	public String toString() {
		return "DiscardJson{" +
				"purchaseGuid='" + purchaseGuid + '\'' +
				", reason='" + reason + '\'' +
				", createDateTime=" + createDateTime +
				'}';
	}
}
