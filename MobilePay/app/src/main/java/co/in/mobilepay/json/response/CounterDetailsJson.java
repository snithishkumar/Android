package co.in.mobilepay.json.response;

public class CounterDetailsJson {
	private String counterUUID;
	private String counterNumber;
	private String message;
	
	public CounterDetailsJson(){
		
	}
	


	public String getCounterUUID() {
		return counterUUID;
	}

	public void setCounterUUID(String counterUUID) {
		this.counterUUID = counterUUID;
	}

	public String getCounterNumber() {
		return counterNumber;
	}

	public void setCounterNumber(String counterNumber) {
		this.counterNumber = counterNumber;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "CounterDetailsJson [counterUUID=" + counterUUID + ", counterNumber=" + counterNumber + ", message="
				+ message + "]";
	}
	
	

}
