package com.lawlett.taskmanageruikit.tasksPage.doneTask;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.tasksPage.data.model.DoneModel;
import com.lawlett.taskmanageruikit.tasksPage.doneTask.recycler.DoneAdapter;
import com.lawlett.taskmanageruikit.utils.App;
import com.lawlett.taskmanageruikit.utils.IDoneOnClickListener;

import java.util.ArrayList;
import java.util.List;

public class DoneTasksActivity extends AppCompatActivity implements IDoneOnClickListener {
    RecyclerView recyclerView;
    DoneAdapter adapter;
    List<DoneModel> list;
    int pos;
    ImageView doneBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done_tasks);
        changeView();
        list = new ArrayList<>();

        App.getDataBase().doneTaskDao().getAllLive().observe(this, doneModels -> {
            list.clear();
            list.addAll(doneModels);
            adapter.notifyDataSetChanged();
        });

        recyclerView = findViewById(R.id.done_recyclerview);
        adapter = new DoneAdapter(list, this, this);
        recyclerView.setAdapter(adapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                pos = viewHolder.getAdapterPosition();
                App.getDataBase().doneTaskDao().delete(list.get(pos));
                adapter.notifyDataSetChanged();
                Toast.makeText(DoneTasksActivity.this, "Удалено", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);
        doneBack=findViewById(R.id.personal_back);
        doneBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void changeView() {
        TextView toolbar = findViewById(R.id.toolbar_title);
        toolbar.setText("Выполненные");
        ImageView imageView = findViewById(R.id.personal_circle_image);
        ImageView imageView2 = findViewById(R.id.green_circle_image);
        imageView.setVisibility(View.GONE);
        imageView2.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemLongClick(int position) {

    }
}
