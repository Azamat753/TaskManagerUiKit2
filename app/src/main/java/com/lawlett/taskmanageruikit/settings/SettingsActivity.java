package com.lawlett.taskmanageruikit.settings;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.utils.LanguagePreference;
import com.lawlett.taskmanageruikit.utils.PasswordDonePreference;
import com.lawlett.taskmanageruikit.utils.PasswordPreference;
import com.lawlett.taskmanageruikit.utils.ThemePreference;
import com.lawlett.taskmanageruikit.utils.TimingSizePreference;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    TextView clearPassword_tv, clearMinutes_tv, language_tv;
    ImageView back;

    Switch mySwich;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        clearPassword_tv = findViewById(R.id.clear_password);
        clearMinutes_tv = findViewById(R.id.clear_time);
        mySwich = findViewById(R.id.mySwitch);
        back = findViewById(R.id.back_view);
        language_tv = findViewById(R.id.language);
        loadLocale();

        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setNavigationBarColor(getResources().getColor(R.color.statusBarC));

        boolean booleanValue = ThemePreference.getInstance(this).isTheme();
        if (booleanValue) {
            mySwich.setChecked(true);
        }

        mySwich.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    mySwich.setChecked(true);
                    ThemePreference.getInstance(SettingsActivity.this).saveThemeTrue();
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    mySwich.setChecked(false);
                    ThemePreference.getInstance(SettingsActivity.this).saveThemeFalse();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        clearPassword_tv.setOnClickListener(new View.OnClickListener() {
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
        clearMinutes_tv.setOnClickListener(new View.OnClickListener() {
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

    private void showChangeLanguageDialog() {
        final String[] listItems = {"English", "Русский", "Кырзгызча"};
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
    public void loadLocale(){
        String language= LanguagePreference.getInstance(this).getLanguage();
        setLocale(language);
    }
}
