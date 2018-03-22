package com.defensorisveritatis.poloik.copacatolica2018.news;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.defensorisveritatis.poloik.copacatolica2018.R;
import com.defensorisveritatis.poloik.copacatolica2018.utilities.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class NewsChild extends AppCompatActivity {

    private static final String TAG = "LOG NewsCHILD -> ";

    private String mJsonURL;
    private ImageView mPostHighlightImage;
    private TextView mPostTitle;
    private TextView mPostText;
    TextView mErrorMessage;
    ProgressBar mProgessBar;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_child);

        mPostHighlightImage = (ImageView) findViewById(R.id.iv_post_image);
        mPostTitle = (TextView) findViewById(R.id.tv_post_title);
        mPostText = (TextView) findViewById(R.id.tv_post_text);
        mErrorMessage = (TextView) findViewById(R.id.tv_error_message);
        mProgessBar = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
                mJsonURL = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);

                //ASYNC to get JSON
                URL jsonDataURL = NetworkUtils.buildUrl(mJsonURL);
                String jsonSearchResults = null;
                new NewsChild.JsonQueryTask().execute(jsonDataURL);
            }
        }
    }

    public class JsonQueryTask extends AsyncTask<URL, Void, String> {

        NewsData postData;

        @Override
        protected void onPreExecute() {
            mProgessBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String jsonSearchResults = null;

            try {
                jsonSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
                JSONObject jsonObj = new JSONObject(jsonSearchResults);

                String postContent = jsonObj.getJSONObject("content").getString("rendered");

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    postContent = String.valueOf( Html.fromHtml(String.valueOf(postContent), Html.FROM_HTML_MODE_LEGACY));
                } else {
                    postContent = String.valueOf(Html.fromHtml(String.valueOf(postContent)));
                }

                postData = new NewsData(
                        jsonObj.getInt("id"),
                        jsonObj.getJSONObject("title").getString("rendered"),
                        jsonObj.getJSONObject("excerpt").getString("rendered"),
                        jsonObj.getJSONObject("_embedded").getJSONArray("wp:featuredmedia").getJSONObject(0).getString("source_url"),
                        postContent);





            } catch (IOException e){
                e.printStackTrace();
            } catch (JSONException e) {
                System.out.println("ERROR: " + e);
            }
            return jsonSearchResults;
        }

        @Override
        protected void onPostExecute(String s) {
            mProgessBar.setVisibility(View.INVISIBLE);

            if (s != null && !s.equals("")){
                mPostTitle.setText(postData.getTitle());
                Glide.with(NewsChild.this).load(postData.getImage_path()).into(mPostHighlightImage);
                mPostText.setText(postData.getText());

                showData();

            }else{
                showErrorMessage();
            }
        }
    }

    private void showData(){
        mErrorMessage.setVisibility(View.INVISIBLE);

        mPostHighlightImage.setVisibility(View.VISIBLE);
        mPostTitle.setVisibility(View.VISIBLE);
        mPostText.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage(){
        mErrorMessage.setVisibility(View.VISIBLE);

        mPostHighlightImage.setVisibility(View.INVISIBLE);
        mPostTitle.setVisibility(View.INVISIBLE);
        mPostText.setVisibility(View.INVISIBLE);
    }
}
