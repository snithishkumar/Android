package co.in.mobilepay.enumeration;

public enum DiscardBy {
	
USER(0), MERCHANT(1);
	
	private int discardBy;
	
	DiscardBy(int discardBy){
		this.discardBy = discardBy;
	}
	
	public int getDiscardBy(){
		return  discardBy;
	}

}
