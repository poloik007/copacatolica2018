package com.defensorisveritatis.poloik.copacatolica2018.teams;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.defensorisveritatis.poloik.copacatolica2018.R;

import java.util.List;

/**
 * Created by poloi on 04/02/2018.
 */

class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.ViewHolderTeams>{

    private static final String TAG = "LOG TeamsAdapter -> ";
    private Context context;
    private List<TeamData> teamsData_list;

    public TeamsAdapter(Context context, List<TeamData> teamsData_list) {
        this.context = context;
        this.teamsData_list = teamsData_list;
    }

    @Override
    public TeamsAdapter.ViewHolderTeams onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.teams_list_item, parent,false);

        return new TeamsAdapter.ViewHolderTeams(itemView);
    }

    @Override
    public void onBindViewHolder(TeamsAdapter.ViewHolderTeams holder, int position) {

        holder.mTv_team_name.setText(teamsData_list.get(position).getName());
        Glide.with(context).load(teamsData_list.get(position).getImage_path()).into(holder.mIv_team_img);
    }

    @Override
    public int getItemCount() {
        return teamsData_list.size();
    }

    public static class ViewHolderTeams extends RecyclerView.ViewHolder {

        TextView mTv_team_name;
        ImageView mIv_team_img;

        // each data item is just a string in this case
        public TextView mTextView;
        public ViewHolderTeams(View v) {
            super(v);

            mTv_team_name = (TextView) v.findViewById(R.id.tv_team_name);
            mIv_team_img = (ImageView) v.findViewById(R.id.iv_team_image);
        }
    }

}
