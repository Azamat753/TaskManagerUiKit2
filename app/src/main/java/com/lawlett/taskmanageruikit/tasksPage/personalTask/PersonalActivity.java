package com.lawlett.taskmanageruikit.tasksPage.personalTask;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.tasksPage.data.model.TaskModel;
import com.lawlett.taskmanageruikit.tasksPage.personalTask.recyclerview.PersonalAdapter;
import com.lawlett.taskmanageruikit.utils.PersonalStorage;

import java.util.ArrayList;

public class PersonalActivity extends AppCompatActivity {
    EditText editText;
    PersonalAdapter adapter;
    private ArrayList<TaskModel> list = new ArrayList<>();
    TaskModel taskModel = new TaskModel();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        RecyclerView recyclerView = findViewById(R.id.recycler_personal);
        adapter = new PersonalAdapter(list);
        recyclerView.setAdapter(adapter);

        editText = findViewById(R.id.editText_personal);


list.addAll(PersonalStorage.read(this));
adapter.notifyDataSetChanged();
    }

    public void addPersonalTask(View view) {
        taskModel.personalTask = editText.getText().toString().trim();
        list.add(taskModel);
        adapter.notifyDataSetChanged();
        PersonalStorage.save(list, this);
        editText.setText("");
    }
}