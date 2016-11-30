package com.isaacbenakiva.fanzone;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class PostFragment extends Fragment implements View.OnClickListener {
    public interface OnPostClickListener {
        void onPostClick(final String text);
    }

    private static final int TWITTER_MAX_TWEET_CHARS = 140;
    private Integer mMaxChar = 140;
    private OnPostClickListener mListener = null;
    private EditText mTweetText;
    private TextView mNumChar;
    private Button mSend;

    public PostFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        PostActivity activity;

        try {
            if (context instanceof PostActivity) {
                activity = (PostActivity) context;

                mListener = (OnPostClickListener) activity;
            }
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnPostClickListener");
        }
    }

    @Override
    public void onDetach() {
        mListener = null;

        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post, container, false);

        mNumChar = (TextView) view.findViewById(R.id.num_character);
        mSend = (Button) view.findViewById(R.id.send_tweet);
        mSend.setOnClickListener(this);
        mSend.setEnabled(false);

        mTweetText = (EditText) view.findViewById(R.id.tweet_text);
        mTweetText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int count, int after) {
                if (!mSend.isEnabled() && after > 0 && after <= TWITTER_MAX_TWEET_CHARS) {
                    mSend.setEnabled(true);
                    mNumChar.setTextColor(Color.BLACK);
                } else if (mSend.isEnabled() && after == 0) {
                    mSend.setEnabled(false);
                    mNumChar.setTextColor(Color.BLACK);
                } else if (mSend.isEnabled() && after > TWITTER_MAX_TWEET_CHARS) {
                    mSend.setEnabled(false);
                    mNumChar.setTextColor(Color.RED);
                } else if (!mSend.isEnabled() && after > TWITTER_MAX_TWEET_CHARS) {
                    mSend.setEnabled(false);
                    mNumChar.setTextColor(Color.RED);
                }

                mMaxChar = TWITTER_MAX_TWEET_CHARS - after;
                mNumChar.setText(mMaxChar.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        return view;
    }

    public void setOnPostClickListener(OnPostClickListener listener) {
        mListener = listener;
    }

    @Override
    public void onClick(View view) {
        if (mListener != null) {
            mListener.onPostClick(mTweetText.getText().toString());
        }
    }
}
