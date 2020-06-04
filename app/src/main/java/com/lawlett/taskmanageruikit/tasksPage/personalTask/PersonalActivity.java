package com.lawlett.taskmanageruikit.tasksPage.personalTask;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.quick.data.model.QuickModel;
import com.lawlett.taskmanageruikit.tasksPage.personalTask.recyclerview.PersonalAdapter;
import com.lawlett.taskmanageruikit.utils.App;

import java.util.ArrayList;
import java.util.List;

public class PersonalActivity extends AppCompatActivity {
    EditText editText;
    PersonalAdapter adapter;
    String personalTask;
    QuickModel quickModel=new QuickModel();
    private List<QuickModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        list = new ArrayList<>();
        RecyclerView recyclerView = findViewById(R.id.recycler_personal);
        adapter = new PersonalAdapter(list);
        recyclerView.setAdapter(adapter);

        editText = findViewById(R.id.editText_personal);

        App.getDataBase().taskDao().getAllLive().observe(this, quickModels -> {
            list.clear();
            list.addAll(quickModels);
            adapter.notifyDataSetChanged();
        });
    }

    public void recordDataRoom() {
        personalTask = editText.getText().toString();
        quickModel.setPersonalTask(personalTask);
        App.getDataBase().taskDao().insert(quickModel);
    }
    public void addPersonalTask(View view) {
        recordDataRoom();
    }
}