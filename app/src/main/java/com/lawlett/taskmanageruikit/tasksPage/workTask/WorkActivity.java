package com.lawlett.taskmanageruikit.tasksPage.workTask;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.tasksPage.data.model.WorkModel;
import com.lawlett.taskmanageruikit.tasksPage.workTask.recycler.WorkAdapter;
import com.lawlett.taskmanageruikit.utils.App;

import java.util.ArrayList;
import java.util.List;

public class WorkActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    WorkAdapter adapter;
    EditText editText;
    WorkModel workModel;
    List<WorkModel> list ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        changeView();

        list = new ArrayList<>();

        App.getDataBase().workDao().getAllLive().observe(this, workModels -> {
            if (workModels != null) {
                list.clear();
                list.addAll(workModels);
                adapter.notifyDataSetChanged();
            }
        });

        recyclerView = findViewById(R.id.recycler_work);
        adapter = new WorkAdapter((ArrayList<WorkModel>) list);
        recyclerView.setAdapter(adapter);

        editText = findViewById(R.id.editText_work);



    }

    public void addWorkTask(View view) {
        recordDataRoom();
    }

    public void recordDataRoom() {
        workModel = new WorkModel(editText.getText().toString().trim());
        App.getDataBase().workDao().insert(workModel);
        editText.setText("");
    }
    public void changeView(){
        TextView toolbar = findViewById(R.id.toolbar_title);
        toolbar.setText("Работа");
        ImageView imageView = findViewById(R.id.personal_circle_image);
        ImageView imageView2 = findViewById(R.id.purple_circle_image);
        imageView.setVisibility(View.GONE);
        imageView2.setVisibility(View.VISIBLE);
    }
}
