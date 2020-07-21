package com.lawlett.taskmanageruikit.tasksPage.workTask;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.lawlett.taskmanageruikit.tasksPage.data.model.DoneModel;
import com.lawlett.taskmanageruikit.tasksPage.data.model.WorkModel;
import com.lawlett.taskmanageruikit.tasksPage.workTask.recycler.WorkAdapter;
import com.lawlett.taskmanageruikit.utils.App;
import com.lawlett.taskmanageruikit.utils.IWorkOnClickListener;
import com.lawlett.taskmanageruikit.utils.WorkSizePreference;

import java.util.ArrayList;
import java.util.List;

public class WorkActivity extends AppCompatActivity implements IWorkOnClickListener {
    RecyclerView recyclerView;
    WorkAdapter adapter;
    EditText editText;
    WorkModel workModel;
    DoneModel doneModel;
    List<WorkModel> list;
    int pos;
    ImageView workBack;
    int counter=0;

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
        adapter = new WorkAdapter((ArrayList<WorkModel>) list, this, this);
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
            workModel = new WorkModel(editText.getText().toString().trim());
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
    public void onItemLongClick(int position) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Вы выполнили задачу ?").setMessage("Убрать задачу")
                .setNegativeButton("Нет", (dialog1, which) -> dialog1.cancel()).setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                doneModel = new DoneModel("Работа", list.get(0).workTask, R.color.color10);
                App.getDataBase().doneTaskDao().insert(doneModel);
                App.getDataBase().workDao().delete(list.get(position));
                adapter.notifyDataSetChanged();


                counter= WorkSizePreference.getInstance(WorkActivity.this).getWorkSize()+1;
                WorkSizePreference.getInstance(WorkActivity.this).saveWorkSize(counter);
            }
        }).show();
    }
}
