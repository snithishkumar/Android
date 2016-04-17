package co.in.mobilepay.json.response;

import co.in.mobilepay.entity.DiscardEntity;
import co.in.mobilepay.entity.PurchaseEntity;
import co.in.mobilepay.enumeration.DiscardBy;

public class DiscardJson extends TokenJson {
	
	private String purchaseGuid;
	private String reason;
	private long createDateTime;
	private String discardUUID;
	private DiscardBy discardBy;

	public DiscardJson(){

	}

	public DiscardJson(DiscardEntity discardEntity,PurchaseEntity  purchaseEntity){
		this.reason = discardEntity.getReason();
		this.purchaseGuid = purchaseEntity.getPurchaseGuid();
		this.createDateTime = discardEntity.getCreatedDateTime();
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

	@Override
	public String toString() {
		return "DiscardJson{" +
				"purchaseGuid='" + purchaseGuid + '\'' +
				", reason='" + reason + '\'' +
				", createDateTime=" + createDateTime +
				'}';
	}
}
