package com.lawlett.taskmanageruikit.todo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.tasksPage.doneTask.DoneTasksActivity;
import com.lawlett.taskmanageruikit.tasksPage.homeTask.HomeActivity;
import com.lawlett.taskmanageruikit.tasksPage.meetTask.MeetActivity;
import com.lawlett.taskmanageruikit.tasksPage.personalTask.PersonalActivity;
import com.lawlett.taskmanageruikit.tasksPage.workTask.WorkActivity;
import com.lawlett.taskmanageruikit.utils.App;
import com.lawlett.taskmanageruikit.utils.PassCodeActivity;


public class TodoFragment extends Fragment {
    ImageView personalImage, workImage, meetImage, homeImage, privateImage, doneImage;
    View dotsPerson, dotsWork, dotsMeet, dotsHome, dotsPrivate;
    TextView personal_amount, work_amount, meet_amount, home_amount, private_amount, done_amount;
    Integer doneAmount, personalAmount, workAmount, meetAmount, homeAmount, privateAmount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        personalImage = view.findViewById(R.id.person_image);
        workImage = view.findViewById(R.id.work_image);
        meetImage = view.findViewById(R.id.meet_image);
        homeImage = view.findViewById(R.id.home_image);
        privateImage = view.findViewById(R.id.private_image);
        doneImage = view.findViewById(R.id.done_tasks_image);

        personal_amount = view.findViewById(R.id.personal_amount);
        work_amount = view.findViewById(R.id.work_amount);
        meet_amount = view.findViewById(R.id.meet_task_amount);
        home_amount = view.findViewById(R.id.home_task_amount);
        private_amount = view.findViewById(R.id.private_task_amount);
        done_amount = view.findViewById(R.id.done_amount);






//        dotsPerson.setOnClickListener(v -> {
//            PopupMenu popupMenu = new PopupMenu(getContext(), dotsPerson);
//            popupMenu.getMenuInflater().inflate(R.menu.popupmenutodo, popupMenu.getMenu());
//            popupMenu.setOnMenuItemClickListener(item -> {
//                switch (item.getItemId()) {
//                    case R.id.delete_all_list:
//App.getDataBase().personalDao().deleteAll();
//
//                        break;
//                }
//                return false;
//            });
//            popupMenu.show();
//        });

        personalImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PersonalActivity.class));

            }
        });
        workImage.setOnClickListener(v -> startActivity(new Intent(getContext(), WorkActivity.class)));
        meetImage.setOnClickListener(v -> startActivity(new Intent(getContext(), MeetActivity.class)));
        homeImage.setOnClickListener(v -> startActivity(new Intent(getContext(), HomeActivity.class)));
        privateImage.setOnClickListener(v -> startActivity(new Intent(getContext(), PassCodeActivity.class)));
        doneImage.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), DoneTasksActivity.class));

        });
    }
    
    public void notifyView() {
        personal_amount.setText(personalAmount + "");
        work_amount.setText(workAmount + "");
        meet_amount.setText(meetAmount + "");
        home_amount.setText(homeAmount + "");
        private_amount.setText(privateAmount + "");
        done_amount.setText(doneAmount + "");
    }

    @Override
    public void onResume() {
        super.onResume();
        doneAmount = App.getDataBase().doneTaskDao().getAll().size();
        personalAmount = App.getDataBase().personalDao().getAll().size();
        workAmount = App.getDataBase().workDao().getAll().size();
        meetAmount = App.getDataBase().meetDao().getAll().size();
        homeAmount = App.getDataBase().homeDao().getAll().size();
        privateAmount = App.getDataBase().privateDao().getAll().size();

        notifyView();
    }

}
