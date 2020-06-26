package com.lawlett.taskmanageruikit.tasksPage.privateTask;

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
import com.lawlett.taskmanageruikit.tasksPage.data.model.PrivateModel;
import com.lawlett.taskmanageruikit.tasksPage.privateTask.recycler.PrivateAdapter;
import com.lawlett.taskmanageruikit.utils.App;
import com.lawlett.taskmanageruikit.utils.IPrivateOnClickListener;

import java.util.ArrayList;

public class PrivateActivity extends AppCompatActivity implements IPrivateOnClickListener {
RecyclerView recyclerView;
PrivateAdapter adapter;
ArrayList<PrivateModel> list;
EditText editText;
PrivateModel privateModel ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_private);
     changeView();

     list= new ArrayList<>();

     App.getDataBase().privateDao().getAllLive().observe(this, privateModels -> {
         if (privateModels!=null){
             list.clear();
             list.addAll(privateModels);
             adapter.notifyDataSetChanged();
         }
     });

        recyclerView= findViewById(R.id.recycler_private);
        adapter= new PrivateAdapter(list,this,this);
        recyclerView.setAdapter(adapter);

        editText=findViewById(R.id.editText_private);


    }

    public void addPrivateTask(View view) {
        recordRoom();
    }
    public void recordRoom(){
        privateModel=new PrivateModel(editText.getText().toString().trim());
        App.getDataBase().privateDao().insert(privateModel);
    }
    public void changeView(){
        TextView toolbar = findViewById(R.id.toolbar_title);
        toolbar.setText("Частное");
        ImageView imageView = findViewById(R.id.personal_circle_image);
        ImageView imageView2 = findViewById(R.id.red_circle_image);
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
                App.getDataBase().privateDao().delete(list.get(position));
                adapter.notifyDataSetChanged();
            }
        }).show();
    }
}
