package com.lawlett.taskmanageruikit.tasksPage.personalTask;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.tasksPage.data.model.DoneModel;
import com.lawlett.taskmanageruikit.tasksPage.data.model.PersonalModel;
import com.lawlett.taskmanageruikit.tasksPage.personalTask.recyclerview.PersonalAdapter;
import com.lawlett.taskmanageruikit.utils.App;
import com.lawlett.taskmanageruikit.utils.IPersonalOnClickListener;

import java.util.ArrayList;
import java.util.List;

public class PersonalActivity extends AppCompatActivity implements IPersonalOnClickListener {
    EditText editText;
    PersonalAdapter adapter;
    PersonalModel personalModel;
    DoneModel doneModel;
    List<PersonalModel> list;
    String personal;
    ImageView personalBack;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        list = new ArrayList<>();

        App.getDataBase().personalDao().getAllLive().observe(this, personalModels -> {
            if (personalModels != null) {
                list.clear();
                list.addAll(personalModels);
                adapter.notifyDataSetChanged();
                Log.e("personal", "onChanged: " + personalModels);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_personal);
        adapter = new PersonalAdapter((ArrayList<PersonalModel>) list, this, this);
        recyclerView.setAdapter(adapter);

        editText = findViewById(R.id.editText_personal);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                pos = viewHolder.getAdapterPosition();
                App.getDataBase().personalDao().delete(list.get(pos));
                adapter.notifyDataSetChanged();
                Toast.makeText(PersonalActivity.this, "Удалено", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        personalBack = findViewById(R.id.personal_back);
        personalBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
onBackPressed();
            }
        });
    }



    public void addPersonalTask(View view) {
        recordDataRoom();
    }

    public void recordDataRoom() {
        if (editText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Пусто", Toast.LENGTH_SHORT).show();
        } else {
            personal = editText.getText().toString().trim();
            personalModel = new PersonalModel(personal);
            App.getDataBase().personalDao().insert(personalModel);
            editText.setText("");
        }
    }

    @Override
    public void onItemLongClick(int position) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Вы выполнили задачу ?").setMessage("Убрать задачу")
                .setNegativeButton("Нет", (dialog1, which) -> dialog1.cancel()).setPositiveButton("Да", (dialog12, which) -> {

            doneModel = new DoneModel("Персональные", list.get(0).personalTask, R.color.color2);
            App.getDataBase().doneTaskDao().insert(doneModel);
            App.getDataBase().personalDao().delete(list.get(position));
            adapter.notifyDataSetChanged();

        }).show();
    }

}