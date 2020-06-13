package com.lawlett.taskmanageruikit.tasksPage.privateTask;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.tasksPage.data.model.TaskModel;
import com.lawlett.taskmanageruikit.tasksPage.privateTask.recycler.PrivateAdapter;
import com.lawlett.taskmanageruikit.utils.PrivateStorage;

import java.util.ArrayList;

public class PrivateActivity extends AppCompatActivity {
RecyclerView recyclerView;
PrivateAdapter adapter;
ArrayList<TaskModel> list= new ArrayList<>();
EditText editText;
TaskModel taskModel= new TaskModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private);
        TextView toolbar = findViewById(R.id.toolbar_title);
        toolbar.setText("Частное");
        ImageView imageView = findViewById(R.id.personal_circle_image);
        ImageView imageView2 = findViewById(R.id.red_circle_image);
        imageView.setVisibility(View.GONE);
        imageView2.setVisibility(View.VISIBLE);

        recyclerView= findViewById(R.id.recycler_private);
        adapter= new PrivateAdapter(list);
        recyclerView.setAdapter(adapter);

        editText=findViewById(R.id.editText_private);

        list.addAll(PrivateStorage.read(this));
        adapter.notifyDataSetChanged();
    }

    public void addPrivateTask(View view) {
        taskModel.privateTask=editText.getText().toString().trim();
        list.add(taskModel);
        adapter.notifyDataSetChanged();
        PrivateStorage.save(list,this);
        editText.setText("");
    }
}
