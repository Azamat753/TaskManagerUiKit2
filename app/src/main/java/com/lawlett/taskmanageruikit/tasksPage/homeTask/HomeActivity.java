package com.lawlett.taskmanageruikit.tasksPage.homeTask;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.tasksPage.data.model.HomeModel;
import com.lawlett.taskmanageruikit.tasksPage.homeTask.recycler.HomeAdapter;
import com.lawlett.taskmanageruikit.utils.App;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
RecyclerView recyclerView;
HomeAdapter adapter;
ArrayList<HomeModel>list ;
HomeModel homeModel ;
EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        changeView();

        list=new ArrayList<>();

        App.getDataBase().homeDao().getAllLive().observe(this, homeModels -> {
            if (homeModels != null) {
                list.clear();
                list.addAll(homeModels);
                adapter.notifyDataSetChanged();
            }
        });


            recyclerView=findViewById(R.id.recycler_home);
        adapter = new HomeAdapter(list);
        recyclerView.setAdapter(adapter);

        editText=findViewById(R.id.editText_home);


    }

    public void addHomeTask(View view) {
recordRoom();
    }

    private void recordRoom() {
        homeModel=new HomeModel(editText.getText().toString().trim());
        App.getDataBase().homeDao().insert(homeModel);
    }

    public void changeView(){
        TextView toolbar= findViewById(R.id.toolbar_title);
        toolbar.setText("Дом");
        ImageView imageView = findViewById(R.id.personal_circle_image);
        ImageView imageView2 = findViewById(R.id.purple_circle_image);
        imageView.setVisibility(View.GONE);
        imageView2.setVisibility(View.VISIBLE);

    }
}
