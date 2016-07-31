package co.in.mobilepay.json.response;

import co.in.mobilepay.entity.HomeDeliveryOptionsEntity;
import co.in.mobilepay.enumeration.DeliveryOptions;
import co.in.mobilepay.enumeration.OrderStatus;
import co.in.mobilepay.enumeration.PaymentStatus;

public class PurchaseJson {

	private String purchaseId;
	private long purchaseDate;
	private String billNumber;
	private MerchantJson merchants;
	private UserJson users;
	private String productDetails;
	private String amountDetails;
	private String category;
	private boolean isEditable;
	private long lastModifiedDateTime;
	private long serverDateTime;
	private PaymentStatus paymentStatus;
	private OrderStatus orderStatus;
	//private DeliveryOptions deliveryOptions;
	private DeliveryOptions merchantDeliveryOptions;
	private DeliveryOptions userDeliveryOptions;
	private String totalAmount;
	private AddressJson addressJson;
    private DiscardJson discardJson;
	private CounterDetailsJson counterDetails;

	private HomeDeliveryOptionsEntity homeDeliveryOptions;


	public PurchaseJson(){
		
	}

	public DeliveryOptions getMerchantDeliveryOptions() {
		return merchantDeliveryOptions;
	}

	public void setMerchantDeliveryOptions(DeliveryOptions merchantDeliveryOptions) {
		this.merchantDeliveryOptions = merchantDeliveryOptions;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getPurchaseId() {
		return purchaseId;
	}

	public void setPurchaseId(String purchaseId) {
		this.purchaseId = purchaseId;
	}

	public long getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(long purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getBillNumber() {
		return billNumber;
	}

	public void setBillNumber(String billNumber) {
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean isEditable() {
		return isEditable;
	}

	public void setIsEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}


	public long getLastModifiedDateTime() {
		return lastModifiedDateTime;
	}

	public void setLastModifiedDateTime(long lastModifiedDateTime) {
		this.lastModifiedDateTime = lastModifiedDateTime;
	}

	public long getServerDateTime() {
		return serverDateTime;
	}

	public void setServerDateTime(long serverDateTime) {
		this.serverDateTime = serverDateTime;
	}



	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

/*	public DeliveryOptions getDeliveryOptions() {
		return deliveryOptions;
	}

	public void setDeliveryOptions(DeliveryOptions deliveryOptions) {
		this.deliveryOptions = deliveryOptions;
	}*/

    public DiscardJson getDiscardJson() {
        return discardJson;
    }

    public void setDiscardJson(DiscardJson discardJson) {
        this.discardJson = discardJson;
    }

    public AddressJson getAddressJson() {
        return addressJson;
    }

    public void setAddressJson(AddressJson addressJson) {
        this.addressJson = addressJson;
    }

    public CounterDetailsJson getCounterDetails() {
        return counterDetails;
    }

    public void setCounterDetails(CounterDetailsJson counterDetails) {
        this.counterDetails = counterDetails;
    }

	public HomeDeliveryOptionsEntity getHomeDeliveryOptions() {
		return homeDeliveryOptions;
	}

	public void setHomeDeliveryOptions(HomeDeliveryOptionsEntity homeDeliveryOptions) {
		this.homeDeliveryOptions = homeDeliveryOptions;
	}

	public DeliveryOptions getUserDeliveryOptions() {
		return userDeliveryOptions;
	}

	public void setUserDeliveryOptions(DeliveryOptions userDeliveryOptions) {
		this.userDeliveryOptions = userDeliveryOptions;
	}

	@Override
	public String toString() {
		return "PurchaseJson{" +
				"purchaseId='" + purchaseId + '\'' +
				", purchaseDate=" + purchaseDate +
				", billNumber='" + billNumber + '\'' +
				", merchants=" + merchants +
				", users=" + users +
				", productDetails='" + productDetails + '\'' +
				", amountDetails='" + amountDetails + '\'' +
				", category='" + category + '\'' +
				", isEditable=" + isEditable +
				", lastModifiedDateTime=" + lastModifiedDateTime +
				", serverDateTime=" + serverDateTime +
				", paymentStatus=" + paymentStatus +
				", orderStatus=" + orderStatus +
				", merchantDeliveryOptions=" + merchantDeliveryOptions +
				", totalAmount='" + totalAmount + '\'' +
				", addressJson=" + addressJson +
				", discardJson=" + discardJson +
				", counterDetails=" + counterDetails +
				", homeDeliveryOptions=" + homeDeliveryOptions +
				'}';
	}
}
