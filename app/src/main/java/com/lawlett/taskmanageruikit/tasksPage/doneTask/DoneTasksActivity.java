package com.lawlett.taskmanageruikit.tasksPage.doneTask;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.tasksPage.data.model.DoneModel;
import com.lawlett.taskmanageruikit.tasksPage.doneTask.recycler.DoneAdapter;
import com.lawlett.taskmanageruikit.utils.App;

import java.util.ArrayList;
import java.util.List;

public class DoneTasksActivity extends AppCompatActivity {
RecyclerView recyclerView;
DoneAdapter adapter;
List<DoneModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done_tasks);
        changeView();
        list=new ArrayList<>();

        App.getDataBase().doneTaskDao().getAllLive().observe(this, doneModels -> {
            list.clear();
            list.addAll(doneModels);
            adapter.notifyDataSetChanged();
        });

        recyclerView=findViewById(R.id.done_recyclerview);
        adapter= new DoneAdapter(list);
    }

    public void changeView(){
        TextView toolbar = findViewById(R.id.toolbar_title);
        toolbar.setText("Выполненные");
        ImageView imageView = findViewById(R.id.personal_circle_image);
        ImageView imageView2 = findViewById(R.id.orange_circle_image);
        imageView.setVisibility(View.GONE);
        imageView2.setVisibility(View.VISIBLE);
    }
}