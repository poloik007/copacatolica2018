package com.defensorisveritatis.poloik.copacatolica2018.news;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.defensorisveritatis.poloik.copacatolica2018.R;
import com.defensorisveritatis.poloik.copacatolica2018.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NewsFragment extends Fragment implements NewsAdapter.ListItemClickListener {

    public static final String TAG = "LOG NewsFragment -> ";

    final static String NEWS_JSON_URL = "http://madrid.copacatolica.com/wp-json/wp/v2/posts?cat=5&_embed";
    final String NEWS_DETAILS_JSON_URL = "http://madrid.copacatolica.com/wp-json/wp/v2/posts/";

    CardView mResultJson;
    TextView mErrorMessage;
    ProgressBar mProgessBar;

    private NewsAdapter adapter;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private List<NewsData> newsData_list;
    private int currentPage = 1;
    private Context context;


    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.news_fragment, container, false);
        // Inflate the layout for this fragment

        currentPage = 1;

        mResultJson = (CardView) view.findViewById(R.id.cv_news_card);
        mErrorMessage = (TextView) view.findViewById(R.id.tv_error_message);
        mProgessBar = (ProgressBar) view.findViewById(R.id.pb_loading_indicator);

        //Recycle View
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_cat_news);
        newsData_list = new ArrayList<>();

        //Get JSON Data
        URL jsonDataURL = NetworkUtils.buildUrl(NEWS_JSON_URL);
        String jsonSearchResults = null;
        new JsonQueryTask().execute(jsonDataURL);

        //Grid
        gridLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new NewsAdapter(getContext(), newsData_list, this);

        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (gridLayoutManager.findLastCompletelyVisibleItemPosition() == newsData_list.size() - 1) {
                    currentPage++;
                    URL jsonDataURL = NetworkUtils.buildUrl(NEWS_JSON_URL.concat("&page=" + String.valueOf(currentPage)));
                    new JsonQueryTask().execute(jsonDataURL);
                }

            }
        });
        return view;
    }

    private void showJsonData(){
        mErrorMessage.setVisibility(View.INVISIBLE);
//        mResultJson.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage(){
        mErrorMessage.setVisibility(View.VISIBLE);
//        mResultJson.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onListItemClick(int clickedPosition) {
        context = getContext();

        //Creating url
        String postId = String.valueOf(newsData_list.get(clickedPosition).getId());
        String postUrl = NEWS_DETAILS_JSON_URL + postId + "?_embed";

        //Intent
        Class destinationClass = NewsChild.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, postUrl);
        startActivity(intentToStartDetailActivity);
    }

    public class JsonQueryTask extends AsyncTask<URL, Void, String> {

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
                JSONArray newsArray = new JSONArray(jsonSearchResults);

                for (int i = 0; i < newsArray.length(); i++) {
                    JSONObject newsObj = newsArray.getJSONObject(i);

                    NewsData data = new NewsData(
                            newsObj.getInt("id"),
                            newsObj.getJSONObject("title").getString("rendered"),
                            newsObj.getJSONObject("excerpt").getString("rendered"),
                            newsObj.getJSONObject("_embedded").getJSONArray("wp:featuredmedia").getJSONObject(0).getString("source_url"),
                            "Post full text not used here");

                    newsData_list.add(data);
                }



            } catch (IOException e){
                e.printStackTrace();
            } catch (JSONException e) {
                System.out.println("End of content");
            }
            return jsonSearchResults;
        }

        @Override
        protected void onPostExecute(String s) {
            mProgessBar.setVisibility(View.INVISIBLE);

            if (s != null && !s.equals("")){
                showJsonData();
                adapter.notifyDataSetChanged();
            }else{
                showErrorMessage();
            }
        }
    }

}
