package com.lawlett.taskmanageruikit.quick;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.quick.data.model.QuickModel;
import com.lawlett.taskmanageruikit.utils.App;
import com.lawlett.taskmanageruikit.utils.QuickViewModel;
import com.shivtechs.maplocationpicker.MapUtility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import petrov.kristiyan.colorpicker.ColorPicker;

public class QuickActivity extends AppCompatActivity {

    String userId;
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButtonColorPicker, floatingActionButtonLocationPicker, floatingActionButtonImagePicker;
    private QuickViewModel quickViewModel;
    QuickModel quickModel;
    EditText e_title, e_description;
    ImageView back_view, done_view, image_title;
    String pickImage, textTitle, textDescription;
    int choosedColor;
    String imageUri;
    String image;
    Integer amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick);
        userId = FirebaseAuth.getInstance().getUid();
        initView();
        getIncomingIntent();


        quickViewModel = ViewModelProviders.of(this).get(QuickViewModel.class);

        MapUtility.apiKey = getResources().getString(R.string.your_api_key);

        quickViewModel.counter.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                amount = integer;
            }
        });

        findViewById(R.id.back_view).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordDataRoom();
            }
        });

        done_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordDataRoom();
                uploadTask();
             //   uploadImage();
            }
        });
    }

    public void uploadTask() {
        Map<String, Object> map = new HashMap<>();
        map.put("description", e_description.getText().toString());
        map.put("title", e_title.getText().toString());
//        userId = FirebaseAuth.getInstance().getUid();
        FirebaseFirestore.getInstance()
                .collection("tasks")

                .add(map)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(QuickActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(QuickActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


//    private void getInfo() {
//        FirebaseFirestore.getInstance()
//                .collection("tasks")
//                .document(userId)
//                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//                   if (documentSnapshot!=null){
//                       String title = documentSnapshot.getString("title");
//                       e_title.setText(title);
//                   }
//                    }
//                });


    public void recordDataRoom() {
        textTitle = e_title.getText().toString();
        textDescription = e_description.getText().toString();
        if (textTitle.equals("") && textDescription.equals("")) {
            finish();
        } else {
            String currentDate = new SimpleDateFormat("dd ", Locale.getDefault()).format(new Date());
            if (pickImage == null) pickImage = image;

            quickModel = new QuickModel(textTitle, textDescription, currentDate, pickImage, choosedColor,null);

                App.getDataBase().taskDao().insert(quickModel);
                finish();
            }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        recordDataRoom();
    }

    public void getIncomingIntent() {
        Intent intent = getIntent();
        quickModel = (QuickModel) intent.getSerializableExtra("task");
        if (quickModel != null) {
            e_title.setText(quickModel.getTitle());
            e_description.setText(quickModel.getDescription());
            e_title.setTextColor(quickModel.getColor());
            image = quickModel.getImage();
            Glide.with(this).load(quickModel.getImage()).into(image_title);

        }
    }

    public void initView() {
        materialDesignFAM = findViewById(R.id.menu_floating);
        floatingActionButtonColorPicker = findViewById(R.id.fab);
        floatingActionButtonLocationPicker = findViewById(R.id.fab2);
        floatingActionButtonImagePicker = findViewById(R.id.fab3);
        image_title = findViewById(R.id.image_title);

        e_title = findViewById(R.id.edit_title);
        e_description = findViewById(R.id.edit_description);
        back_view = findViewById(R.id.back_view);
        done_view = findViewById(R.id.done_view);

        floatingActionButtonColorPicker.setOnClickListener(new View.OnClickListener() {
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
                                choosedColor = color;
                            }

                            @Override
                            public void onCancel() {

                            }
                        })
                        .addListenerButton("Попробовать", (v1, position, color) -> {
                            Toast.makeText(QuickActivity.this, position + "", Toast.LENGTH_SHORT).show();
                            e_title.setTextColor(color);
                        }).show();
            }
        });
        floatingActionButtonLocationPicker.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("geo:0,0?q=Скопируйте+локацию"));
            startActivity(intent);
        });
        floatingActionButtonImagePicker.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 01);

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 01 && resultCode == RESULT_OK) {
            final Uri imageUri = data.getData();
            pickImage = imageUri.toString();
            Glide.with(this).load(pickImage).into(image_title);

        }
    }

    public void uploadImage() {
        Random random = new Random();
        Integer counter =random.nextInt(20000);

        StorageReference reference = FirebaseStorage.getInstance()
                .getReference().child("save/image.jpg" + counter);
        UploadTask task = reference.putFile(Uri.parse(pickImage));

        task.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(QuickActivity.this, "All Right!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(QuickActivity.this, "Danger!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}

