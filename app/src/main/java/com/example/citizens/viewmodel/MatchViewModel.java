package com.example.citizens.viewmodel;

import android.media.Image;
import android.nfc.FormatException;

import androidx.annotation.Nullable;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MatchViewModel {
    private String matchID;
    private String matchLeague;
    private String matchType;
    private String matchRound;
    private String matchDate;
    private String matchWeekDay;
    private String matchTime;

    private String homeTeamName;
    private String awayTeamName;
    private String homeTeamScore;
    private String awayTeamScore;

    private String homeTeamLogoURL;
    private String awayTeamLogoURL;

    private String matchProgress;

    public MatchViewModel(String matchID, String matchLeague, String matchType, String matchRound,
                          String matchDate, String matchTime, String homeTeamName,
                          String awayTeamName, String homeTeamScore, String awayTeamScore,
                          String homeTeamLogoURL, String awayTeamLogoURL) {
        this.matchID = matchID;
        this.matchLeague = matchLeague;
        this.matchType = matchType;
        this.matchRound = matchRound;
        this.matchDate = matchDate;
        this.matchTime = matchTime;
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
        this.homeTeamScore = homeTeamScore == null || homeTeamScore.equals("null") ? "" : homeTeamScore;
        this.awayTeamScore = awayTeamScore == null || homeTeamScore.equals("null") ? "" : awayTeamScore;
        this.homeTeamLogoURL = homeTeamLogoURL;
        this.awayTeamLogoURL = awayTeamLogoURL;

        // set match progress text
        boolean isMatchTypeEmpty = matchType == null || matchType.equals("null") || matchType.isEmpty();
        if (matchRound != null && !matchRound.equals("null") && !matchRound.isEmpty()) {
            this.matchProgress = isMatchTypeEmpty ? "第" + matchRound + "轮" : matchType + "第" + matchRound + "轮";
        } else {
            this.matchProgress = isMatchTypeEmpty ? "" : matchType;
        }

        // set chinese date format text & chinese weekday format text
        DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat outputFormat = new SimpleDateFormat("yyyy年MM月dd日");
        try {
            Date date = inputFormat.parse(matchDate);
            this.matchDate = outputFormat.format(date);

            String[] weeks = {"星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"};
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int week_index = calendar.get(Calendar.DAY_OF_WEEK) - 1;
            if (week_index < 0) {
                week_index = 0;
            }
            this.matchWeekDay = weeks[week_index];
        } catch (ParseException e) {
            e.printStackTrace();
            this.matchWeekDay = "";
        } catch (Exception e) {
            e.printStackTrace();
            this.matchDate = matchDate;
        }

    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (!(obj instanceof MatchViewModel)) {
            return false;
        }
        MatchViewModel rhs = (MatchViewModel) obj;
        return this.getMatchID().equals(rhs.getMatchID());
    }

    @Override
    public int hashCode() {
        return this.getMatchID().hashCode();
    }

    public String getMatchRound() {
        return matchRound;
    }

    public void setMatchRound(String matchRound) {
        this.matchRound = matchRound;
    }

    public String getMatchProgress() {
        return matchProgress;
    }

    public void setMatchProgress(String matchProgress) {
        this.matchProgress = matchProgress;
    }

    public String getMatchLeague() {
        return matchLeague;
    }

    public void setMatchLeague(String matchLeague) {
        this.matchLeague = matchLeague;
    }

    public String getMatchID() {
        return matchID;
    }

    public void setMatchID(String matchID) {
        this.matchID = matchID;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public String getMatchWeekDay() {
        return matchWeekDay;
    }

    public void setMatchWeekDay(String matchWeekDay) {
        this.matchWeekDay = matchWeekDay;
    }

    public String getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(String matchTime) {
        this.matchTime = matchTime;
    }

    public String getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setHomeTeamScore(String homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    public String getAwayTeamScore() {
        return awayTeamScore;
    }

    public void setAwayTeamScore(String awayTeamScore) {
        this.awayTeamScore = awayTeamScore;
    }

    public String getMatchTypeLogo() {
        return matchType;
    }

    public void setMatchTypeLogo(String matchTypeLogo) {
        this.matchType = matchType;
    }

    public String getHomeTeamLogoURL() {
        return homeTeamLogoURL;
    }

    public void setHomeTeamLogoURL(String homeTeamLogoURL) {
        this.homeTeamLogoURL = homeTeamLogoURL;
    }

    public String getAwayTeamLogoURL() {
        return awayTeamLogoURL;
    }

    public void setAwayTeamLogoURL(Image awayTeamLogo) {
        this.awayTeamLogoURL = awayTeamLogoURL;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }
}
