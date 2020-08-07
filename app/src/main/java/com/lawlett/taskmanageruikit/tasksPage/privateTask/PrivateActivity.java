package com.lawlett.taskmanageruikit.tasksPage.privateTask;

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
import com.lawlett.taskmanageruikit.tasksPage.data.model.PrivateModel;
import com.lawlett.taskmanageruikit.tasksPage.privateTask.recycler.PrivateAdapter;
import com.lawlett.taskmanageruikit.utils.App;
import com.lawlett.taskmanageruikit.utils.PrivateDoneSizePreference;

import java.util.ArrayList;

public class PrivateActivity extends AppCompatActivity implements PrivateAdapter.IPCheckedListener {
    RecyclerView recyclerView;
    PrivateAdapter adapter;
    ArrayList<PrivateModel> list;
    EditText editText;
    PrivateModel privateModel;
    int pos, previousData, currentData, updateData;
    ImageView privateBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private);

        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setNavigationBarColor(getResources().getColor(R.color.black));

        changeView();

        list = new ArrayList<>();
        adapter = new PrivateAdapter(this);

        App.getDataBase().privateDao().getAllLive().observe(this, privateModels -> {
            if (privateModels != null) {
                list.clear();
                list.addAll(privateModels);
                adapter.updateList(list);
            }
        });

        recyclerView = findViewById(R.id.recycler_private);
        recyclerView.setAdapter(adapter);

        editText = findViewById(R.id.editText_private);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                pos = viewHolder.getAdapterPosition();
                privateModel = list.get(pos);
                if (!privateModel.isDone) {
                    App.getDataBase().privateDao().delete(list.get(pos));
                } else {
                    decrementDone();

                    App.getDataBase().privateDao().update(list.get(pos));
                    App.getDataBase().privateDao().delete(list.get(pos));
                    adapter.notifyDataSetChanged();
                    Toast.makeText(PrivateActivity.this, "Удалено", Toast.LENGTH_SHORT).show();
                }
            }
        }).attachToRecyclerView(recyclerView);

        privateBack = findViewById(R.id.personal_back);
        privateBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void addPrivateTask(View view) {
        recordRoom();
    }

    public void recordRoom() {
        if (editText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Пусто", Toast.LENGTH_SHORT).show();
        } else {
            privateModel = new PrivateModel(editText.getText().toString().trim(), false);
            App.getDataBase().privateDao().insert(privateModel);
            editText.setText("");
        }
    }

    public void changeView() {
        TextView toolbar = findViewById(R.id.toolbar_title);
        toolbar.setText("Приватные");
        ImageView imageView = findViewById(R.id.personal_circle_image);
        ImageView imageView2 = findViewById(R.id.red_circle_image);
        imageView.setVisibility(View.GONE);
        imageView2.setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemCheckClick(int id) {
        privateModel = list.get(id);
        if (!privateModel.isDone) {
            privateModel.isDone = true;
            incrementDone();
        } else {
            privateModel.isDone = false;
            decrementDone();
        }
        App.getDataBase().privateDao().update(list.get(id));
    }

    private void incrementDone() {
        previousData = PrivateDoneSizePreference.getInstance(this).getDataSize();
        PrivateDoneSizePreference.getInstance(this).saveDataSize(previousData + 1);
    }

    private void decrementDone() {
        currentData = PrivateDoneSizePreference.getInstance(this).getDataSize();
        updateData = currentData - 1;
        PrivateDoneSizePreference.getInstance(this).saveDataSize(updateData);
    }
}