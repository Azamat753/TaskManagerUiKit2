package com.lawlett.taskmanageruikit.tasksPage.meetTask;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.tasksPage.data.model.TaskModel;
import com.lawlett.taskmanageruikit.tasksPage.meetTask.recyclerview.MeetAdapter;
import com.lawlett.taskmanageruikit.utils.MeetStorage;

import java.util.ArrayList;

public class MeetActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MeetAdapter adapter;
    private ArrayList<TaskModel> list = new ArrayList<>();
    EditText editText;
    TaskModel taskModel = new TaskModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet);
        TextView toolbar = findViewById(R.id.toolbar_title);
        toolbar.setText("Встречи");
        ImageView imageView = findViewById(R.id.personal_circle_image);
        ImageView imageView2 = findViewById(R.id.orange_circle_image);
        imageView.setVisibility(View.GONE);
        imageView2.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.recycler_meet);
        adapter = new MeetAdapter(list);
        recyclerView.setAdapter(adapter);
        editText = findViewById(R.id.editText_meet);

        list.addAll(MeetStorage.read(this));
        adapter.notifyDataSetChanged();
    }

    public void addMeetTask(View view) {
        taskModel.meetTask = editText.getText().toString().trim();
        list.add(taskModel);
        adapter.notifyDataSetChanged();
        MeetStorage.save(list, this);
        editText.setText("");
    }
}
