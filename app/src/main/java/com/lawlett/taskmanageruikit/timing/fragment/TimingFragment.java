package com.lawlett.taskmanageruikit.timing.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.clans.fab.FloatingActionButton;
import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.timing.activity.StopwatchActivity;
import com.lawlett.taskmanageruikit.timing.activity.TimerActivity;
import com.lawlett.taskmanageruikit.timing.adapter.TimingAdapter;
import com.lawlett.taskmanageruikit.timing.model.TimingModel;
import com.lawlett.taskmanageruikit.utils.App;

import java.util.ArrayList;
import java.util.List;

public class TimingFragment extends Fragment {
    TimingAdapter adapter;
    TimingModel timingModel;
    private List<TimingModel> list;
    FloatingActionButton floatingActionTimer, floatingActionStopwatch;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_timing, container, false);
        list = new ArrayList<>();

        App.getDataBase().timingDao().getAllLive().observe(this, timingModels -> {
            if (timingModels != null)
                list.clear();
            list.addAll(timingModels);
            adapter.notifyDataSetChanged();

        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        RecyclerView recyclerView = view.findViewById(R.id.timing_recycler);
        adapter = new TimingAdapter(list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        floatingActionStopwatch = view.findViewById(R.id.fab_stopwatch);
        floatingActionTimer = view.findViewById(R.id.fab_timer);

        floatingActionTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), TimerActivity.class));
            }
        });
        floatingActionStopwatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), StopwatchActivity.class));
            }
        });
    }
}