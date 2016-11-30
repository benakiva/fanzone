package com.isaacbenakiva.fanzone;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.isaacbenakiva.fanzone.adapter.TimelineAdapter;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.models.Search;
import com.twitter.sdk.android.core.services.SearchService;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimelineActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private TimelineAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private TwitterApiClient mApiClient;

    private FloatingActionButton mPostButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        mPostButton = (FloatingActionButton) findViewById(R.id.fab);

        mPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TimelineActivity.this, PostActivity.class));
            }
        });

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mApiClient = TwitterCore.getInstance().getApiClient();

        SearchService muHashTag = mApiClient.getSearchService();

        Call<Search> call = muHashTag.tweets("#mufc", null, null, null, null, null, null, null, null, null);
        call.enqueue(new Callback<Search>() {

            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {
                Search search = response.body();

                // specify an adapter (see also next example)
                mAdapter = new TimelineAdapter(TimelineActivity.this, search.tweets);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<Search> call, Throwable t) {

            }
        });


    }

}
