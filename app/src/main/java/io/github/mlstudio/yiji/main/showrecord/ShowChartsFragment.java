package io.github.mlstudio.yiji.main.showrecord;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.github.mlstudio.yiji.R;
import io.github.mlstudio.yiji.base.BaseFragment;
import io.github.mlstudio.yiji.javabeans.YiJiRecord;
import io.github.mlstudio.yiji.tools.YiJiUtil;
import io.github.mlstudio.yiji.tools.db.DataManager;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ShowChartsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowChartsFragment extends BaseFragment {
    static final int TODAY = 0;
    static final int YESTERDAY = 1;
    static final int THIS_WEEK = 2;
    static final int LAST_WEEK = 3;
    static final int THIS_MONTH = 4;
    static final int LAST_MONTH = 5;
    static final int THIS_YEAR = 6;
    static final int LAST_YEAR = 7;
    private int position;
    private List<YiJiRecord> list = new ArrayList<>();
    private Context mContext;

    public static ShowChartsFragment newInstance(int position) {
        ShowChartsFragment fragment = new ShowChartsFragment();
        Bundle args = new Bundle();
        args.putInt("POSITION", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        position = getArguments() != null ? getArguments().getInt("POSITION") : 1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_show_charts, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView mRecyclerView = view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        Calendar now = Calendar.getInstance();
        Calendar leftRange;
        Calendar rightRange;

        int start = -1;
        int end = 0;

        switch (position) {
            case TODAY:
                leftRange = YiJiUtil.GetTodayLeftRange(now);
                for (int i = DataManager.RECORDS.size() - 1; i >= 0; i--) {
                    if (DataManager.RECORDS.get(i).getCalendar().before(leftRange)) {
                        end = i + 1;
                        break;
                    }
                    if (start == -1) {
                        start = i;
                    }
                }
                break;
            case YESTERDAY:
                leftRange = YiJiUtil.GetYesterdayLeftRange(now);
                rightRange = YiJiUtil.GetYesterdayRightRange(now);
                for (int i = DataManager.RECORDS.size() - 1; i >= 0; i--) {
                    if (DataManager.RECORDS.get(i).getCalendar().before(leftRange)) {
                        end = i + 1;
                        break;
                    } else if (!DataManager.RECORDS.get(i).getCalendar().after(rightRange)) {
                        if (start == -1) {
                            start = i;
                        }
                    }
                }
                break;
            case THIS_WEEK:
                leftRange = YiJiUtil.GetThisWeekLeftRange(now);
                for (int i = DataManager.RECORDS.size() - 1; i >= 0; i--) {
                    if (DataManager.RECORDS.get(i).getCalendar().before(leftRange)) {
                        end = i + 1;
                        break;
                    }
                    if (start == -1) {
                        start = i;
                    }
                }
                break;
            case LAST_WEEK:
                leftRange = YiJiUtil.GetLastWeekLeftRange(now);
                rightRange = YiJiUtil.GetLastWeekRightRange(now);
                for (int i = DataManager.RECORDS.size() - 1; i >= 0; i--) {
                    if (DataManager.RECORDS.get(i).getCalendar().before(leftRange)) {
                        end = i + 1;
                        break;
                    } else if (DataManager.RECORDS.get(i).getCalendar().before(rightRange)) {
                        if (start == -1) {
                            start = i;
                        }
                    }
                }
                break;
            case THIS_MONTH:
                leftRange = YiJiUtil.GetThisMonthLeftRange(now);
                for (int i = DataManager.RECORDS.size() - 1; i >= 0; i--) {
                    if (DataManager.RECORDS.get(i).getCalendar().before(leftRange)) {
                        end = i + 1;
                        break;
                    }
                    if (start == -1) {
                        start = i;
                    }
                }
                break;
            case LAST_MONTH:
                leftRange = YiJiUtil.GetLastMonthLeftRange(now);
                rightRange = YiJiUtil.GetLastMonthRightRange(now);
                for (int i = DataManager.RECORDS.size() - 1; i >= 0; i--) {
                    if (DataManager.RECORDS.get(i).getCalendar().before(leftRange)) {
                        end = i + 1;
                        break;
                    } else if (DataManager.RECORDS.get(i).getCalendar().before(rightRange)) {
                        if (start == -1) {
                            start = i;
                        }
                    }
                }
                break;
            case THIS_YEAR:
                leftRange = YiJiUtil.GetThisYearLeftRange(now);
                for (int i = DataManager.RECORDS.size() - 1; i >= 0; i--) {
                    if (DataManager.RECORDS.get(i).getCalendar().before(leftRange)) {
                        end = i + 1;
                        break;
                    }
                    if (start == -1) {
                        start = i;
                    }
                }
                break;
            case LAST_YEAR:
                leftRange = YiJiUtil.GetLastYearLeftRange(now);
                rightRange = YiJiUtil.GetLastYearRightRange(now);
                for (int i = DataManager.RECORDS.size() - 1; i >= 0; i--) {
                    if (DataManager.RECORDS.get(i).getCalendar().before(leftRange)) {
                        end = i + 1;
                        break;
                    } else if (DataManager.RECORDS.get(i).getCalendar().before(rightRange)) {
                        if (start == -1) {
                            start = i;
                        }
                    }
                }
                break;
        }

        RecyclerView.Adapter adapter = new ShowChartsRecyclerViewAdapter(start, end, mContext, position);

        RecyclerViewMaterialAdapter mAdapter = new RecyclerViewMaterialAdapter(adapter);
        mRecyclerView.setAdapter(mAdapter);
        Log.d("TodayViewFragment", "text");
        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
