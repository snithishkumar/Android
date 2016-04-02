package co.in.mobilepay.enumeration;

public enum DeliveryOptions {
	NONE(0), HOME(1), LUGGAGE(2);

	private int deliveryOptions;

	private DeliveryOptions(int deliveryOptions) {
		this.deliveryOptions = deliveryOptions;
	}

	public int getDeliveryOptions() {
		return deliveryOptions;
	}

}
