package com.example.citizens.model;

import androidx.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class BaseStatistics {

    protected String processName(@NonNull String name) {
        return name.replace("·", "\n").trim();
    }

    protected String processAge(@NonNull String age) {
        return age.replace("-", "岁").concat("天").trim();
    }

    protected Integer processInteger(@NonNull String integer) {
        return integer.equals("-") ? 0 : Integer.parseInt(integer);
    }

//    protected Double processDouble(String doubleStr) {
//        return doubleStr.equals("-") ? 0.0 : Double.parseDouble(doubleStr);
//    }

    protected Float processFloat(@NonNull String floatStr) {
        return floatStr.equals("-") ? 0 : Float.parseFloat(floatStr);
    }

    public BaseStatistics(@NonNull JSONObject data) {
    }
}
