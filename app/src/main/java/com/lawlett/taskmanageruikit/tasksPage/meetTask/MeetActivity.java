package com.lawlett.taskmanageruikit.tasksPage.meetTask;

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
import com.lawlett.taskmanageruikit.tasksPage.data.model.MeetModel;
import com.lawlett.taskmanageruikit.tasksPage.meetTask.recyclerview.MeetAdapter;
import com.lawlett.taskmanageruikit.utils.App;
import com.lawlett.taskmanageruikit.utils.MeetDoneSizePreference;

import java.util.ArrayList;
import java.util.List;

public class MeetActivity extends AppCompatActivity implements MeetAdapter.IMCheckedListener {
    RecyclerView recyclerView;
    MeetAdapter adapter;
    private List<MeetModel> list;
    EditText editText;
    MeetModel meetModel;
    int position, currentData, updateData, previousData;
    ImageView meetBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet);
        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setNavigationBarColor(getResources().getColor(R.color.black));

        changeView();

        list = new ArrayList<>();
        adapter = new MeetAdapter(this);

        App.getDataBase().meetDao().getAllLive().observe(this, meetModels -> {
            if (meetModels != null) {
                list.clear();
                list.addAll(meetModels);
                adapter.updateList(list);
            }
        });


        recyclerView = findViewById(R.id.recycler_meet);
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
                meetModel = list.get(position);
                meetModel.isDone = false;

                decrementDone();

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
            meetModel = new MeetModel(editText.getText().toString().trim(), false);
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
    public void onItemCheckClick(int id) {
        meetModel = list.get(id);
        if (!meetModel.isDone) {
            meetModel.isDone = true;
            incrementDone();
        } else {
            meetModel.isDone = false;
            decrementDone();
        }
        App.getDataBase().meetDao().update(list.get(id));
    }

    private void incrementDone() {
        previousData = MeetDoneSizePreference.getInstance(this).getDataSize();
        MeetDoneSizePreference.getInstance(this).saveDataSize(previousData + 1);
    }

    private void decrementDone() {
        currentData = MeetDoneSizePreference.getInstance(this).getDataSize();
        updateData = currentData - 1;
        MeetDoneSizePreference.getInstance(this).saveDataSize(updateData);
    }
}
