package com.defensorisveritatis.poloik.copacatolica2018.news;


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

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolderNews>{

    private static final String TAG = "LOG NewsAdapter -> ";

    public interface ListItemClickListener {
        void onListItemClick(int clickedPosition);
    }

    private Context context;
    private List<NewsData> newsData_list;

    private final ListItemClickListener mOnClickListener ;

    public NewsAdapter(Context context, List<NewsData> newsData_list, ListItemClickListener listener) {
        this.context = context;
        this.newsData_list = newsData_list;
        mOnClickListener = listener;
    }

    public class ViewHolderNews extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView mTv_news_cat_title;
        TextView mTv_news_cat_desc;
        ImageView mIv_news_cat_img;

        // each data item is just a string in this case
        public ViewHolderNews(View v) {
            super(v);
            v.setOnClickListener(this);

            mTv_news_cat_title = (TextView) v.findViewById(R.id.tv_news_cat_title);
            mTv_news_cat_desc = (TextView) v.findViewById(R.id.tv_news_cat_desc);
            mIv_news_cat_img = (ImageView) v.findViewById(R.id.iv_news_cat_image);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }

    @Override
    public NewsAdapter.ViewHolderNews onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_list_item, parent,false);

        return new ViewHolderNews(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolderNews holder, int position) {

        String newsDesc = newsData_list.get(position).getDescription();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            newsDesc = String.valueOf( Html.fromHtml(String.valueOf(newsDesc), Html.FROM_HTML_MODE_LEGACY));
        } else {
            newsDesc = String.valueOf(Html.fromHtml(String.valueOf(newsDesc)));
        }

        holder.mTv_news_cat_title.setText(newsData_list.get(position).getTitle());
        holder.mTv_news_cat_desc.setText(newsDesc);
        Glide.with(context).load(newsData_list.get(position).getImage_path()).into(holder.mIv_news_cat_img);

    }

    @Override
    public int getItemCount() {
        return newsData_list.size();
    }
}
