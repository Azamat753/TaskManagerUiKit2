package com.lawlett.taskmanageruikit.quick;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.quick.data.model.QuickModel;
import com.lawlett.taskmanageruikit.utils.App;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.ui.PlaceAutocompleteFragment;
import com.mapbox.mapboxsdk.plugins.places.autocomplete.ui.PlaceSelectionListener;
import com.shivtechs.maplocationpicker.MapUtility;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import petrov.kristiyan.colorpicker.ColorPicker;

public class QuickActivity extends AppCompatActivity {
    public static final int ADDRESS_PICKER_REQUEST = 47;
    private static final int PLACE_SELECTION_REQUEST_CODE = 56789;

    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButtonColorPicker, floatingActionButtonLocationPicker, floatingActionButtonImagePicker;

    EditText e_title, e_description;
    ImageView back_view, done_view, image_title;
    QuickModel quickModel;
    String avatar, textTitle, textDescription;
    int choosedColor;
    String imageUri;
    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quick);

        MapUtility.apiKey = getResources().getString(R.string.your_api_key);


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

    public void recordDataRoom() {
        textTitle = e_title.getText().toString();
        textDescription = e_description.getText().toString();
        if (textTitle.equals("") && textDescription.equals("")) {
            finish();
        } else {
            String currentDate = new SimpleDateFormat("dd ", Locale.getDefault()).format(new Date());

            image = avatar;
            quickModel = new QuickModel(textTitle, textDescription, currentDate, image, choosedColor);

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
                        .addListenerButton("Попробовать", new ColorPicker.OnButtonListener() {
                            @Override
                            public void onClick(View v, int position, int color) {
                                Toast.makeText(QuickActivity.this, position + "", Toast.LENGTH_SHORT).show();
                                e_title.setTextColor(color);
                            }
                        }).show();
            }
        });
        floatingActionButtonLocationPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlaceAutocompleteFragment autocompleteFragment;

                autocompleteFragment = PlaceAutocompleteFragment.newInstance("<access_token>");

                final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.add(R.id.fragment_container, autocompleteFragment);
                transaction.commit();

                autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
                    @Override
                    public void onPlaceSelected(CarmenFeature carmenFeature) {
                        Toast.makeText(QuickActivity.this, carmenFeature.text(), Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onCancel() {
                        finish();

                    }
                });
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

