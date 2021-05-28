package com.example.citizens.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.citizens.R;
import com.example.citizens.utils.MySingleton;
import com.example.citizens.utils.NetworkPort;
import com.example.citizens.viewmodel.NewsViewModel;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.ViewHolder> {

    private List<NewsViewModel> newsList;
    private ItemClickListener clickListener;
    private Context context;

    public NewsRecyclerViewAdapter(Context context, List<NewsViewModel> myNewsList) {
        this.context = context;
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

    public void updateNewsList(List<NewsViewModel> newsList, List<String> labelsList) {
        // merge and sort old list and new list by index;
        Set<NewsViewModel> set = new HashSet<NewsViewModel>();
        set.addAll(this.newsList);
        set.addAll(newsList);

//        if (newsList.size() == 0 || list.size() == 0) {
//            newsList.addAll(list);
//        } else {
//            if (!newsList.get(newsList.size() - 1).getDate().equals(list.get(0).getDate())) {
//                newsList.addAll(list);
//            }
//        }

        this.newsList.clear();

        for (NewsViewModel news : set) {
            boolean containLabel = false;
            String labels = news.getLabels();
            for (String label : labelsList) {
                if (labels.contains(label)) {
                    containLabel = true;
                    break;
                }
            }
            if (containLabel) {
                this.newsList.add(news);
            }
        }
//        this.newsList.addAll(set);
        Collections.sort(this.newsList, new Comparator<NewsViewModel>() {
            @Override
            public int compare(NewsViewModel o1, NewsViewModel o2) {
                if (o1 == null || o2 == null) {
                    return 0;
                }
                return o2.getUpdateTime().compareTo(o1.getUpdateTime());
            }
        });
    }

//    public void updateData(SwipyRefreshLayoutDirection direction, Context context) {
//        NetworkPort networkPort = NetworkPort.getInstance();
//        networkPort.getNews(context, this);
////        if (direction == SwipyRefreshLayoutDirection.TOP) {
////            NewsViewModel newsViewModel = new NewsViewModel("新闻-" + (newsList.size() + 1), null, "");
////            newsList.add(0, newsViewModel);
////
////        } else {
////            NewsViewModel newsViewModel = new NewsViewModel("新闻-" + "0", null, "");
////            newsList.add(newsList.size(), newsViewModel);
////        }
//
////        this.notifyDataSetChanged();
//    }

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
        holder.setCardView(context, newsList.get(position));
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public interface ItemClickListener {
        void onNewsItemClick(View view, int position);
    }

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

        public void setCardView(Context context, NewsViewModel news) {
            title.setText(news.getTitle());
//            Glide.with(context);
            Glide.with(context)
                    .load(news.getCoverURL())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(cover);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onNewsItemClick(view, getAdapterPosition());
        }
    }
}
