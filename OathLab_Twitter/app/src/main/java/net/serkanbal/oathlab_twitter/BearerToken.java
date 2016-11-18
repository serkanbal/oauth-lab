package net.serkanbal.oathlab_twitter;

/**
 * Created by Serkan on 17/11/16.
 */

public class BearerToken {
    private String access_token;

    public BearerToken(String bearerToken) {
        this.access_token = bearerToken;
    }

    public String getBearerToken() {
        return access_token;
    }

    public void setBearerToken(String bearerToken) {
        this.access_token = bearerToken;
    }
}
