package com.defensorisveritatis.poloik.copacatolica2018.teams;

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

public class TeamsFragment extends Fragment {

    public static final String TAG = "LOG TeamsFragment -> ";
    final static String TEAMS_JSON_URL = "http://madrid.copacatolica.com/wp-json/wp/v2/tb_club?_embed&per_page=50";

    CardView mResultJson;
    TextView mErrorMessage;
    ProgressBar mProgessBar;

    private TeamsAdapter adapter;
    private RecyclerView recyclerView;
    private GridLayoutManager gridLayoutManager;
    private List<TeamData> teamsData_list;

    public TeamsFragment() {
        // Required empty public constructor
    }
    

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.teams_fragment, container, false);

        super.onCreate(savedInstanceState);

        mResultJson = (CardView) view.findViewById(R.id.cv_teams_card);
        mErrorMessage = (TextView) view.findViewById(R.id.tv_error_message);
        mProgessBar = (ProgressBar) view.findViewById(R.id.pb_loading_indicator);

        //Recycle View
        recyclerView = (RecyclerView)  view.findViewById(R.id.rv_cat_teams);
        teamsData_list = new ArrayList<>();

        //Get JSON Data
        URL jsonDataURL = NetworkUtils.buildUrl(TEAMS_JSON_URL);
        String jsonSearchResults = null;
        new JsonQueryTask().execute(jsonDataURL);

        //Grid
        gridLayoutManager = new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(gridLayoutManager);

        adapter = new TeamsAdapter(getContext(), teamsData_list);

        recyclerView.setAdapter(adapter);

//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//
//                if (gridLayoutManager.findLastCompletelyVisibleItemPosition() == newsData_list.size()-1){
//                    currentPage++;
//                    URL jsonDataURL = NetworkUtils.buildUrl(NEWS_JSON_URL.concat("&page="+String.valueOf(currentPage)));
//                    Log.d(TAG, "onScrolled: CURRENTPAGE NUMBER IS " + jsonDataURL);
//                    new JsonQueryTask().execute(jsonDataURL);
//                }
//
//            }
//        });

        // Inflate the layout for this fragment
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
                JSONArray teamArray = new JSONArray(jsonSearchResults);

                for (int i = 0; i < teamArray.length(); i++) {
                    JSONObject teamObj = teamArray.getJSONObject(i);
                    JSONObject image_path = teamObj.getJSONObject("_embedded").getJSONArray("wp:featuredmedia").getJSONObject(0);


                    if (image_path.has("source_url")){
                        TeamData data = new TeamData(
                                teamObj.getInt("id"),
                                teamObj.getJSONObject("title").getString("rendered"),
                                teamObj.getJSONObject("_embedded").getJSONArray("wp:featuredmedia").getJSONObject(0).getString("source_url"));

                        teamsData_list.add(data);
                    }else{
                        TeamData data = new TeamData(
                                teamObj.getInt("id"),
                                teamObj.getJSONObject("title").getString("rendered"),
                                "http://icons.iconarchive.com/icons/icons-land/metro-raster-sport/256/Soccer-Ball-icon.png");

                        teamsData_list.add(data);
                    }
                }



            } catch (IOException e){
                e.printStackTrace();
            } catch (JSONException e) {
                System.out.println("End of content Teams");
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
