package co.in.mobilepay.enumeration;

public enum DiscountType {
	AMOUNT(0),PERCENTAGE(1);
	
private int discountType;
	
	private DiscountType(int discountType){
		this.discountType = discountType;
	}
	
	public int getDiscountType(){
		return  discountType;
	}
	

}
