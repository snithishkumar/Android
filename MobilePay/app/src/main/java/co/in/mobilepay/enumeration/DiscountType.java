package co.in.mobilepay.enumeration;

public enum DiscountType {
	AMOUNT(0),PERCENTAGE(1),NONE(3);
	
private int discountType;
	
	DiscountType(int discountType){
		this.discountType = discountType;
	}
	
	public int getDiscountType(){
		return  discountType;
	}
	

}
