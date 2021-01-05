package com.example.citizens.viewmodel;

import android.media.Image;

public class MatchViewModel {
    private String matchType;
    private String matchDate;
    private String matchWeekDay;
    private String matchTime;
    private String homeTeamName;
    private String awayTeamName;
    private String homeTeamScore;
    private String awayTeamScore;

    private Image matchTypeLogo = null;
    private Image homeTeamLogo = null;
    private Image awayTeamLogo = null;

    public MatchViewModel(String matchType, String matchDate, String matchWeekDay, String matchTime,
                          String homeTeamName, String awayTeamName, String homeTeamScore, String awayTeamScore) {
        this.matchType = matchType;
        this.matchDate = matchDate;
        this.matchWeekDay = matchWeekDay;
        this.matchTime = matchTime;
        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
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

    public Image getMatchTypeLogo() {
        return matchTypeLogo;
    }

    public void setMatchTypeLogo(Image matchTypeLogo) {
        this.matchTypeLogo = matchTypeLogo;
    }

    public Image getHomeTeamLogo() {
        return homeTeamLogo;
    }

    public void setHomeTeamLogo(Image homeTeamLogo) {
        this.homeTeamLogo = homeTeamLogo;
    }

    public Image getAwayTeamLogo() {
        return awayTeamLogo;
    }

    public void setAwayTeamLogo(Image awayTeamLogo) {
        this.awayTeamLogo = awayTeamLogo;
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
