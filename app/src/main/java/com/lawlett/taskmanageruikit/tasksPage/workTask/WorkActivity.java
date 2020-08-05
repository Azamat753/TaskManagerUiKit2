package com.lawlett.taskmanageruikit.tasksPage.workTask;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.tasksPage.data.model.WorkModel;
import com.lawlett.taskmanageruikit.tasksPage.workTask.recycler.WorkAdapter;
import com.lawlett.taskmanageruikit.utils.App;
import com.lawlett.taskmanageruikit.utils.WorkDoneSizePreference;

import java.util.ArrayList;
import java.util.List;

public class WorkActivity extends AppCompatActivity implements WorkAdapter.IWCheckedListener {
    WorkAdapter adapter;
    EditText editText;
    WorkModel workModel;
    List<WorkModel> list;
    int pos, previousData, currentData, updateData;
    ImageView workBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setNavigationBarColor(getResources().getColor(R.color.black));

        changeView();

        list = new ArrayList<>();
        adapter = new WorkAdapter(this);

        App.getDataBase().workDao().getAllLive().observe(this, workModels -> {
            if (workModels != null) {
                list.clear();
                list.addAll(workModels);
                adapter.updateList(list);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_work);
        recyclerView.setAdapter(adapter);

        editText = findViewById(R.id.editText_work);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                pos = viewHolder.getAdapterPosition();
                workModel = list.get(pos);
                workModel.isDone = false;

                decrementDone();

                App.getDataBase().workDao().delete(list.get(pos));
                adapter.notifyDataSetChanged();
                Toast.makeText(WorkActivity.this, "Удалено", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        workBack = findViewById(R.id.personal_back);
        workBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void addWorkTask(View view) {
        recordDataRoom();
    }

    public void recordDataRoom() {
        if (editText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Пусто", Toast.LENGTH_SHORT).show();
        } else {
            workModel = new WorkModel(editText.getText().toString().trim(), false);
            App.getDataBase().workDao().insert(workModel);
            editText.setText("");
        }
    }

    public void changeView() {
        TextView toolbar = findViewById(R.id.toolbar_title);
        toolbar.setText("Работа");
        ImageView imageView = findViewById(R.id.personal_circle_image);
        ImageView imageView2 = findViewById(R.id.purple_circle_image);
        imageView.setVisibility(View.GONE);
        imageView2.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemCheckClick(int id) {
        workModel = list.get(id);
        if (!workModel.isDone) {
            workModel.isDone = true;
            incrementDone();
        } else {
            workModel.isDone = false;
            decrementDone();
        }
        App.getDataBase().workDao().update(list.get(id));
    }

    private void incrementDone() {
        previousData = WorkDoneSizePreference.getInstance(this).getDataSize();
        WorkDoneSizePreference.getInstance(this).saveDataSize(previousData + 1);
    }

    private void decrementDone() {
        currentData = WorkDoneSizePreference.getInstance(this).getDataSize();
        updateData = currentData - 1;
        WorkDoneSizePreference.getInstance(this).saveDataSize(updateData);
    }
}

