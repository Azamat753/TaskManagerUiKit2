package com.lawlett.taskmanageruikit.tasksPage.homeTask;

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
import com.lawlett.taskmanageruikit.tasksPage.data.model.HomeModel;
import com.lawlett.taskmanageruikit.tasksPage.homeTask.recycler.HomeAdapter;
import com.lawlett.taskmanageruikit.utils.App;
import com.lawlett.taskmanageruikit.utils.HomeDoneSizePreference;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements HomeAdapter.IHCheckedListener {
    RecyclerView recyclerView;
    HomeAdapter adapter;
    List<HomeModel> list;
    HomeModel homeModel;
    EditText editText;
    int pos, previousData,currentData,updateData;
    ImageView homeBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setNavigationBarColor(getResources().getColor(R.color.black));

        changeView();

        list = new ArrayList<>();
        adapter = new HomeAdapter(this);

        App.getDataBase().homeDao().getAllLive().observe(this, homeModels -> {
            if (homeModels != null) {
                list.clear();
                list.addAll(homeModels);
                adapter.updateList(list);
            }
        });


        recyclerView = findViewById(R.id.recycler_home);
        recyclerView.setAdapter(adapter);

        editText = findViewById(R.id.editText_home);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                pos = viewHolder.getAdapterPosition();
                homeModel = list.get(pos);
                if (!homeModel.isDone) {
                    App.getDataBase().homeDao().delete(list.get(pos));
                } else {
                    decrementDone();

                    App.getDataBase().homeDao().update(list.get(pos));
                    App.getDataBase().homeDao().delete(list.get(pos));
                    adapter.notifyDataSetChanged();
                    Toast.makeText(HomeActivity.this, "Удалено", Toast.LENGTH_SHORT).show();
                }
            }
        }).attachToRecyclerView(recyclerView);

        homeBack = findViewById(R.id.personal_back);
        homeBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void addHomeTask(View view) {
        recordRoom();
    }

    private void recordRoom() {
        if (editText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Пусто", Toast.LENGTH_SHORT).show();
        } else {
            homeModel = new HomeModel(editText.getText().toString().trim(),false);
            App.getDataBase().homeDao().insert(homeModel);
            editText.setText("");
        }
    }

    public void changeView() {
        TextView toolbar = findViewById(R.id.toolbar_title);
        toolbar.setText("Дом");
        ImageView imageView = findViewById(R.id.personal_circle_image);
        ImageView imageView2 = findViewById(R.id.blue_circle_image);
        imageView.setVisibility(View.GONE);
        imageView2.setVisibility(View.VISIBLE);

    }

    @Override
    public void onItemCheckClick(int id) {
        homeModel = list.get(id);
        if (!homeModel.isDone) {
            homeModel.isDone = true;
            incrementDone();
        } else {
            homeModel.isDone = false;
            decrementDone();
        }
        App.getDataBase().homeDao().update(list.get(id));
    }
    private void incrementDone() {
        previousData = HomeDoneSizePreference.getInstance(this).getDataSize();
        HomeDoneSizePreference.getInstance(this).saveDataSize(previousData + 1);
    }

    private void decrementDone() {
        currentData = HomeDoneSizePreference.getInstance(this).getDataSize();
        updateData = currentData - 1;
        HomeDoneSizePreference.getInstance(this).saveDataSize(updateData);
    }
    }