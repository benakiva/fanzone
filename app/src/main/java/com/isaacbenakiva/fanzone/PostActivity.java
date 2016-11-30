package com.isaacbenakiva.fanzone;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.Search;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.services.StatusesService;

import retrofit2.Call;

public class PostActivity extends AppCompatActivity implements PostFragment.OnPostClickListener {
    private TwitterLoginButton mLoginButton;
    private TwitterApiClient mApiClient = null;
    private StatusesService mStatusService = null;
    private PostFragment mFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        mLoginButton = (TwitterLoginButton) findViewById(R.id.login_button);

        final TwitterSession activeSession = TwitterCore.getInstance()
                .getSessionManager().getActiveSession();

        if (activeSession != null) {
            mLoginButton.setVisibility(View.GONE);
            mApiClient = TwitterCore.getInstance().getApiClient();
            mStatusService = mApiClient.getStatusesService();

            mFragment = new PostFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frag_container, mFragment).commit();
        } else {
            mLoginButton.setVisibility(View.VISIBLE);

            if (mFragment != null) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.remove(mFragment);
            }
        }

        mLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls
                TwitterAuthToken authToken = result.data.getAuthToken();

                // Save these for later authentication , if required.
                String token = authToken.token;
                String secret = authToken.secret;
            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result to the login button.
        mLoginButton.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onPostClick(String text) {
        if (!TextUtils.isEmpty(text) && mStatusService != null) {
            Call<Tweet> call = mStatusService.update(text, null, null, null, null, null, null,
                    null, null);

            call.enqueue(new Callback<Tweet>() {
                @Override
                public void success(Result<Tweet> result) {
                    Toast.makeText(PostActivity.this, getString(R.string.post_ok),
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void failure(TwitterException exception) {
                    Toast.makeText(PostActivity.this, getString(R.string.post_fail),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
