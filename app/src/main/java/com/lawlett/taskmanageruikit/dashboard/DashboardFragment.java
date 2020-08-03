package com.lawlett.taskmanageruikit.dashboard;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.PorterDuff;
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
            complete_task_amount, todo_percent, personalPercent, workPercent, meetPercent, homePercent, privatePercent;
    ProgressBar allTaskProgress, personalProgress, workProgress, meetProgress, homeProgress, privateProgress;
    int personalDoneAmount, workDoneAmount, meetDoneAmount, homeDoneAmount, privateDoneAmount, plansAmount, doneAmount,
            personalAmount, workAmount, meetAmount, homeAmount, privateAmount, todoAmount, eventAmount, allTaskAmount,
            allTaskDoneAndNotDone, personalDoneAndNotDone, workDoneAndNotDone,
            homeDoneAndNotDone, meetDoneAndNotDone, privateDoneAndNotDone;
    int todoPercent, personalPercentAmount, workPercentAmount, meetPercentAmount, homePercentAmount, privatePercentAmount;

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

        getDataFromBD();
        getTasksDoneAmount();
        getAllTasks();
        getAllDoneAndNotDoneTasks();
        countUpPercent();

        setShow();
    }

    public void countUpPercent() {
        try {
            meetPercentAmount = meetDoneAmount * 100 / meetDoneAndNotDone;
            todoPercent = doneAmount * 100 / allTaskDoneAndNotDone;
            personalPercentAmount = personalDoneAmount * 100 / personalDoneAndNotDone;
            workPercentAmount = workDoneAmount * 100 / workDoneAndNotDone;
            homePercentAmount = homeDoneAmount * 100 / homeDoneAndNotDone;
            privatePercentAmount = privateDoneAmount * 100 / privateDoneAndNotDone;
        } catch (ArithmeticException e) {
            e.printStackTrace();
        }
    }

    public void getTasksDoneAmount() {
        personalDoneAmount = App.getDataBase().personalDoneTaskDao().getAll().size();//Персональные выполненные
        workDoneAmount = App.getDataBase().workDoneTaskDao().getAll().size();//Работа выполненные
        meetDoneAmount = App.getDataBase().meetDoneTaskDao().getAll().size();//Встречи выполненные
        homeDoneAmount = App.getDataBase().homeDoneTaskDao().getAll().size();//Дом выполненные
        privateDoneAmount = App.getDataBase().privateDoneTaskDao().getAll().size();//Приватные выполненные
    }

    public void getDataFromBD() {
        plansAmount = App.getDataBase().taskDao().getAll().size(); //идеи кол=во
        doneAmount = App.getDataBase().doneTaskDao().getAll().size();//Выполненные задачи
        personalAmount = App.getDataBase().personalDao().getAll().size();//Персональные задачи кол-во
        workAmount = App.getDataBase().workDao().getAll().size();//Работа задачи кол-во
        meetAmount = App.getDataBase().meetDao().getAll().size();//Встречи задачи кол-во
        homeAmount = App.getDataBase().homeDao().getAll().size();//Дом задачи кол-во
        privateAmount = App.getDataBase().privateDao().getAll().size();//Приватные задачи кол-во
        eventAmount = App.getDataBase().dataDao().getAll().size();//События задачи кол-во
    }

    public void getAllDoneAndNotDoneTasks() {
        allTaskDoneAndNotDone = doneAmount + allTaskAmount; //Все задачи для получение % Всех задач
        personalDoneAndNotDone = personalDoneAmount + personalAmount;//Все Персональные задачи
        workDoneAndNotDone = workDoneAmount + workAmount;
        meetDoneAndNotDone = meetDoneAmount + meetAmount;
        homeDoneAndNotDone = homeDoneAmount + homeAmount;
        privateDoneAndNotDone = privateDoneAmount + privateAmount;
    }

    public void getAllTasks() {
        todoAmount = personalAmount + workAmount + meetAmount + homeAmount + privateAmount; //Все задачи
        allTaskAmount = todoAmount + eventAmount + plansAmount;//Всего записей
    }

    @SuppressLint("SetTextI18n")
    public void setShow() {
        plans_amount.setText(String.valueOf(plansAmount)); //Отображеие кол-во идей
        todo_amount.setText(String.valueOf(todoAmount));  //Отображение кол-во Задач
        event_amount.setText(String.valueOf(eventAmount)); //Отображение кол-во Событий
        complete_task_amount.setText(String.valueOf(doneAmount)); //Отображение кол-во Завершённых Задач
        allTask_amount.setText(String.valueOf(allTaskAmount)); //Отображение кол-во Всех Задач

        todo_percent.setText(todoPercent + "%");
        personalPercent.setText(personalPercentAmount + "%");
        workPercent.setText(workPercentAmount + "%");
        meetPercent.setText(meetPercentAmount + "%");
        homePercent.setText(homePercentAmount + "%");
        privatePercent.setText(privatePercentAmount + "%");

        allTaskProgress.setProgress(doneAmount);
        allTaskProgress.setMax(allTaskDoneAndNotDone);
        allTaskProgress.getProgressDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

        personalProgress.setProgress(personalDoneAmount);
        personalProgress.setMax(personalDoneAndNotDone);
        personalProgress.getProgressDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

        workProgress.setProgress(workDoneAmount);
        workProgress.setMax(workDoneAndNotDone);
        workProgress.getProgressDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

        meetProgress.setProgress(meetDoneAmount);
        meetProgress.setMax(meetDoneAndNotDone);
        meetProgress.getProgressDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

        homeProgress.setProgress(homeDoneAmount);
        homeProgress.setMax(homeDoneAndNotDone);
        homeProgress.getProgressDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);

        privateProgress.setProgress(privateDoneAmount);
        privateProgress.setMax(privateDoneAndNotDone);
        privateProgress.getProgressDrawable().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
    }
}
