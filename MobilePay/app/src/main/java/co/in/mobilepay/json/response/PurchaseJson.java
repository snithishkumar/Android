package co.in.mobilepay.json.response;

public class PurchaseJson {

	private int purchaseId;
	private long purchaseDate;
	private int billNumber;
	private MerchantJson merchants;
	private UserJson users;
	private String productDetails;
	private String amountDetails;
	private boolean isEditable;
	private boolean isDelivered;
	
	public PurchaseJson(){
		
	}
	

	public int getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(int purchaseId) {
		this.purchaseId = purchaseId;
	}

	public long getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(long purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public int getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(int billNumber) {
		this.billNumber = billNumber;
	}

	public MerchantJson getMerchants() {
		return merchants;
	}

	public void setMerchants(MerchantJson merchants) {
		this.merchants = merchants;
	}

	public UserJson getUsers() {
		return users;
	}

	public void setUsers(UserJson users) {
		this.users = users;
	}

	public String getProductDetails() {
		return productDetails;
	}

	public void setProductDetails(String productDetails) {
		this.productDetails = productDetails;
	}

	public String getAmountDetails() {
		return amountDetails;
	}

	public void setAmountDetails(String amountDetails) {
		this.amountDetails = amountDetails;
	}

    public boolean isEditable() {
        return isEditable;
    }

    public void setIsEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }

    public boolean isDelivered() {
        return isDelivered;
    }

    public void setIsDelivered(boolean isDelivered) {
        this.isDelivered = isDelivered;
    }

    @Override
    public String toString() {
        return "PurchaseJson{" +
                "purchaseId=" + purchaseId +
                ", purchaseDate=" + purchaseDate +
                ", billNumber=" + billNumber +
                ", merchants=" + merchants +
                ", users=" + users +
                ", productDetails='" + productDetails + '\'' +
                ", amountDetails='" + amountDetails + '\'' +
                ", isEditable=" + isEditable +
                ", isDelivered=" + isDelivered +
                '}';
    }

}
