package com.example.citizens.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.citizens.R;
import com.example.citizens.viewmodel.NewsViewModel;

import java.util.List;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView cover;
        public TextView title;
        public CardView card;

        public ViewHolder(View itemView) {
            super(itemView);
            card = (CardView) itemView.findViewById(R.id.card_view_news);
            cover = (ImageView) itemView.findViewById(R.id.image_view_news_cover);
            title = (TextView) itemView.findViewById(R.id.text_view_news_title);
            card.setOnClickListener(this);
        }

        public void setCardView(NewsViewModel news) {
            title.setText(news.getTitle());
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onItemClick(view, getAdapterPosition());
        }
    }

    private List<NewsViewModel> newsList;
    private ItemClickListener clickListener;

    public NewsRecyclerViewAdapter(List<NewsViewModel> myNewsList) {
        newsList = myNewsList;
    }

    public void setClickListener(ItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public NewsViewModel getItem(int id) {
        return newsList.get(id);
    }

    public List<NewsViewModel> getCurrentData() {
        return newsList;
    }

    public void updateData() {
        NewsViewModel newsViewModel = new NewsViewModel("新闻-" + (newsList.size() + 1), null, "");
        newsList.add(0, newsViewModel);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_news, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull NewsRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.setCardView(newsList.get(position));
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
