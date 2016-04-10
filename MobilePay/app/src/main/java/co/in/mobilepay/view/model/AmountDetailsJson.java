package co.in.mobilepay.view.model;


import co.in.mobilepay.enumeration.DiscountType;

public class AmountDetailsJson{

private float taxAmount;
private double discount;
private DiscountType discountType;
private double discountMiniVal;

		public float getTaxAmount() {
			return taxAmount;
		}

		public void setTaxAmount(float taxAmount) {
			this.taxAmount = taxAmount;
		}

		public double getDiscount() {
			return discount;
		}

		public void setDiscount(double discount) {
			this.discount = discount;
		}

		public DiscountType getDiscountType() {
			return discountType;
		}

		public void setDiscountType(DiscountType discountType) {
			this.discountType = discountType;
		}

		public double getDiscountMiniVal() {
			return discountMiniVal;
		}

		public void setDiscountMiniVal(double discountMiniVal) {
			this.discountMiniVal = discountMiniVal;
		}

		@Override
		public String toString() {
			return "AmountDetailsJson [taxAmount=" + taxAmount + ", discount=" + discount + ", discountType=" + discountType
					+ ", discountMiniVal=" + discountMiniVal + "]";
		}



}
