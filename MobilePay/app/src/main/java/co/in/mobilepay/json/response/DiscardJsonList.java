package co.in.mobilepay.json.response;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nithishkumar on 4/11/2016.
 */
public class DiscardJsonList extends  TokenJson {
    List<DiscardJson> discardJsons = new ArrayList<>();

    public List<DiscardJson> getDiscardJsons() {
        return discardJsons;
    }

    public void setDiscardJsons(List<DiscardJson> discardJsons) {
        this.discardJsons = discardJsons;
    }

    @Override
    public String toString() {
        return "DiscardJsonList{" +
                "discardJsons=" + discardJsons +
                '}';
    }
}
