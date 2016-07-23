package co.in.mobilepay.json.response;

/**
 * Created by Nithish on 23-01-2016.
 */
public class ResponseData {

    private int statusCode;
    private String data;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseData{" +
                "statusCode=" + statusCode +
                ", data='" + data + '\'' +
                '}';
    }
}
