package co.in.mobilepay.json.response;

/**
 * Created by Nithish on 07-03-2016.
 */
public class TokenJson {

    private String serverToken;
    private String accessToken;

    public String getServerToken() {
        return serverToken;
    }

    public void setServerToken(String serverToken) {
        this.serverToken = serverToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String toString() {
        return "TokenJson{" +
                "serverToken='" + serverToken + '\'' +
                ", accessToken='" + accessToken + '\'' +
                '}';
    }
}
