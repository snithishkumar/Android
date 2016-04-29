package co.in.mobilepay.json.response;

import java.util.ArrayList;
import java.util.List;

public class OrderStatusJsonsList {

	private List<LuggageJson> luggageJsons = new ArrayList<>();
	private List<PurchaseJson> purchaseJsons = new ArrayList<>();

	public List<LuggageJson> getLuggageJsons() {
		return luggageJsons;
	}

	public void setLuggageJsons(List<LuggageJson> luggageJsons) {
		this.luggageJsons = luggageJsons;
	}

	public List<PurchaseJson> getPurchaseJsons() {
		return purchaseJsons;
	}

	public void setPurchaseJsons(List<PurchaseJson> purchaseJsons) {
		this.purchaseJsons = purchaseJsons;
	}

	@Override
	public String toString() {
		return "OrderStatusJsonsList [luggageJsons=" + luggageJsons + ", purchaseJsons=" + purchaseJsons + "]";
	}

}
