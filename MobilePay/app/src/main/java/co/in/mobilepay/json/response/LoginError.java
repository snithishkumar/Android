package co.in.mobilepay.json.response;

/**
 * Created by Nithish on 13-03-2016.
 */
public class LoginError {
    private int statusCode;

    public LoginError(int statusCode){
        this.statusCode  = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
