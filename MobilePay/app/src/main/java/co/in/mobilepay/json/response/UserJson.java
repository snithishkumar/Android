package co.in.mobilepay.json.response;


public class UserJson extends TokenJson{
	private String name;
	private String mobileNumber;
	
	public UserJson(){
		
	}
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	@Override
	public String toString() {
		return "UserJson [name=" + name + ", mobileNumber=" + mobileNumber + "]";
	}

}
