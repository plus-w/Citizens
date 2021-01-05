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
import com.example.citizens.viewmodel.MatchViewModel;

import java.util.List;

public class MatchRecyclerViewAdapter extends RecyclerView.Adapter<MatchRecyclerViewAdapter.ViewHolder> {

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CardView cardView;

        private ImageView matchTypeImageView;  // league, cup ...
        private ImageView homeTeamLogoImageView;
        private ImageView awayTeamLogoImageView;

        private TextView matchTypeTextView;
        private TextView homeTeamNameTextView;
        private TextView awayTeamNameTextView;
        private TextView homeTeamScoreTextView;
        private TextView awayTeamScoreTextView;
        private TextView matchDateTextView;
        private TextView matchWeekDayTextView;
        private TextView matchTimeTextView;


        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view_match);

            matchTypeImageView = itemView.findViewById(R.id.image_view_match_type);
            homeTeamLogoImageView = itemView.findViewById(R.id.image_view_home_team_logo);
            awayTeamLogoImageView = itemView.findViewById(R.id.image_view_away_team_logo);

            matchTypeTextView = itemView.findViewById(R.id.text_view_match_type);
            homeTeamNameTextView = itemView.findViewById(R.id.text_view_home_team_name);
            awayTeamNameTextView = itemView.findViewById(R.id.text_view_away_team_name);
            homeTeamScoreTextView = itemView.findViewById(R.id.text_view_home_team_score);
            awayTeamScoreTextView = itemView.findViewById(R.id.text_view_away_team_score);
            matchDateTextView = itemView.findViewById(R.id.text_view_match_date);
            matchWeekDayTextView = itemView.findViewById(R.id.text_view_match_weekday);
            matchTimeTextView = itemView.findViewById(R.id.text_view_match_time);

            cardView.setOnClickListener(this);
        }

        public void setCardView(MatchViewModel matchViewModel) {
            matchTypeTextView.setText(matchViewModel.getMatchType());
            matchDateTextView.setText(matchViewModel.getMatchDate());
            matchWeekDayTextView.setText(matchViewModel.getMatchWeekDay());
            matchTimeTextView.setText(matchViewModel.getMatchTime());
            homeTeamNameTextView.setText(matchViewModel.getHomeTeamName());
            awayTeamNameTextView.setText(matchViewModel.getAwayTeamName());
            homeTeamScoreTextView.setText(matchViewModel.getHomeTeamScore());
            awayTeamScoreTextView.setText(matchViewModel.getAwayTeamScore());
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) clickListener.onItemClick(v, getAdapterPosition());
        }
    }

    private List<MatchViewModel> matchList;
    private ItemClickListener clickListener;

    public MatchRecyclerViewAdapter(List<MatchViewModel> list) {
        matchList = list;
    }

    public void setClickListener(ItemClickListener listener) {
        clickListener = listener;
    }

    public MatchViewModel getItem(int id) {
        return matchList.get(id);
    }

    public List<MatchViewModel> getCurrentData() {
        return matchList;
    }

    public void updateData() {
        MatchViewModel matchViewModel = new MatchViewModel("英超", "2021年1月4日",
                "星期一", "00:30","切尔西","曼城",
                "0", "3");
        matchList.add(0, matchViewModel);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_match, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setCardView(matchList.get(position));
    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

}
