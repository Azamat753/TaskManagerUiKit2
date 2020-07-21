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
import com.lawlett.taskmanageruikit.utils.HomeSizePreference;
import com.lawlett.taskmanageruikit.utils.MeetSizePreference;
import com.lawlett.taskmanageruikit.utils.PersonalSizePreference;
import com.lawlett.taskmanageruikit.utils.PrivateSizePreference;
import com.lawlett.taskmanageruikit.utils.WorkSizePreference;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardFragment extends Fragment {
    TextView plans_amount, todo_amount, event_amount, allTask_amount,
            complete_task_amount, todo_percent, personalPercent, workPercent, meetPercent, homePercent, privatePercent;
    ProgressBar allTaskProgress, personalProgress, workProgress, meetProgress, homeProgress, privateProgress;

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
        todo_percent = view.findViewById(R.id.allTask_percent);
        personalPercent = view.findViewById(R.id.personal_percent);
        workPercent = view.findViewById(R.id.work_percent);
        homePercent = view.findViewById(R.id.homeTask_percent);
        meetPercent = view.findViewById(R.id.meetTask_percent);
        privatePercent = view.findViewById(R.id.privateTask_percent);
        allTaskProgress = view.findViewById(R.id.allTask_progress);
        personalProgress = view.findViewById(R.id.personal_progress);
        workProgress = view.findViewById(R.id.work_progress);
        meetProgress = view.findViewById(R.id.meetTask_progress);
        homeProgress = view.findViewById(R.id.homeTask_progress);
        privateProgress = view.findViewById(R.id.private_progress);

        int personalDoneAmount = PersonalSizePreference.getInstance(getContext()).getPersonalSize();
        int workDoneAmount = WorkSizePreference.getInstance(getContext()).getWorkSize();
        int meetDoneAmount = MeetSizePreference.getInstance(getContext()).getMeetSize();
        int homeDoneAmount = HomeSizePreference.getInstance(getContext()).getHomeSize();
        int privateDoneAmount = PrivateSizePreference.getInstance(getContext()).getPrivateSize();

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
        int allTaskDoneAndNotDone = doneAmount + allTaskAmount;
        int personalDoneAndNotDone = personalDoneAmount + personalAmount;
        int workDoneAndNotDone = workDoneAmount + workAmount;
        int meetDoneAndNotDone = meetDoneAmount + meetAmount;
        int homeDoneAndNotDone = homeDoneAmount + homeAmount;
        int privateDoneAndNotDone = privateDoneAmount + privateAmount;


        plans_amount.setText(plansAmount + "");
        todo_amount.setText(todoAmount + "");
        event_amount.setText(eventAmount + "");
        complete_task_amount.setText(doneAmount + "");
        allTask_amount.setText(allTaskAmount + "");

        try {
            int todoPercent = doneAmount * 100 / allTaskDoneAndNotDone;
            int personalPercentAmount = personalDoneAmount * 100 / personalDoneAndNotDone;
            int workPercentAmount = workDoneAmount * 100 / workDoneAndNotDone;
            int meetPercentAmount = meetDoneAmount * 100 / meetDoneAndNotDone;
            int homePercentAmount = homeDoneAmount * 100 / homeDoneAndNotDone;
            int privatePercentAmount = privateDoneAmount * 100 / privateDoneAndNotDone;

            todo_percent.setText(todoPercent + "%");
            personalPercent.setText(personalPercentAmount + "%");
            workPercent.setText(workPercentAmount + "%");
            meetPercent.setText(meetPercentAmount + "%");
            homePercent.setText(homePercentAmount + "%");
            privatePercent.setText(privatePercentAmount + "%");
        } catch (ArithmeticException e) {
            e.printStackTrace();

        }
        allTaskProgress.setProgress(doneAmount);
        allTaskProgress.setMax(allTaskDoneAndNotDone);

        personalProgress.setProgress(personalDoneAmount);
        personalProgress.setMax(personalDoneAndNotDone);

        workProgress.setProgress(workDoneAmount);
        workProgress.setMax(workDoneAndNotDone);

        meetProgress.setProgress(meetDoneAmount);
        meetProgress.setMax(meetDoneAndNotDone);

        homeProgress.setProgress(homeDoneAmount);
        homeProgress.setMax(homeDoneAndNotDone);

        privateProgress.setProgress(privateDoneAmount);
        privateProgress.setMax(privateDoneAndNotDone);
    }

}
