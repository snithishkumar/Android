package co.in.mobilepay.enumeration;
public enum CardType {
	MASTER(0), VISA(1);
	
	private int cardType;
	
	private CardType(int cardType){
		this.cardType = cardType;
	}
	
	public int getCardType(){
		return  cardType;
	}

}
