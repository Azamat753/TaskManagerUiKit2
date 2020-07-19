package com.lawlett.taskmanageruikit.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.utils.App;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {
    TextView plans_amount, todo_amount, event_amount, allTask_amount,
            complete_task_amount, event_percent, todo_percent, plans_percent;
    ProgressBar todoProgress;

    public DashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        plans_amount = view.findViewById(R.id.plans_amount);
        todo_amount = view.findViewById(R.id.todo_amount);
        event_amount = view.findViewById(R.id.events_amount);
        allTask_amount = view.findViewById(R.id.total_amount);
        complete_task_amount = view.findViewById(R.id.complete_task_amount);
        event_percent = view.findViewById(R.id.event_percent);
        todo_percent = view.findViewById(R.id.task_percent);
        plans_percent = view.findViewById(R.id.quick_percent);

        todoProgress=view.findViewById(R.id.todo_progress);

        int plansAmount = App.getDataBase().taskDao().getAll().size();
        int doneAmount = App.getDataBase().doneTaskDao().getAll().size();
        int personalAmount = App.getDataBase().personalDao().getAll().size();
        int workAmount = App.getDataBase().workDao().getAll().size();
        int meetAmount = App.getDataBase().meetDao().getAll().size();
        int homeAmount = App.getDataBase().homeDao().getAll().size();
        int privateAmount = App.getDataBase().privateDao().getAll().size();
        int todoAmount = personalAmount + workAmount + meetAmount + homeAmount + privateAmount;
        int eventAmount = App.getDataBase().dataDao().getAll().size();
        int allTaskAmount = todoAmount + eventAmount + plansAmount;
        int eventDoneAmount=App.getDataBase().calendarDoneTaskDao().getAll().size();
        int plansDoneAmount =App.getDataBase().quickDoneTaskDao().getAll().size();

        plans_amount.setText(plansAmount + "");
        todo_amount.setText(todoAmount + "");
        event_amount.setText(eventAmount + "");
        complete_task_amount.setText(doneAmount + "");
        allTask_amount.setText(allTaskAmount + "");

        try {
            int todoPercent =  doneAmount * 100 /allTaskAmount;
            todo_percent.setText(todoPercent + "%");
        } catch (ArithmeticException e) {
            e.printStackTrace();

        }
        todoProgress.setProgress(doneAmount);
        todoProgress.setMax(todoAmount);
    }

}
