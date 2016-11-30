package com.isaacbenakiva.fanzone.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.isaacbenakiva.fanzone.R;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.core.models.Tweet;

import java.util.List;

/**
 * Created by benakiva on 29/11/2016.
 */

public class TimelineAdapter extends RecyclerView.Adapter<TimelineAdapter.ViewHolder> {

    private List<Tweet> mTweets;
    private Context mContext;

    public TimelineAdapter(Context context, List<Tweet> tweets) {
        mTweets = tweets;
        mContext = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public ImageView mProfileImg;
        public TextView mTitle;
        public TextView mScreenname;
        public TextView mTweetAge;
        public TextView mTweetText;

        public ViewHolder(View v) {
            super(v);

            mProfileImg = (ImageView) v.findViewById(R.id.profile_img);
            mTitle = (TextView) v.findViewById(R.id.name);
            mScreenname = (TextView) v.findViewById(R.id.screen_name);
            mTweetText = (TextView) v.findViewById(R.id.tweet);
            mTweetAge = (TextView) v.findViewById(R.id.tweet_age);
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public TimelineAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.timeline_list_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Tweet tweet = mTweets.get(position);

        Float dimen = mContext.getResources().getDimension(R.dimen.profile_img_size);

        Picasso.with(mContext).load(tweet.user.profileImageUrl)
                .resize(dimen.intValue(), dimen.intValue())
                .into(holder.mProfileImg);

        holder.mTweetText.setText(tweet.text);
        holder.mTitle.setText(tweet.user.name);
        holder.mScreenname.setText(tweet.user.screenName);

        //holder.mTweetAge.setText(Utils.calculateEllapsedTime(mContext, tweet.));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mTweets.size();
    }
}
