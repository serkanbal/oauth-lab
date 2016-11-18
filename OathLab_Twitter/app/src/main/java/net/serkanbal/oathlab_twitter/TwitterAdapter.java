package net.serkanbal.oathlab_twitter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Serkan on 17/11/16.
 */

public class TwitterAdapter extends RecyclerView.Adapter<TwitterViewHolder> {
    List<TwitterObject> mList;

    public TwitterAdapter(List<TwitterObject> list) {
        mList = list;
    }

    @Override
    public TwitterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_layout, parent, false);
        return new TwitterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TwitterViewHolder holder, int position) {
        String tweetContent = mList.get(position).getTweet();
        String tweetTimeStamp = mList.get(position).getTimeStamp();

        holder.mTweets.setText(tweetContent);
        holder.mTimeStamp.setText(tweetTimeStamp);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
