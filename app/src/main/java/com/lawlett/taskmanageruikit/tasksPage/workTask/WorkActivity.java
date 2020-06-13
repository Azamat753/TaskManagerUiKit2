package com.lawlett.taskmanageruikit.tasksPage.workTask;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.tasksPage.data.model.TaskModel;
import com.lawlett.taskmanageruikit.tasksPage.workTask.recycler.WorkAdapter;
import com.lawlett.taskmanageruikit.utils.WorkStorage;

import java.util.ArrayList;

public class WorkActivity extends AppCompatActivity {
RecyclerView recyclerView;
WorkAdapter adapter;
EditText editText;
TaskModel taskModel = new TaskModel();
ArrayList<TaskModel> list= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        TextView toolbar= findViewById(R.id.toolbar_title);
        toolbar.setText("Работа");
        ImageView imageView = findViewById(R.id.personal_circle_image);
        ImageView imageView2 = findViewById(R.id.purple_circle_image);
        imageView.setVisibility(View.GONE);
        imageView2.setVisibility(View.VISIBLE);

        recyclerView=findViewById(R.id.recycler_work);
        adapter= new WorkAdapter(list);
        recyclerView.setAdapter(adapter);

        editText= findViewById(R.id.editText_work);

        list.addAll(WorkStorage.read(this));
        adapter.notifyDataSetChanged();
    }

    public void addWorkTask(View view) {
        taskModel.workTask=editText.getText().toString().trim();
        list.add(taskModel);
        adapter.notifyDataSetChanged();
        WorkStorage.save(list,this);
        editText.setText("");
    }
}
