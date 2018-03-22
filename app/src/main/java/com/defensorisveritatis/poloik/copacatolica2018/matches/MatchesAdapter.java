package com.defensorisveritatis.poloik.copacatolica2018.matches;


import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.defensorisveritatis.poloik.copacatolica2018.R;

import java.util.List;

public class MatchesAdapter extends RecyclerView.Adapter<MatchesAdapter.ViewHolderMatches>{

    private static final String TAG = "LOG MATCHES Adapter -> ";

    public interface ListItemClickListener {
        void onListItemClick(int clickedPosition);
    }

    private Context context;
    private List<MatchesData> matchesData_list;

    private final ListItemClickListener mOnClickListener ;

    public MatchesAdapter(Context context, List<MatchesData> matchesData_list, ListItemClickListener listener) {
        this.context = context;
        this.matchesData_list = matchesData_list;
        mOnClickListener = listener;
    }

    public class ViewHolderMatches extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView mIv_matches_home;
        ImageView mIv_matches_away;

        TextView mTv_matches_goals_home;
        TextView mTv_matches_goals_away;

        TextView mTv_matches_club_home;
        TextView mTv_matches_club_away;


        // each data item is just a string in this case
        public ViewHolderMatches(View v) {
            super(v);
            v.setOnClickListener(this);

//            mIv_matches_home = (ImageView) v.findViewById(R.id.iv_matches_tb_home_image);
//            mIv_matches_away = (ImageView) v.findViewById(R.id.iv_matches_tb_away_image);

            mTv_matches_goals_home = (TextView) v.findViewById(R.id.tv_home_goals);
            mTv_matches_goals_away = (TextView) v.findViewById(R.id.tv_away_goals);

            mTv_matches_club_home = (TextView) v.findViewById(R.id.tv_matches_club_home);
            mTv_matches_club_away = (TextView) v.findViewById(R.id.tv_matches_club_away);
        }

        //ON CLICK EVENT TO GET POSITION AND ID
        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }

    @Override
    public MatchesAdapter.ViewHolderMatches onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.matches_list_item, parent,false);

        return new ViewHolderMatches(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolderMatches holder, int position) {

//HTML TO TEXT
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            newsDesc = String.valueOf( Html.fromHtml(String.valueOf(newsDesc), Html.FROM_HTML_MODE_LEGACY));
//        } else {
//            newsDesc = String.valueOf(Html.fromHtml(String.valueOf(newsDesc)));
//        }

// RETRIVING IMAGE
//      Glide.with(context).load(newsData_list.get(position).getImage_path()).into(holder.mIv_news_cat_img);


        holder.mTv_matches_goals_home.setText(matchesData_list.get(position).getTb_home_goals());
        holder.mTv_matches_goals_away.setText(matchesData_list.get(position).getTb_away_gols());

        holder.mTv_matches_club_home.setText(matchesData_list.get(position).getTb_id_home_club());
        holder.mTv_matches_club_away.setText(matchesData_list.get(position).getTb_id_away_club());

    }

    @Override
    public int getItemCount() {
        return matchesData_list.size();
    }
}
