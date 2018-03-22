package com.defensorisveritatis.poloik.copacatolica2018.matches;

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
import com.defensorisveritatis.poloik.copacatolica2018.matches.MatchesData;
import com.defensorisveritatis.poloik.copacatolica2018.matches.MatchesAdapter;
import com.defensorisveritatis.poloik.copacatolica2018.matches.MatchesFragment;
import com.defensorisveritatis.poloik.copacatolica2018.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MatchesFragment extends Fragment implements MatchesAdapter.ListItemClickListener {

    public String teamId;

    public static final String TAG = "LOG MATCHES Fragment -> ";

    final static String MATCHES_JSON_URL = "http://madrid.copacatolica.com/wp-json/wp/v2/tb_match";
    private String TEAM_JSON_URL = "http://madrid.copacatolica.com/wp-json/wp/v2/tb_club/" + teamId + "?_embed\"?_embed";

    CardView mResultJson;
    TextView mErrorMessage;
    ProgressBar mProgessBar;

    private MatchesAdapter adapter;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private List<MatchesData> matchesData_list;
    private Context context;


    public MatchesFragment() {
        // Required empty public constructor
    }
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.matches_fragment, container, false);

        mResultJson = (CardView) view.findViewById(R.id.cv_matches_card);
        mErrorMessage = (TextView) view.findViewById(R.id.tv_error_message);
        mProgessBar = (ProgressBar) view.findViewById(R.id.pb_loading_indicator);

        //Recycle View
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_matches);
        matchesData_list = new ArrayList<>();

        //Get JSON Data
        URL jsonDataURL = NetworkUtils.buildUrl(MATCHES_JSON_URL);
        String jsonSearchResults = null;
        new MatchesFragment.JsonQueryTask().execute(jsonDataURL);

        //Grid
        gridLayoutManager = new GridLayoutManager(getContext(), 1);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new MatchesAdapter(getContext(), matchesData_list, this);

        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {


            // IF IT IS THE LAST ITEAM ON THE RECYCLER VIEW

//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//
//                if (gridLayoutManager.findLastCompletelyVisibleItemPosition() == newsData_list.size() - 1) {
//                    currentPage++;
//                    URL jsonDataURL = NetworkUtils.buildUrl(NEWS_JSON_URL.concat("&page=" + String.valueOf(currentPage)));
//                    new NewsFragment.JsonQueryTask().execute(jsonDataURL);
//                }
//
//            }
        });
        return view;
    }

    @Override
    public void onListItemClick(int clickedPosition) {
        context = getContext();

//        //Creating url
//        String postId = String.valueOf(matchesData_list.get(clickedPosition).getId());
//        String postUrl = MATCHES_JSON_URL + postId + "?_embed";
//
//        //Intent
//        Class destinationClass = NewsChild.class;
//        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
//        intentToStartDetailActivity.putExtra(Intent.EXTRA_TEXT, postUrl);
//        startActivity(intentToStartDetailActivity);
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

                    MatchesData data = new MatchesData(
                            newsObj.getInt("id"),
                            newsObj.getJSONArray("tb_comp").getString(0),
                            newsObj.getString("tb_home_goals"),
                            newsObj.getString("tb_away_goals"),
                            newsObj.getString("tb_home_club"),
                            newsObj.getString("tb_away_club"),
                            newsObj.getString("date")
                    );

                    matchesData_list.add(data);
                }



            } catch (IOException e){
                e.printStackTrace();
            } catch (JSONException e) {
                System.out.println("----- ERROR WITH CONTENT -----");
                e.printStackTrace();
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

    private void showJsonData(){
        mErrorMessage.setVisibility(View.INVISIBLE);
    }

    private void showErrorMessage(){
        mErrorMessage.setVisibility(View.VISIBLE);
    }

}
