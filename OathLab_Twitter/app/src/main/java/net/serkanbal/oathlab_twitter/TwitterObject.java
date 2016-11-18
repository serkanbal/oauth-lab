package net.serkanbal.oathlab_twitter;

/**
 * Created by Serkan on 17/11/16.
 */

public class TwitterObject {
    String mTweet, mTimeStamp;

    public TwitterObject(String timeStamp, String tweet) {
        mTimeStamp = timeStamp;
        mTweet = tweet;
    }

    public String getTimeStamp() {
        return mTimeStamp;
    }

    public String getTweet() {
        return mTweet;
    }
}
