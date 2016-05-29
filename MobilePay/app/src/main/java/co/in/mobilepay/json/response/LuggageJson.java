package co.in.mobilepay.json.response;

import co.in.mobilepay.enumeration.OrderStatus;

public class LuggageJson {
	private long serverDateTime;
	private String purchaseGuid;
	private OrderStatus orderStatus;
	private long updatedDateTime;
	private CounterDetailsJson counterDetails;

	public long getServerDateTime() {
		return serverDateTime;
	}

	public void setServerDateTime(long serverDateTime) {
		this.serverDateTime = serverDateTime;
	}

	public String getPurchaseGuid() {
		return purchaseGuid;
	}

	public void setPurchaseGuid(String purchaseGuid) {
		this.purchaseGuid = purchaseGuid;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public long getUpdatedDateTime() {
		return updatedDateTime;
	}

	public void setUpdatedDateTime(long updatedDateTime) {
		this.updatedDateTime = updatedDateTime;
	}

	public CounterDetailsJson getCounterDetails() {
		return counterDetails;
	}

	public void setCounterDetails(CounterDetailsJson counterDetails) {
		this.counterDetails = counterDetails;
	}
}
