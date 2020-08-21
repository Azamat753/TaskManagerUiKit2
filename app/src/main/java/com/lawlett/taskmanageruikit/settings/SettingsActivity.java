package com.lawlett.taskmanageruikit.settings;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.main.MainActivity;
import com.lawlett.taskmanageruikit.utils.LanguagePreference;
import com.lawlett.taskmanageruikit.utils.PasswordDonePreference;
import com.lawlett.taskmanageruikit.utils.PasswordPreference;
import com.lawlett.taskmanageruikit.utils.ThemePreference;
import com.lawlett.taskmanageruikit.utils.TimingSizePreference;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    ImageView back;
    LinearLayout language_tv, clear_password_layout, clearMinutes_layout, theme_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_settings);

        clear_password_layout = findViewById(R.id.first_layout);
        clearMinutes_layout = findViewById(R.id.second_layout);
        theme_layout = findViewById(R.id.third_layout);
        back = findViewById(R.id.back_view);
        language_tv = findViewById(R.id.four_layout);


        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setNavigationBarColor(getResources().getColor(R.color.statusBarC));

        theme_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeThemeDialog();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        clear_password_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(SettingsActivity.this);
                dialog.setTitle(R.string.are_you_sure).setMessage(R.string.clear_password)
                        .setNegativeButton(R.string.no, (dialog1, which) ->
                                dialog1.cancel())
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                PasswordPreference.getInstance(SettingsActivity.this).clearPassword();
                                PasswordDonePreference.getInstance(SettingsActivity.this).clearSettings();
                                Toast.makeText(SettingsActivity.this, R.string.data_of_password_delete, Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });
        clearMinutes_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(SettingsActivity.this);
                dialog.setTitle(R.string.are_you_sure).setMessage(R.string.clear_minute)
                        .setNegativeButton(R.string.no, (dialog1, which) ->
                                dialog1.cancel())
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TimingSizePreference.getInstance(SettingsActivity.this).clearSettings();
                                Toast.makeText(SettingsActivity.this, R.string.data_about_minutes_clear, Toast.LENGTH_SHORT).show();
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
        final String[] listItems = {getString(R.string.light_theme), getString(R.string.dark_theme)};
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
        builder.setTitle(R.string.choose_theme);
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
        final String[] listItems = {"English", "Русский", "Кыргызча"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingsActivity.this);
        mBuilder.setTitle(R.string.choose_language);
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

    private void loadLocale() {
        String language = LanguagePreference.getInstance(this).getLanguage();
        setLocale(language);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
