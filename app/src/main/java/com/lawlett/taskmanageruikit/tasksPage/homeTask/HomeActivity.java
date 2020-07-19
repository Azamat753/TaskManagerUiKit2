package com.lawlett.taskmanageruikit.tasksPage.homeTask;

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
import com.lawlett.taskmanageruikit.tasksPage.data.model.HomeModel;
import com.lawlett.taskmanageruikit.tasksPage.homeTask.recycler.HomeAdapter;
import com.lawlett.taskmanageruikit.todo.TodoFragment;
import com.lawlett.taskmanageruikit.utils.App;
import com.lawlett.taskmanageruikit.utils.IHomeOnClickListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements IHomeOnClickListener {
    RecyclerView recyclerView;
    HomeAdapter adapter;
    ArrayList<HomeModel> list;
    HomeModel homeModel;
    DoneModel doneModel;
    EditText editText;
int pos;
TodoFragment todoFragment;
ImageView homeBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        changeView();

        list = new ArrayList<>();

        App.getDataBase().homeDao().getAllLive().observe(this, homeModels -> {
            if (homeModels != null) {
                list.clear();
                list.addAll(homeModels);
                adapter.notifyDataSetChanged();
            }
        });


        recyclerView = findViewById(R.id.recycler_home);
        adapter = new HomeAdapter(list, this, this);
        recyclerView.setAdapter(adapter);

        editText = findViewById(R.id.editText_home);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                pos= viewHolder.getAdapterPosition();
                App.getDataBase().homeDao().delete(list.get(pos));
                adapter.notifyDataSetChanged();
                Toast.makeText(HomeActivity.this, "Удалено", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        homeBack=findViewById(R.id.personal_back);
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
            homeModel = new HomeModel(editText.getText().toString().trim());
            App.getDataBase().homeDao().insert(homeModel);
            editText.setText("");
        }
    }
    public void changeView() {
        TextView toolbar = findViewById(R.id.toolbar_title);
        toolbar.setText("Дом");
        ImageView imageView = findViewById(R.id.personal_circle_image);
        ImageView imageView2 = findViewById(R.id.purple_circle_image);
        imageView.setVisibility(View.GONE);
        imageView2.setVisibility(View.VISIBLE);

    }

    @Override
    public void onItemLongClick(int position) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Вы выполнили задачу ?").setMessage("Убрать задачу")
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                doneModel = new DoneModel("Дом", list.get(0).homeTask, R.color.color1);
                App.getDataBase().doneTaskDao().insert(doneModel);
                App.getDataBase().homeDao().delete(list.get(position));
                adapter.notifyDataSetChanged();
            }
        }).show();
    }
}
