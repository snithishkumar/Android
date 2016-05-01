package co.in.mobilepay.json.response;


import co.in.mobilepay.enumeration.NotificationType;

public class NotificationJson {
	
	private String message;
	private NotificationType notificationType;
	private String purchaseGuid;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public NotificationType getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(NotificationType notificationType) {
		this.notificationType = notificationType;
	}
	public String getPurchaseGuid() {
		return purchaseGuid;
	}
	public void setPurchaseGuid(String purchaseGuid) {
		this.purchaseGuid = purchaseGuid;
	}

	
	
}
