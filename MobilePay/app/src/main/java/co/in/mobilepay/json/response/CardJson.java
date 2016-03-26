package co.in.mobilepay.json.response;


import co.in.mobilepay.enumeration.PaymentType;

public class CardJson extends  TokenJson{

	private String cardGuid;
	private String createdDateTime;
	private CardDetailsJson cardDetails;
	private PaymentType paymentType;
	private boolean isExpanded = false;

	public String getCardGuid() {
		return cardGuid;
	}

	public void setCardGuid(String cardGuid) {
		this.cardGuid = cardGuid;
	}

	public String getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(String createdDateTime) {
		this.createdDateTime = createdDateTime;
	}


	public CardDetailsJson getCardDetails() {
		return cardDetails;
	}

	public void setCardDetails(CardDetailsJson cardDetails) {
		this.cardDetails = cardDetails;
	}
	
	

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public boolean isExpanded() {
		return isExpanded;
	}

	public void setIsExpanded(boolean isExpanded) {
		this.isExpanded = isExpanded;
	}

	@Override
	public String toString() {
		return "CardJson{" +
				"cardGuid='" + cardGuid + '\'' +
				", createdDateTime='" + createdDateTime + '\'' +
				", cardDetails=" + cardDetails +
				", paymentType=" + paymentType +
				", isExpanded=" + isExpanded +
				'}';
	}
}
