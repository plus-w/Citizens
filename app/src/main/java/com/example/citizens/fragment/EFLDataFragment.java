package com.example.citizens.fragment;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bin.david.form.core.SmartTable;
import com.bin.david.form.data.CellInfo;
import com.bin.david.form.data.column.Column;
import com.bin.david.form.data.column.ColumnInfo;
import com.bin.david.form.data.format.bg.BaseCellBackgroundFormat;
import com.bin.david.form.data.format.bg.IBackgroundFormat;
import com.bin.david.form.data.style.FontStyle;
import com.bin.david.form.data.table.TableData;
import com.bin.david.form.listener.OnColumnClickListener;
import com.example.citizens.R;
import com.example.citizens.model.EFLPlayerStatistics;
import com.example.citizens.model.EFLTeamStatistics;
import com.example.citizens.model.EPLPlayerStatistics;
import com.example.citizens.model.EPLTeamStatistics;
import com.example.citizens.model.FAPlayerStatistics;
import com.example.citizens.model.FATeamStatistics;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EFLDataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EFLDataFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private SmartTable<EFLTeamStatistics> mTeamTable;
    private TableData<EFLTeamStatistics> mTeamTableData;

    private SmartTable<EFLPlayerStatistics> mPlayerTable;
    private TableData<EFLPlayerStatistics> mPlayerTableData;
    private Map<String, Boolean> mPlayerTableColumnSortToggle = new HashMap<>();
    private String mPlayerTableCurrentSortedColumnFieldName;

    public EFLDataFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
     * @return A new instance of fragment EFLDataFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EFLDataFragment newInstance() {
        EFLDataFragment fragment = new EFLDataFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_efl_data, container, false);
        mTeamTable = v.findViewById(R.id.table_efl_team);
        mPlayerTable = v.findViewById(R.id.table_efl_player);
        setDefaultTableStyle();
        return v;
    }

    public void setDefaultTableStyle() {
        mTeamTable.getConfig().setContentCellBackgroundFormat(new BaseCellBackgroundFormat<CellInfo>() {
            @Override
            public int getBackGroundColor(CellInfo cellInfo) {
                return ContextCompat.getColor(getActivity(), R.color.white);
            }
        });
        mPlayerTable.getConfig().setContentCellBackgroundFormat(new BaseCellBackgroundFormat<CellInfo>() {
            @Override
            public int getBackGroundColor(CellInfo cellInfo) {
                return ContextCompat.getColor(getActivity(), R.color.white);
            }
        });
        FontStyle contentStyle = new FontStyle();
        contentStyle.setTextColor(getContext().getColor(R.color.blue));
        contentStyle.setTextSpSize(getActivity(), 12);
        IBackgroundFormat columnTitleBackgroud = new IBackgroundFormat() {
            @Override
            public void drawBackground(Canvas canvas, Rect rect, Paint paint) {
                canvas.drawColor(getContext().getColor(R.color.blue));
//                paint.setColor(getContext().getColor(R.color.light_blue));
            }
        };
        FontStyle titleStyle = new FontStyle();
        titleStyle.setTextSpSize(getActivity(), 18);
        titleStyle.setTextColor(getContext().getColor(R.color.blue));

        FontStyle columnTitleStyle = new FontStyle();
        columnTitleStyle.setTextSpSize(getActivity(), 12);
        columnTitleStyle.setTextColor(getContext().getColor(R.color.white));
        mTeamTable.getConfig()
                .setColumnTitleHorizontalPadding(10)
                .setTableTitleStyle(titleStyle)
                .setContentStyle(contentStyle)
                .setColumnTitleBackground(columnTitleBackgroud)
                .setColumnTitleStyle(columnTitleStyle)
                .setShowXSequence(false)
                .setShowYSequence(false);
        mPlayerTable.getConfig()
                .setColumnTitleHorizontalPadding(10)
                .setTableTitleStyle(titleStyle)
                .setContentStyle(contentStyle)
                .setColumnTitleBackground(columnTitleBackgroud)
                .setColumnTitleStyle(columnTitleStyle)
                .setShowXSequence(false)
                .setShowYSequence(false);
//        mTable.setNestedScrollingEnabled(false);
    }

    public void setPlayerStatTableData(JSONArray jsonArray) throws JSONException {
// create columns
        Column<String> nameCol = new Column<String>("球员", "name");
        nameCol.setFixed(true);
        mPlayerTableColumnSortToggle.put("name", false);
        Column<String> ageCol = new Column<String>("年龄", "age");
        mPlayerTableColumnSortToggle.put("age", false);
        Column<String> positionCol = new Column<String>("位置", "position");
        mPlayerTableColumnSortToggle.put("position", false);
        Column<Integer> gamesCol = new Column<Integer>("场次", "games");
        mPlayerTableColumnSortToggle.put("games", false);
        Column<Integer> gamesStartsCol = new Column<Integer>("首发", "gamesStarts");
        mPlayerTableColumnSortToggle.put("gamesStarts", false);
        Column<Integer> minutesCol = new Column<Integer>("出场分钟", "minutes");
        mPlayerTableColumnSortToggle.put("minutes", true);
        mPlayerTableCurrentSortedColumnFieldName = "minutes";
        Column<Integer> goalsCol = new Column<Integer>("进球", "goals");
        mPlayerTableColumnSortToggle.put("goals", false);
        Column<Integer> assistsCol = new Column<Integer>("助攻", "assists");
        mPlayerTableColumnSortToggle.put("assists", false);
        Column<Integer> penMadeCol = new Column<Integer>("点球命中", "penMade");
        mPlayerTableColumnSortToggle.put("penMade", false);
        Column<Integer> penAttCol = new Column<Integer>("点球射门", "penAtt");
        mPlayerTableColumnSortToggle.put("penAtt", false);
        Column<Integer> cardsYellowCol = new Column<Integer>("黄牌", "cardsYellow");
        mPlayerTableColumnSortToggle.put("cardsYellow", false);
        Column<Integer> cardsRedCol = new Column<Integer>("红牌", "cardsRed");
        mPlayerTableColumnSortToggle.put("cardsRed", false);
        Column<Float> goalsPer90Col = new Column<Float>("场均进球", "goalsPer90");
        mPlayerTableColumnSortToggle.put("goalsPer90", false);
        Column<Float> assistsPer90Col = new Column<Float>("场均助攻", "assistsPer90");
        mPlayerTableColumnSortToggle.put("assistsPer90", false);
        Column<Float> goalsAssistsPer90Col = new Column<Float>("场均进球+助攻", "goalsAssistsPer90");
        mPlayerTableColumnSortToggle.put("goalsAssistsPer90", false);
        //        Column<String> goalsNpPer90Col = new Column<String>("场均非点球进球", "goalsNpPer90");
//        Column<String> goalsAssistsNpPer90Col = new Column<String>("场均非点球助攻", "goalsAssistsNpPer90");
//        Column<Float> XGCol = new Column<Float>("期望进球", "XG");
//        mPlayerTableColumnSortToggle.put("XG", false);
//        Column<String> npXGCol = new Column<String>("非点球期望进球", "npXG");
//        Column<Float> XACol = new Column<Float>("期望助攻", "XA");
//        mPlayerTableColumnSortToggle.put("XA", false);
//        Column<Float> XGPer90Col = new Column<Float>("场均期望进球", "XGPer90");
//        mPlayerTableColumnSortToggle.put("XGPer90", false);
//        Column<Float> XAPer90Col = new Column<Float>("场均期望助攻", "XAPer90");
//        mPlayerTableColumnSortToggle.put("XAPer90", false);
//        Column<Float> XGXAPer90Col = new Column<Float>("场均期望进球+助攻", "XGXAPer90");
//        mPlayerTableColumnSortToggle.put("XGXAPer90", false);
//        Column<String> nationalityCol = new Column<String>("国籍", "nationality");
//        Column<String> npXGNpPer90Col = new Column<String>("场均非点球期望进球", "npXGNpPer90");
//        Column<String> npXGXANpPer90Col = new Column<String>("场均非点球期望进球+助攻", "npXGXANpPer90");

        List<EFLPlayerStatistics> statList = new ArrayList<>();

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = (JSONObject) jsonArray.get(i);
            statList.add(new EFLPlayerStatistics(obj));
        }
        mPlayerTableData = new TableData<>(
                "2020-2021赛季球员联赛杯数据",
                statList,
                nameCol,
                gamesCol,
                gamesStartsCol,
                minutesCol,
                goalsCol,
                penMadeCol,
                penAttCol,
                assistsCol,
                goalsPer90Col,
                assistsPer90Col,
                goalsAssistsPer90Col,
                cardsYellowCol,
                cardsRedCol,
                positionCol,
                ageCol
        );
        mPlayerTable.setTableData(mPlayerTableData);
        mPlayerTable.setOnColumnClickListener(new OnColumnClickListener() {
            @Override
            public void onClick(ColumnInfo columnInfo) {
                if (columnInfo.column.getFieldName().equals("name")) {
                    return;
                }
                String columnFiledName = columnInfo.column.getFieldName();
                if (!mPlayerTableCurrentSortedColumnFieldName.equals(columnFiledName)) {
                    mPlayerTableColumnSortToggle.put(mPlayerTableCurrentSortedColumnFieldName, false);
                    mPlayerTableCurrentSortedColumnFieldName = columnFiledName;
                    mPlayerTableColumnSortToggle.put(columnFiledName, true);
                    columnInfo.column.setReverseSort(true);
                } else {
                    columnInfo.column.setReverseSort(!mPlayerTableColumnSortToggle.get(columnFiledName));
                    mPlayerTableColumnSortToggle.put(columnFiledName, !mPlayerTableColumnSortToggle.get(columnFiledName));
                }
                mPlayerTableData.setSortColumn(columnInfo.column);
//                mTable.notifyDataChanged();
                mPlayerTable.setTableData(mPlayerTableData);
//                mTable.notifyDataChanged();
//                mTableData.
            }
        });
    }

    public void setTeamStatTableData(JSONArray jsonArray) throws JSONException {
        // create columns
        Column<String> nameCol = new Column<String>("队伍", "name");
        nameCol.setFixed(true);
        Column<String> ageCol = new Column<String>("年龄", "age");
        Column<Integer> gamesCol = new Column<Integer>("场次", "games");
        Column<Integer> goalsCol = new Column<Integer>("进球", "goals");
        Column<Integer> assistsCol = new Column<Integer>("助攻", "assists");
        Column<Integer> penMadeCol = new Column<Integer>("点球命中", "penMade");
        Column<Integer> penAttCol = new Column<Integer>("点球射门", "penAtt");
        Column<Integer> cardsYellowCol = new Column<Integer>("黄牌", "cardsYellow");
        Column<Integer> cardsRedCol = new Column<Integer>("红牌", "cardsRed");
        Column<Float> goalsPer90Col = new Column<Float>("场均进球", "goalsPer90");
        Column<Float> assistsPer90Col = new Column<Float>("场均助攻", "assistsPer90");
        Column<Float> goalsAssistsPer90Col = new Column<Float>("场均进球+助攻", "goalsAssistsPer90");
        //        Column<String> goalsNpPer90Col = new Column<String>("场均非点球进球", "goalsNpPer90");
//        Column<String> goalsAssistsNpPer90Col = new Column<String>("场均非点球助攻", "goalsAssistsNpPer90");
//        Column<Float> XGCol = new Column<Float>("期望进球", "XG");
//        Column<String> npXGCol = new Column<String>("非点球期望进球", "npXG");
//        Column<Float> XACol = new Column<Float>("期望助攻", "XA");
//        Column<Float> XGPer90Col = new Column<Float>("场均期望进球", "XGPer90");
//        Column<Float> XAPer90Col = new Column<Float>("场均期望助攻", "XAPer90");
//        Column<Float> XGXAPer90Col = new Column<Float>("场均期望进球+助攻", "XGXAPer90");
//        Column<String> nationalityCol = new Column<String>("国籍", "nationality");
//        Column<String> npXGNpPer90Col = new Column<String>("场均非点球期望进球", "npXGNpPer90");
//        Column<String> npXGXANpPer90Col = new Column<String>("场均非点球期望进球+助攻", "npXGXANpPer90");

        List<EFLTeamStatistics> statList = new ArrayList<>();
//        Toast.makeText(getContext(), jsonArray.toString(), Toast.LENGTH_SHORT).show();
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = (JSONObject) jsonArray.get(i);
            statList.add(new EFLTeamStatistics(obj));
        }

        mTeamTableData = new TableData<>(
                "2020-2021赛季球队联赛杯数据",
                statList,
                nameCol,
                gamesCol,
                goalsCol,
                penMadeCol,
                penAttCol,
                assistsCol,
                goalsPer90Col,
                assistsPer90Col,
                goalsAssistsPer90Col,
                cardsYellowCol,
                cardsRedCol,
                ageCol
        );
        mTeamTable.setTableData(mTeamTableData);
    }
}