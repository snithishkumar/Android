package co.in.mobilepay.json.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nithishkumar on 4/11/2016.
 */
public class DiscardJsonList extends  TokenJson {
    List<DiscardJson> discardJsonList = new ArrayList<>();

    public List<DiscardJson> getDiscardJsonList() {
        return discardJsonList;
    }

    public void setDiscardJsonList(List<DiscardJson> discardJsonList) {
        this.discardJsonList = discardJsonList;
    }

    @Override
    public String toString() {
        return "DiscardJsonList{" +
                "discardJsonList=" + discardJsonList +
                '}';
    }
}
