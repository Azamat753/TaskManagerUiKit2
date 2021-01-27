package com.lawlett.taskmanageruikit.settings;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.lawlett.taskmanageruikit.main.MainActivity;
import com.lawlett.taskmanageruikit.utils.LanguagePreference;
import com.lawlett.taskmanageruikit.utils.PasswordDonePreference;
import com.lawlett.taskmanageruikit.utils.PasswordPreference;
import com.lawlett.taskmanageruikit.utils.ThemePreference;
import com.lawlett.taskmanageruikit.utils.TimingSizePreference;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import static com.lawlett.taskmanageruikit.R.color;
import static com.lawlett.taskmanageruikit.R.drawable;
import static com.lawlett.taskmanageruikit.R.id;
import static com.lawlett.taskmanageruikit.R.layout;
import static com.lawlett.taskmanageruikit.R.string;

public class SettingsActivity extends AppCompatActivity {
    LinearLayout language_tv, clear_password_layout, clearMinutes_layout, share_layout;
    ReviewManager manager;
    ReviewInfo reviewInfo;
    ImageView magick;
    ListView listView;
    public static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;
    ConstraintLayout container;
    ImageView back, imageSettings, imageTheme;
    ConstraintLayout theme_layout;

   public static String PROGRESS="Прогресс",TASKS="Задачи",TIMING="Тайминг",CALENDAR="События",IDEA="Идеи";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(layout.activity_settings);
checkFrom();
        clear_password_layout = findViewById(id.first_layout);
        clearMinutes_layout = findViewById(id.second_layout);
        theme_layout = findViewById(id.third_layout);
        back = findViewById(id.back_view);
        imageTheme = findViewById(id.image_day_night);
        language_tv = findViewById(id.four_layout);
        share_layout = findViewById(id.five_layout);
        container = findViewById(id.container_settings);
        imageSettings = findViewById(id.image_settings);

        magick = findViewById(id.btn_magick);
        listView = findViewById(id.listView);

        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setNavigationBarColor(getResources().getColor(color.statusBarC));

        if (ThemePreference.getInstance(SettingsActivity.this).isTheme()) {
            imageTheme.setImageResource(drawable.ic_day);
        } else {
            imageTheme.setImageResource(drawable.ic_nights);
        }

        theme_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ThemePreference.getInstance(SettingsActivity.this).isTheme()) {
                    ThemePreference.getInstance(SettingsActivity.this).saveThemeTrue();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    ThemePreference.getInstance(SettingsActivity.this).saveThemeFalse();
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

        magick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startVoiceRecognitionActivity();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        share_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Planner");
                    String shareMessage = "\nPlanner\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=com.lawlett.taskmanageruikit";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, getString(string.choose_app)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        clear_password_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(SettingsActivity.this);
                dialog.setTitle(string.are_you_sure).setMessage(string.clear_password)
                        .setNegativeButton(string.no, (dialog1, which) ->
                                dialog1.cancel())
                        .setPositiveButton(string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                PasswordPreference.getInstance(SettingsActivity.this).clearPassword();
                                PasswordDonePreference.getInstance(SettingsActivity.this).clearSettings();
                                Toast.makeText(SettingsActivity.this, string.data_of_password_delete, Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });
        clearMinutes_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(SettingsActivity.this);
                dialog.setTitle(string.are_you_sure).setMessage(string.clear_minute)
                        .setNegativeButton(string.no, (dialog1, which) ->
                                dialog1.cancel())
                        .setPositiveButton(string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TimingSizePreference.getInstance(SettingsActivity.this).clearSettings();
                                Toast.makeText(SettingsActivity.this, string.data_about_minutes_clear, Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });

        language_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeLanguageDialog();
            }
        });
    }

    public void showChangeThemeDialog() {
        final String[] listItems = {getString(string.light_theme), getString(string.dark_theme)};
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
        builder.setTitle(string.choose_theme);
        builder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (i == 0) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    ThemePreference.getInstance(SettingsActivity.this).saveThemeTrue();
                } else if (i == 1) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    ThemePreference.getInstance(SettingsActivity.this).saveThemeFalse();
                }

                dialog.dismiss();
            }
        });
        AlertDialog mDialog = builder.create();
        mDialog.show();
    }

    private void showChangeLanguageDialog() {
        final String[] listItems = {"English", "Русский", "Кыргызча", "Português"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingsActivity.this);
        mBuilder.setTitle(string.choose_language);
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (i == 0) {
                    setLocale("en");
                    recreate();
                } else if (i == 1) {
                    setLocale("ru");
                    recreate();
                } else if (i == 2) {
                    setLocale("ky");
                    recreate();
                } else if (i == 3) {
                    setLocale("pt");
                    recreate();
                }
                dialog.dismiss();
            }
        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        LanguagePreference.getInstance(SettingsActivity.this).saveLanguage(lang);
    }


    private void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                "Speech recognition demo");
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        CameraManager cameraManager = (CameraManager) getSystemService(CAMERA_SERVICE);

        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {

            ArrayList matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            listView.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, matches));

            if (matches.contains("Экспекто патронум")) {
                Random random = new Random();
                String animals[] = {getString(string.fox), getString(string.deer), getString(string.bull), getString(string.dog), getString(string.cat), getString(string.rat), "Журавль", "Бегемот", getString(string.giraffe), getString(string.lion), getString(string.zebra)};
                int a = random.nextInt(animals.length);
                Toast.makeText(this, getString(string.your_patronus) + animals[a], Toast.LENGTH_SHORT).show();
            }

            if (matches.contains(getString(string.lumos))) {
                if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
                    if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                        try {
                            cameraManager.setTorchMode("0", true);
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    } else
                        Toast.makeText(this, "FailureCamera", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show();
            }

            if (matches.contains("Nox")) {
                if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
                    if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
                        try {
                            cameraManager.setTorchMode("0", false);
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    } else
                        Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loadLocale() {
        String language = LanguagePreference.getInstance(this).getLanguage();
        setLocale(language);
    }

    public String checkFrom() {
        if (getIntent().getStringExtra("main") != null) {
            switch (getIntent().getStringExtra("main")) {
                case "Прогресс":
                    return PROGRESS;
                case "Задачи":
                    return TASKS;
                case "Тайминг":
                    return TIMING;
                case "События":
                    return CALENDAR;
                case "Идеи":
                    return IDEA;
                default:
                    throw new IllegalStateException("Unexpected value: " + getIntent().getStringExtra("main"));
            }
        }
        return "Прогресс";
    }

    @Override
    public void onBackPressed() {
        openMain(checkFrom());
    }

    public void openMain(String from) {
        Intent intent = new Intent(SettingsActivity.this, MainActivity.class);
        intent.putExtra("setting", from);
        startActivity(intent);
    }
}
