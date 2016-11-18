package net.serkanbal.oathlab_twitter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Serkan on 17/11/16.
 */

public class TwitterViewHolder extends RecyclerView.ViewHolder {
    public TextView mTweets, mTimeStamp;

    public TwitterViewHolder(View itemView) {
        super(itemView);
        mTweets = (TextView) itemView.findViewById(R.id.text_tweets);
        mTimeStamp = (TextView) itemView.findViewById(R.id.text_timestamp);
    }
}
