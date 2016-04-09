package co.in.mobilepay.json.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nithishkumar on 4/8/2016.
 */
public class AddressBookJson extends  TokenJson{
    private List<AddressJson> addressList = new ArrayList<>();

    private Long lastModifiedTime;

    public List<AddressJson> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<AddressJson> addressList) {
        this.addressList = addressList;
    }

    public Long getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Long lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    @Override
    public String toString() {
        return "AddressBookJson{" +
                "addressList=" + addressList +
                '}';
    }
}
