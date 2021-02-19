package com.example.citizens.adapter;

import android.content.Context;
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
import com.example.citizens.viewmodel.MatchViewModel;
import com.omadahealth.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MatchRecyclerViewAdapter extends RecyclerView.Adapter<MatchRecyclerViewAdapter.ViewHolder> {


    private Context context;
    private List<MatchViewModel> matchList;
    private ItemClickListener clickListener;

//    public int getCurrentPosition() {
//        Date date = new Date();
//        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        String today = format.format(date);
//        Toast.makeText(context, today, Toast.LENGTH_SHORT).show();
//        int position = 0;
//        for (int i = 0; i < matchList.size(); i++) {
//            if (matchList.get(i).getMatchDate().compareTo(today) < 0) {
//                position = i;
//            } else {
//                break;
//            }
//        }
//        return position;
//    }

    public MatchRecyclerViewAdapter(Context context, List<MatchViewModel> list) {
        this.context = context;
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

    public void updateMatchSchedule(List<MatchViewModel> list) {

        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        String today = format.format(date);

        Comparator<MatchViewModel> comparator = new Comparator<MatchViewModel>() {
            @Override
            public int compare(MatchViewModel o1, MatchViewModel o2) {
                if (o1 == null || o2 == null) {
                    return 0;
                }
                return (o1.getMatchDate()+o1.getMatchTime()).compareTo(o2.getMatchDate()+o2.getMatchTime());
            }
        };

//        Collections.sort(list, comparator);

        int slow_index = 0;
        int fast_index = 5;

        while (fast_index < list.size()) {
            if (list.get(fast_index).getMatchDate().compareTo(today) >= 0) {
//                Toast.makeText(context, today + " ->" + list.get(fast_index).getMatchDate(), Toast.LENGTH_SHORT).show();
                break;
            } else {
                slow_index += 1;
                fast_index += 1;
            }
        }

        Set<MatchViewModel> set = new HashSet<MatchViewModel>(list.subList(slow_index, list.size()));
//        set.addAll(list);
//        set.addAll(matchList);
        matchList.clear();
        matchList.addAll(set);

        Collections.sort(matchList, comparator);
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
        holder.setCardView(context, matchList.get(position));
    }

    @Override
    public int getItemCount() {
        return matchList.size();
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CardView cardView;

        private ImageView homeTeamLogoImageView;
        private ImageView awayTeamLogoImageView;

        private TextView matchLeagueTextView;
        private TextView matchDateTextView;
        private TextView matchWeekDayTextView;
        private TextView matchTimeTextView;
        private TextView matchProgressTextView;

        private TextView homeTeamNameTextView;
        private TextView awayTeamNameTextView;
        private TextView homeTeamScoreTextView;
        private TextView awayTeamScoreTextView;



        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view_match);

            homeTeamLogoImageView = itemView.findViewById(R.id.image_view_home_team_logo);
            awayTeamLogoImageView = itemView.findViewById(R.id.image_view_away_team_logo);

            matchProgressTextView = itemView.findViewById(R.id.text_view_match_progress);
            matchLeagueTextView = itemView.findViewById(R.id.text_view_match_league);
            homeTeamNameTextView = itemView.findViewById(R.id.text_view_home_team_name);
            awayTeamNameTextView = itemView.findViewById(R.id.text_view_away_team_name);
            homeTeamScoreTextView = itemView.findViewById(R.id.text_view_home_team_score);
            awayTeamScoreTextView = itemView.findViewById(R.id.text_view_away_team_score);
            matchDateTextView = itemView.findViewById(R.id.text_view_match_date);
            matchWeekDayTextView = itemView.findViewById(R.id.text_view_match_weekday);
            matchTimeTextView = itemView.findViewById(R.id.text_view_match_time);

            cardView.setOnClickListener(this);
        }

        public void setCardView(Context context, MatchViewModel matchViewModel) {
//            matchProgressTextView.setText();

            matchWeekDayTextView.setText(matchViewModel.getMatchWeekDay());
            matchLeagueTextView.setText(matchViewModel.getMatchLeague());
            matchDateTextView.setText(matchViewModel.getMatchDate());
            matchTimeTextView.setText(matchViewModel.getMatchTime());

            matchProgressTextView.setText(matchViewModel.getMatchProgress());

            homeTeamNameTextView.setText(matchViewModel.getHomeTeamName());
            awayTeamNameTextView.setText(matchViewModel.getAwayTeamName());

            homeTeamScoreTextView.setText(matchViewModel.getHomeTeamScore());
            awayTeamScoreTextView.setText(matchViewModel.getAwayTeamScore());

            Glide.with(context)
                    .load(matchViewModel.getHomeTeamLogoURL())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(homeTeamLogoImageView);
            Glide.with(context)
                    .load(matchViewModel.getAwayTeamLogoURL())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(awayTeamLogoImageView);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) clickListener.onItemClick(v, getAdapterPosition());
        }
    }

}
