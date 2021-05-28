package com.example.citizens.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.citizens.R;
import com.example.citizens.activity.MainActivity;
import com.example.citizens.activity.WebViewActivity;
import com.example.citizens.viewmodel.NewsViewModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class NewsLabelsRecyclerViewAdapter
        extends RecyclerView.Adapter<NewsLabelsRecyclerViewAdapter.ViewHolder> {

    private List<String> labelsList;
    private NewsLabelClickListener newsLabelClickListener;
    private NewsLabelLongClickListener newsLabelLongClickListener;
    private Context context;

    public NewsLabelsRecyclerViewAdapter(Context context) {
        this.context = context;
        labelsList = new ArrayList<>();
    }

    public String getItem(int id) {
        return labelsList.get(id);
    }

    public void addLabel(String label) {
        if (labelsList.contains(label) || labelsList.size() >= 11) {
            return;
        }
        if (label.equals("+")) {
            labelsList.add(labelsList.size(), label);
        } else {
            if (labelsList.isEmpty()) {
                labelsList.add(0, label);
            } else {
                labelsList.add(labelsList.size() - 1, label);
            }
        }
        if (labelsList.contains("曼城")) {
            removeLabel("曼城");
            labelsList.add(0, "曼城");
        }
    }

    public boolean removeLabel(String label) {
        if (label.equals("+")) {
            return false;
        } else {

            boolean removed = labelsList.remove(label);
            SharedPreferences.Editor editor= context.getSharedPreferences("cache", Context.MODE_PRIVATE).edit();
            editor.putString("news_labels", String.join(",", labelsList));
            editor.apply();
            return removed;
        }
    }

    public List<String> getLabels() {
        return labelsList;
    }

    public void updateLabelsList(List<String> list) {
        // merge and sort old list and new list by index;
        Set<String> set = new HashSet<String>();
        set.addAll(list);
        set.addAll(labelsList);

        labelsList.clear();

        for (String label : set) {
            if (!label.equals("曼城") && !label.equals("+")) {
                labelsList.add(label);
            }
        }

        labelsList.add(0, "曼城");
        labelsList.add(labelsList.size(), "+");

        SharedPreferences.Editor editor= context.getSharedPreferences("cache", Context.MODE_PRIVATE).edit();
        editor.putString("news_labels", String.join(",", labelsList));
        editor.apply();
    }

    @NonNull
    @Override
    public NewsLabelsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_news_label, parent, false);
        NewsLabelsRecyclerViewAdapter.ViewHolder vh = new NewsLabelsRecyclerViewAdapter.ViewHolder(v);
        return vh;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull NewsLabelsRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.setCardView(context, labelsList.get(position));
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return labelsList.size();
    }

    public interface NewsLabelLongClickListener {
        boolean onNewsLabelLongClick(View view, int position);
    }

    public interface NewsLabelClickListener {
        boolean onNewsLabelClick(View view, int position);
    }

    public void setNewsLabelClickListener(NewsLabelClickListener clickListener) {
        this.newsLabelClickListener = clickListener;
    }

    public void setNewsLabelLongClickListener(NewsLabelLongClickListener clickListener) {
        this.newsLabelLongClickListener = clickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnLongClickListener, View.OnClickListener {
        public TextView labelName;
        public CardView card;

        public ViewHolder(View itemView) {
            super(itemView);
            card = (CardView) itemView.findViewById(R.id.card_view_news_label);
            labelName = (TextView) itemView.findViewById(R.id.text_view_news_label);
            card.setOnLongClickListener(this);
            card.setOnClickListener(this);
        }

        public void setCardView(Context context, String label) {
            labelName.setText(label);
        }

        @Override
        public boolean onLongClick(View v) {
            return newsLabelLongClickListener != null && newsLabelLongClickListener.onNewsLabelLongClick(v, getAdapterPosition());
        }

        @Override
        public void onClick(View v) {
            if (newsLabelClickListener != null) {
                newsLabelClickListener.onNewsLabelClick(v, getAdapterPosition());
            }
        }
    }
}
