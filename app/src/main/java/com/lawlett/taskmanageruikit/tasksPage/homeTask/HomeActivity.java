package com.lawlett.taskmanageruikit.tasksPage.homeTask;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.tasksPage.data.model.TaskModel;
import com.lawlett.taskmanageruikit.tasksPage.homeTask.recycler.HomeAdapter;
import com.lawlett.taskmanageruikit.utils.HomeStorage;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
RecyclerView recyclerView;
HomeAdapter adapter;
ArrayList<TaskModel>list = new ArrayList<>();
TaskModel taskModel= new TaskModel();
EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        TextView toolbar= findViewById(R.id.toolbar_title);
        toolbar.setText("Дом");
        ImageView imageView = findViewById(R.id.personal_circle_image);
        ImageView imageView2 = findViewById(R.id.purple_circle_image);
        imageView.setVisibility(View.GONE);
        imageView2.setVisibility(View.VISIBLE);

        recyclerView=findViewById(R.id.recycler_home);
        adapter = new HomeAdapter(list);
        recyclerView.setAdapter(adapter);

        editText=findViewById(R.id.editText_home);

        list.addAll(HomeStorage.read(this));
        adapter.notifyDataSetChanged();
    }

    public void addHomeTask(View view) {
        taskModel.homeTask = editText.getText().toString().trim();
        list.add(taskModel);
        adapter.notifyDataSetChanged();
        HomeStorage.save(list, this);
        editText.setText("");
    }
}
