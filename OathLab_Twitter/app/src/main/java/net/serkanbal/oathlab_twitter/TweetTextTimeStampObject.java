package net.serkanbal.oathlab_twitter;

/**
 * Created by Serkan on 17/11/16.
 */

public class TweetTextTimeStampObject {
    String created_at;
    String text;

    public TweetTextTimeStampObject(String created_at, String text) {
        this.created_at = created_at;
        this.text = text;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
