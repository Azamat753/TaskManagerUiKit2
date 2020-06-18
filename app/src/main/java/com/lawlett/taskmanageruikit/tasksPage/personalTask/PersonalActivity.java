package com.lawlett.taskmanageruikit.tasksPage.personalTask;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.tasksPage.data.model.PersonalModel;
import com.lawlett.taskmanageruikit.tasksPage.personalTask.recyclerview.PersonalAdapter;
import com.lawlett.taskmanageruikit.utils.App;

import java.util.ArrayList;
import java.util.List;

public class PersonalActivity extends AppCompatActivity {
    EditText editText;
    PersonalAdapter adapter;
    PersonalModel personalModel;
    List<PersonalModel> list;
    String personal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        list= new ArrayList<>();

        App.getDataBase().personalDao().getAllLive().observe(this, personalModels -> {
            if (personalModels!=null){
                list.clear();
                list.addAll(personalModels);
                adapter.notifyDataSetChanged();
                Log.e("personal", "onChanged: "+personalModels );
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_personal);
        adapter = new PersonalAdapter((ArrayList<PersonalModel>) list);
        recyclerView.setAdapter(adapter);

        editText = findViewById(R.id.editText_personal);

    }

    public void addPersonalTask(View view) {
        recordDataRoom();
    }

    public void recordDataRoom() {
       personal = editText.getText().toString().trim();
        personalModel=new PersonalModel(personal);
        App.getDataBase().personalDao().insert(personalModel);
        editText.setText("");
    }
}