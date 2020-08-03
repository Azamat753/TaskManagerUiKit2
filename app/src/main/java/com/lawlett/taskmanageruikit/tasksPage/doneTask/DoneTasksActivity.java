package com.lawlett.taskmanageruikit.tasksPage.doneTask;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.tasksPage.data.done_model.HomeDoneModel;
import com.lawlett.taskmanageruikit.tasksPage.data.done_model.MeetDoneModel;
import com.lawlett.taskmanageruikit.tasksPage.data.done_model.PersonalDoneModel;
import com.lawlett.taskmanageruikit.tasksPage.data.done_model.PrivateDoneModel;
import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.tasksPage.data.done_model.WorkDoneModel;
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
    List<PersonalDoneModel> listPersonal;
    List<WorkDoneModel> listWork;
    List<MeetDoneModel> listMeet;
    List<HomeDoneModel> listHome;
    List<PrivateDoneModel> listPrivate;
    int pos;
    ImageView doneBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done_tasks);

        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setNavigationBarColor(getResources().getColor(R.color.black));


        changeView();
        list = new ArrayList<>();

        App.getDataBase().doneTaskDao().getAllLive().observe(this, doneModels -> {
            list.clear();
            list.addAll(doneModels);
            adapter.notifyDataSetChanged();
        });

        getPersonal();
        getWork();
        getMeet();
        getHome();
        getPrivate();

        recyclerView = findViewById(R.id.done_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
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

                if (list.get(pos).doneTitle.equals("Встречи")) {
                    App.getDataBase().meetDoneTaskDao().delete(listMeet.get(pos));
                }
                else if (list.get(0).doneTitle.equals("Персональные")){
                    App.getDataBase().personalDoneTaskDao().delete(listPersonal.get(0));
                }else if (list.get(0).doneTitle.equals("Работа")){
                    App.getDataBase().workDoneTaskDao().delete(listWork.get(0));
                }else if (list.get(0).doneTitle.equals("Дом")){
                    App.getDataBase().homeDoneTaskDao().delete(listHome.get(0));
                }else if (list.get(0).doneTitle.equals("Приватные")){
                    App.getDataBase().privateDoneTaskDao().delete(listPrivate.get(0));
                }
            }
        }).attachToRecyclerView(recyclerView);
        doneBack = findViewById(R.id.personal_back);
        doneBack.setOnClickListener(v -> onBackPressed());
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

    public void getPersonal() {
        listPersonal = new ArrayList<>();
        App.getDataBase().personalDoneTaskDao().getAllLive().observe(this, personalDoneModels -> {
            listPersonal.clear();
            listPersonal.addAll(personalDoneModels);
        });
    }

    public void getWork() {
        listWork = new ArrayList<>();
        App.getDataBase().workDoneTaskDao().getAllLive().observe(this, workDoneModels -> {
            listWork.clear();
            listWork.addAll(workDoneModels);
        });
    }

    public void getMeet() {
        listMeet = new ArrayList<>();
        App.getDataBase().meetDoneTaskDao().getAllLive().observe(this, meetDoneModels -> {
            listMeet.clear();
            listMeet.addAll(meetDoneModels);
        });
    }

    public void getHome() {
        listHome = new ArrayList<>();
        App.getDataBase().homeDoneTaskDao().getAllLive().observe(this, homeDoneModels -> {
            listHome.clear();
            listHome.addAll(homeDoneModels);
        });
    }

    public void getPrivate() {
        listPrivate = new ArrayList<>();
        App.getDataBase().privateDoneTaskDao().getAllLive().observe(this, privateDoneModels -> {
            listPrivate.clear();
            listPrivate.addAll(privateDoneModels);
        });
    }

}
