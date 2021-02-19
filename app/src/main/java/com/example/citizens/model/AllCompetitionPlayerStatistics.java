package com.example.citizens.model;

import org.json.JSONException;
import org.json.JSONObject;


public class AllCompetitionPlayerStatistics {

    public String name;

    public String position;

    public String age;

    public Integer games;

    public Integer gamesStarts;

    public Integer minutes;

    public Integer goals;

    public Integer assists;

    public Integer penMade;

    public Integer penAtt;

    public Integer cardsYellow;

    public Integer cardsRed;

    public Float goalsPer90;

    public Float assistsPer90;

    public Float goalsAssistsPer90;

    public Float goalsNpPer90;

    public Float goalsAssistsNpPer90;

    public Float XG;

    public Float npXG;

    public Float XA;

    public Float XGPer90;

    public Float XAPer90;

    public Float XGXAPer90;

    public Float npXGNpPer90;

    public Float npXGXAPer90;

    private String processName(String name) {
        return name.replace("·", "\n").trim();
    }

    private String processAge(String age) {
        return age.replace("-", "岁").concat("天").trim();
    }

    private Integer processInteger(String integer) {
        return integer.equals("-") ? 0 : Integer.parseInt(integer);
    }

//    private Double processDouble(String doubleStr) {
//        return doubleStr.equals("-") ? 0.0 : Double.parseDouble(doubleStr);
//    }

    private Float processFloat(String floatStr) {
        return floatStr.equals("-") ? 0 : Float.parseFloat(floatStr);
    }

    public AllCompetitionPlayerStatistics(JSONObject data) throws JSONException {
        this.name = processName(data.getString("name"));
        this.position = data.getString("position").trim();
        this.age = processAge(data.getString("age"));
        this.games = processInteger(data.getString("games"));
        this.gamesStarts = processInteger(data.getString("games_starts"));
        this.minutes = processInteger(data.getString("minutes"));
        this.goals = processInteger(data.getString("goals"));
        this.assists = processInteger(data.getString("assists"));
        this.penMade = processInteger(data.getString("pens_made"));
        this.penAtt = processInteger(data.getString("pens_att"));
        this.cardsYellow = processInteger(data.getString("cards_yellow"));
        this.cardsRed = processInteger(data.getString("cards_red"));
        this.goalsPer90 = processFloat(data.getString("goals_per90"));
        this.assistsPer90 = processFloat(data.getString("assists_per90"));
        this.goalsAssistsPer90 = processFloat(data.getString("goals_assists_per90"));
        this.goalsNpPer90 = processFloat(data.getString("goals_pens_per90"));
        this.goalsAssistsNpPer90 = processFloat(data.getString("goals_assists_pens_per90"));
        this.XG = processFloat(data.getString("xg"));
        this.npXG = processFloat(data.getString("npxg"));
        this.XA = processFloat(data.getString("xa"));
        this.XGPer90 = processFloat(data.getString("xg_per90"));
        this.XAPer90 = processFloat(data.getString("xa_per90"));
        this.XGXAPer90 = processFloat(data.getString("xg_xa_per90"));
        this.npXGNpPer90 = processFloat(data.getString("npxg_per90"));
        this.npXGXAPer90 = processFloat(data.getString("npxg_xa_per90"));
    }
}
