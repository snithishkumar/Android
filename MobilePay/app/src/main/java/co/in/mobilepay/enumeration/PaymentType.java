package co.in.mobilepay.enumeration;

public enum PaymentType {
	CREDIT(0), DEBIT(1),NETBANK(2);

	private int paymentType;

	PaymentType(int paymentType) {
		this.paymentType = paymentType;
	}

	public int getPaymentType() {
		return paymentType;
	}

}
