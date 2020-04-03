package com.lawlett.taskmanageruikit.quick;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.quick.data.model.QuickModel;
import com.lawlett.taskmanageruikit.utils.App;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import petrov.kristiyan.colorpicker.ColorPicker;

public class QuickActivity extends AppCompatActivity {
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;

    EditText e_title, e_description;
    ImageView back_view, done_view, image_title;
    QuickModel quickModel;
    SharedPreferences preferences;
    String avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick);
        initView();

        getIncomingIntent();


        done_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textTitle = e_title.getText().toString();
                String textDescription = e_description.getText().toString();
                String currentDate = new SimpleDateFormat("dd ", Locale.getDefault()).format(new Date());
                String image = avatar;
                quickModel = new QuickModel(textTitle, textDescription, currentDate, image);
                App.getDataBase().taskDao().insert(quickModel);
                finish();


            }
        });

    }

    public void getIncomingIntent() {
        Intent intent = getIntent();
        quickModel = (QuickModel) intent.getSerializableExtra("task");
        if (quickModel != null) {
            e_title.setText(quickModel.getTitle());
            e_description.setText(quickModel.getDescription());

        }
    }

    public void initView() {
        materialDesignFAM = findViewById(R.id.menu_floating);
        floatingActionButton1 = findViewById(R.id.fab);
        floatingActionButton2 = findViewById(R.id.fab2);
        floatingActionButton3 = findViewById(R.id.fab3);
        image_title = findViewById(R.id.image_title);

        e_title = findViewById(R.id.edit_title);
        e_description = findViewById(R.id.edit_description);
        back_view = findViewById(R.id.back_view);
        done_view = findViewById(R.id.done_view);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ColorPicker colorPicker = new ColorPicker(QuickActivity.this);
                ArrayList<String> colors = new ArrayList<>();
                colors.add("#82B926");
                colors.add("#a276eb");
                colors.add("#6a3ab2");
                colors.add("#666666");
                colors.add("#FFFF00");
                colors.add("#3C8D2F");
                colors.add("#FA9F00");
                colors.add("#FF0000");
                colors.add("#03DAC5");
                colors.add("#005EFF");

                colorPicker
                        .setDefaultColorButton(Color.parseColor("#f84c44"))
                        .setColors(colors)
                        .setColumns(5)
                        .setRoundColorButton(true)
                        .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                            @Override
                            public void onChooseColor(int position, int color) {
                                Toast.makeText(QuickActivity.this, position + "", Toast.LENGTH_SHORT).show();
                                e_title.setTextColor(color);
                            }

                            @Override
                            public void onCancel() {

                            }
                        })
                        .addListenerButton("Попробовать", new ColorPicker.OnButtonListener() {
                            @Override
                            public void onClick(View v, int position, int color) {
                                Toast.makeText(QuickActivity.this, position + "", Toast.LENGTH_SHORT).show();
                                e_title.setTextColor(color);
                            }
                        }).show();
            }
        });
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(QuickActivity.this, "location", Toast.LENGTH_SHORT).show();
            }
        });
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 01);


            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 01 && resultCode == RESULT_OK) {

            final Uri imageUri = data.getData();
            avatar = imageUri.toString();
            Glide.with(this).load(avatar).into(image_title);


        }
    }
}

