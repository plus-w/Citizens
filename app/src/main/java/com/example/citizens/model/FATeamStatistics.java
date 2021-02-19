package com.example.citizens.model;

import com.example.citizens.model.BaseStatistics;

import org.json.JSONException;
import org.json.JSONObject;

public class FATeamStatistics extends BaseStatistics {
    public String name;

    public String age;

    public Integer games;

    public Integer goals;

    public Integer assists;

    public Integer penMade;

    public Integer penAtt;

    public Integer cardsYellow;

    public Integer cardsRed;

    public Float goalsPer90;

    public Float assistsPer90;

    public Float goalsAssistsPer90;
    public FATeamStatistics(JSONObject data) throws JSONException {
        super(data);
        this.name = data.getString("name");
        this.age = data.getString("age");
        this.games = processInteger(data.getString("games"));
        this.goals = processInteger(data.getString("goals"));
        this.assists = processInteger(data.getString("assists"));
        this.penMade = processInteger(data.getString("pens_made"));
        this.penAtt = processInteger(data.getString("pens_att"));
        this.cardsYellow = processInteger(data.getString("cards_yellow"));
        this.cardsRed = processInteger(data.getString("cards_red"));
        this.goalsPer90 = processFloat(data.getString("goals_per90"));
        this.assistsPer90 = processFloat(data.getString("assists_per90"));
        this.goalsAssistsPer90 = processFloat(data.getString("goals_assists_per90"));
    }
}
