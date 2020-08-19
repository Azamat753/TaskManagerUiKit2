package com.lawlett.taskmanageruikit.idea;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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
import com.lawlett.taskmanageruikit.idea.data.model.QuickModel;
import com.lawlett.taskmanageruikit.utils.App;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import petrov.kristiyan.colorpicker.ColorPicker;

public class QuickActivity extends AppCompatActivity {
    public static final int CAMERA_REQUEST = 500;

    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButtonColorPicker, floatingActionButtonCameraPicker, floatingActionButtonImagePicker;
    QuickModel quickModel;
    EditText e_title, e_description;
    ImageView back_view, done_view, image_title;
    String pickImage, textTitle, textDescription, captureImage, gallImage;
    int choosedColor ;
    boolean isGallery = false;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick);



        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setNavigationBarColor(getResources().getColor(R.color.statusBarC));

        initView();
        getIncomingIntent();


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
            }
        });

    }
    private void getCurrentPhoto() {
        if (isGallery) {
            pickImage = gallImage;
        } else {
            pickImage = captureImage;
        }
    }

    @SuppressLint("ResourceAsColor")
    public void recordDataRoom() {
        textTitle = e_title.getText().toString();
        textDescription = e_description.getText().toString();
        if (!textTitle.equals("") || !textDescription.equals("")) {
            Calendar c = Calendar.getInstance();
            final int year = c.get(Calendar.YEAR);
            String[] monthName = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль",
                    "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
            final String month = monthName[c.get(Calendar.MONTH)];
            String currentDate = new SimpleDateFormat("dd ", Locale.getDefault()).format(new Date());
            getCurrentPhoto();
            String myTitle = e_title.getText().toString();
            String myDesk = e_description.getText().toString();
            String myPickImage = pickImage;
            int myChoosedColor = choosedColor;

            if (quickModel != null) {
                quickModel.setTitle(myTitle);
                quickModel.setDescription(myDesk);
                quickModel.setImage(myPickImage);
                quickModel.setColor(myChoosedColor);
                quickModel.setCreateData(currentDate + " " + month + " " + year);
                App.getDataBase().taskDao().update(quickModel);

                Log.e("pickImage", "recordDataRoom: " + myPickImage);

            } else {
                choosedColor = e_title.getCurrentTextColor();
                quickModel = new QuickModel(textTitle, textDescription, currentDate + " " + month + " " + year, myPickImage, choosedColor, null);
                App.getDataBase().taskDao().insert(quickModel);
            }
        } else {
            Toast.makeText(this, "Колонка пуста", Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    @SuppressLint("ResourceAsColor")
    public void getIncomingIntent() {
        Intent intent = getIntent();
        quickModel = (QuickModel) intent.getSerializableExtra("task");
        if (quickModel != null) {
            textTitle = quickModel.getTitle();
            e_title.setText(textTitle);
            textDescription = quickModel.getDescription();
            e_description.setText(textDescription);
            choosedColor = quickModel.getColor();
            e_title.setTextColor(choosedColor);
            gallImage = quickModel.getImage();
            Glide.with(this).load(gallImage).into(image_title);
        }
    }

    @SuppressLint("ResourceAsColor")
    public void initView() {
        materialDesignFAM = findViewById(R.id.menu_floating);
        floatingActionButtonColorPicker = findViewById(R.id.fab);
        floatingActionButtonCameraPicker = findViewById(R.id.fab2);
        floatingActionButtonImagePicker = findViewById(R.id.fab3);
        image_title = findViewById(R.id.image_title);
        e_title = findViewById(R.id.edit_title2);
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
                colors.add("#FFFFFF");
                colors.add("#3C8D2F");
                colors.add("#FA9F00");
                colors.add("#FF0000");
                colors.add("#03DAC5");
                colors.add("#005EFF");

                colorPicker
                        .setDefaultColorButton(Color.parseColor("#FFFFFF"))
                        .setColors(colors)
                        .setColumns(5)
                        .setRoundColorButton(true)
                        .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                            @Override
                            public void onChooseColor(int position, int color) {
                                e_title.setTextColor(color);
                                choosedColor = color;
                            }

                            @Override
                            public void onCancel() {

                            }
                        })
                        .addListenerButton("Попробовать", (v1, position, color) -> {
                            e_title.setTextColor(color);
                        }).show();
            }
        });

        floatingActionButtonCameraPicker.setOnClickListener(v -> {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);

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
        if (data != null && resultCode == RESULT_OK) {
            if (requestCode == 01) {
                final Uri imageUri = data.getData();
                gallImage = imageUri.toString();
                isGallery = true;
                Glide.with(this).load(imageUri).into(image_title);
            } else if (requestCode == CAMERA_REQUEST) {
                Bitmap thumbnailBitmap = (Bitmap) data.getExtras().get("data");
                assert thumbnailBitmap != null;
                Uri a = getImageUri(this, thumbnailBitmap);
                isGallery = false;
                if (captureImage == null) {
                    captureImage = a.toString();
                    Glide.with(this).load(captureImage).into(image_title);
                }
            }
        }
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "IMG_" + Calendar.getInstance().getTime(), null);
        return Uri.parse(path);
    }

//    public void uploadImage() {
//        Random random = new Random();
//        Integer counter = random.nextInt(20000);
//
//        StorageReference reference = FirebaseStorage.getInstance()
//                .getReference().child("save/image.jpg" + counter);
//        UploadTask task = reference.putFile(Uri.parse(pickImage));
//
//        task.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
//                if (task.isSuccessful()) {
//                    Toast.makeText(QuickActivity.this, "All Right!", Toast.LENGTH_SHORT).show();
//
//                } else {
//                    Toast.makeText(QuickActivity.this, "Danger!", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
}

