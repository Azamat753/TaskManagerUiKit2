package com.lawlett.taskmanageruikit.quick;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.lawlett.data.model.QuickModel;
import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.utils.App;

public class QuickActivity extends AppCompatActivity {
    EditText e_title, e_description;
    ImageView back_view, done_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick);
        e_title = findViewById(R.id.edit_title);
        e_description = findViewById(R.id.edit_description);
        back_view = findViewById(R.id.back_view);
        done_view = findViewById(R.id.done_view);

        done_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textTitle = e_title.getText().toString();
                String textDescription = e_description.getText().toString();

                QuickModel quickModel = new QuickModel(textTitle,textDescription);
                App.getDataBase().taskDao().insert(quickModel);
                finish();
            }
        });
    }

}
