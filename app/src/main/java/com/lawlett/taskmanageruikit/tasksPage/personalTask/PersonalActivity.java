package com.lawlett.taskmanageruikit.tasksPage.personalTask;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.tasksPage.data.done_model.PersonalDoneModel;
import com.lawlett.taskmanageruikit.tasksPage.data.model.DoneModel;
import com.lawlett.taskmanageruikit.tasksPage.data.model.PersonalModel;
import com.lawlett.taskmanageruikit.tasksPage.personalTask.recyclerview.PersonalAdapter;
import com.lawlett.taskmanageruikit.utils.App;

import java.util.ArrayList;
import java.util.List;

public class PersonalActivity extends AppCompatActivity implements PersonalAdapter.ICheckedListener {
    EditText editText;
    PersonalAdapter adapter;
    PersonalModel personalModel;
    DoneModel doneModel;
    PersonalDoneModel personalDoneModel;
    List<PersonalModel> list;
    List<PersonalDoneModel> listDone;
    String personal;
    ImageView personalBack;
    int pos;
    int myId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setNavigationBarColor(getResources().getColor(R.color.black));

        list = new ArrayList<>();
        adapter = new PersonalAdapter(this);

        App.getDataBase().personalDao().getAllLive().observe(this, personalModels -> {
            if (personalModels != null) {
                list.clear();
                list.addAll(personalModels);
                adapter.updateList(list);
            }
        });

        listDone = new ArrayList<>();

        App.getDataBase().personalDoneTaskDao().getAllLive().observe(this, personalDoneModels -> {
            if (personalDoneModels != null) {
                listDone.clear();
                listDone.addAll(personalDoneModels);
            }
        });


        RecyclerView recyclerView = findViewById(R.id.recycler_personal);
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

                App.getDataBase().personalDoneTaskDao().delete(listDone.get(pos));

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
            personalModel = new PersonalModel(personal, false);
            App.getDataBase().personalDao().insert(personalModel);
            editText.setText("");
        }
    }

    @Override
    public void onItemCheckClick(int id) {
        personalModel = list.get(id);
        myId = id;
        if (!personalModel.isDone) {
            personalModel.isDone = true;
            PersonalDoneModel personalDoneModel;
            personalDoneModel = new PersonalDoneModel("1");
            App.getDataBase().personalDoneTaskDao().insert(personalDoneModel);
        } else {
            personalModel.isDone = false;
            App.getDataBase().personalDoneTaskDao().delete(listDone.get(id));
        }
        App.getDataBase().personalDao().update(list.get(id));
    }
}
