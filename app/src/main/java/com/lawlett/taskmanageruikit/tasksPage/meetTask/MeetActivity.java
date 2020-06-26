package com.lawlett.taskmanageruikit.tasksPage.meetTask;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.tasksPage.data.model.MeetModel;
import com.lawlett.taskmanageruikit.tasksPage.meetTask.recyclerview.MeetAdapter;
import com.lawlett.taskmanageruikit.utils.App;
import com.lawlett.taskmanageruikit.utils.IMeetOnClickListener;

import java.util.ArrayList;

public class MeetActivity extends AppCompatActivity implements IMeetOnClickListener {
    RecyclerView recyclerView;
    MeetAdapter adapter;
    private ArrayList<MeetModel> list;
    EditText editText;
    MeetModel meetModel ;

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
        adapter = new MeetAdapter(list,this,this);
        recyclerView.setAdapter(adapter);
        editText = findViewById(R.id.editText_meet);

    }

    public void addMeetTask(View view) {
recordRoom();
    }
    public void recordRoom(){
        meetModel=new MeetModel(editText.getText().toString().trim());
        App.getDataBase().meetDao().insert(meetModel);
    }
    public void changeView(){
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
        dialog.setTitle("Вы хотите удалить ?").setMessage("Удалить задачу")
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                App.getDataBase().meetDao().delete(list.get(position));
                adapter.notifyDataSetChanged();
            }
        }).show();
    }
}
