package co.in.mobilepay.enumeration;

public enum PaymentStatus {

	NOT_PAIED(1), PAIED(2), SUCCESS(3), FAILURE(4);

	private int paymentStatusType;

	private PaymentStatus(int paymentStatusType) {
		this.paymentStatusType = paymentStatusType;
	}

	public int getPaymentStatusType() {
		return paymentStatusType;
	}

}
