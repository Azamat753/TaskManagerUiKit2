package com.lawlett.taskmanageruikit.tasksPage.meetTask;

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
import com.lawlett.taskmanageruikit.tasksPage.data.model.MeetModel;
import com.lawlett.taskmanageruikit.tasksPage.meetTask.recyclerview.MeetAdapter;
import com.lawlett.taskmanageruikit.utils.App;
import com.lawlett.taskmanageruikit.utils.IMeetOnClickListener;
import com.lawlett.taskmanageruikit.utils.MeetSizePreference;

import java.util.ArrayList;

public class MeetActivity extends AppCompatActivity implements IMeetOnClickListener {
    RecyclerView recyclerView;
    MeetAdapter adapter;
    private ArrayList<MeetModel> list;
    EditText editText;
    MeetModel meetModel;
    DoneModel doneModel;
    int position,counter=0;
    ImageView meetBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet);

        changeView();

        list = new ArrayList<>();

        App.getDataBase().meetDao().getAllLive().observe(this, meetModels -> {
            if (meetModels != null) {
                list.clear();
                list.addAll(meetModels);
                adapter.notifyDataSetChanged();
            }
        });


        recyclerView = findViewById(R.id.recycler_meet);
        adapter = new MeetAdapter(list, this, this);
        recyclerView.setAdapter(adapter);
        editText = findViewById(R.id.editText_meet);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                position = viewHolder.getAdapterPosition();
                App.getDataBase().meetDao().delete(list.get(position));
                adapter.notifyDataSetChanged();
                Toast.makeText(MeetActivity.this, "Удалено", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        meetBack = findViewById(R.id.personal_back);
        meetBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void addMeetTask(View view) {
        recordRoom();
    }

    public void recordRoom() {
        if (editText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Пусто", Toast.LENGTH_SHORT).show();
        } else {
            meetModel = new MeetModel(editText.getText().toString().trim());
            App.getDataBase().meetDao().insert(meetModel);
            editText.setText("");
        }
    }

    public void changeView() {
        TextView toolbar = findViewById(R.id.toolbar_title);
        toolbar.setText("Встречи");
        ImageView imageView = findViewById(R.id.personal_circle_image);
        ImageView imageView2 = findViewById(R.id.orange_circle_image);
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

                doneModel = new DoneModel("Встречи", list.get(0).meetTask, R.color.color2);
                App.getDataBase().doneTaskDao().insert(doneModel);
                App.getDataBase().meetDao().delete(list.get(position));
                adapter.notifyDataSetChanged();

                counter= MeetSizePreference.getInstance(MeetActivity.this).getMeetSize()+1;
                MeetSizePreference.getInstance(MeetActivity.this).saveMeetSize(counter);
            }
        }).show();
    }

}
