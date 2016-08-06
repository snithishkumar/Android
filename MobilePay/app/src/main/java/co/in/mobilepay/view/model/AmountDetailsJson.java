package co.in.mobilepay.view.model;


import co.in.mobilepay.enumeration.DiscountType;

public class AmountDetailsJson{

private float taxAmount;
private String discount;
	private String deliveryAmount;
private DiscountType discountType;
private String discountMiniVal;

		public float getTaxAmount() {
			return taxAmount;
		}

		public void setTaxAmount(float taxAmount) {
			this.taxAmount = taxAmount;
		}

		public String getDiscount() {
			return discount;
		}

		public void setDiscount(String discount) {
			this.discount = discount;
		}

		public DiscountType getDiscountType() {
			return discountType;
		}

		public void setDiscountType(DiscountType discountType) {
			this.discountType = discountType;
		}

		public String getDiscountMiniVal() {
			return discountMiniVal;
		}

		public void setDiscountMiniVal(String discountMiniVal) {
			this.discountMiniVal = discountMiniVal;
		}


	public String getDeliveryAmount() {
		return deliveryAmount;
	}

	public void setDeliveryAmount(String deliveryAmount) {
		this.deliveryAmount = deliveryAmount;
	}

	@Override
	public String toString() {
		return "AmountDetailsJson{" +
				"taxAmount=" + taxAmount +
				", discount='" + discount + '\'' +
				", deliveryAmount='" + deliveryAmount + '\'' +
				", discountType=" + discountType +
				", discountMiniVal='" + discountMiniVal + '\'' +
				'}';
	}
}
