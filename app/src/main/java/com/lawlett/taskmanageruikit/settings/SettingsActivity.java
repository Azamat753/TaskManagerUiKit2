package com.lawlett.taskmanageruikit.settings;

import android.content.DialogInterface;
import android.content.SharedPreferences;
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
import com.lawlett.taskmanageruikit.utils.PasswordDonePreference;
import com.lawlett.taskmanageruikit.utils.PasswordPreference;
import com.lawlett.taskmanageruikit.utils.TimingSizePreference;

public class SettingsActivity extends AppCompatActivity {
    TextView clearPassword_tv, clearMinutes_tv;
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

        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setNavigationBarColor(getResources().getColor(R.color.black));

        SharedPreferences sharedPreferences =getSharedPreferences("light",0);
        Boolean booleanValue=sharedPreferences.getBoolean("night_mode",false);
        if (booleanValue){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            mySwich.setChecked(true);
        }

        mySwich.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    mySwich.setChecked(true);
                    SharedPreferences.Editor editor= sharedPreferences.edit();
                    editor.putBoolean("night_mode",true);
                    editor.commit();
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    mySwich.setChecked(false);
                    SharedPreferences.Editor editor= sharedPreferences.edit();
                    editor.putBoolean("night_mode",false);
                    editor.commit();           }
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
                dialog.setTitle("Вы уверены ?").setMessage("Очистить пароль")
                        .setNegativeButton("Нет", (dialog1, which) ->
                                dialog1.cancel())
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                PasswordPreference.getInstance(SettingsActivity.this).clearPassword();
                                PasswordDonePreference.getInstance(SettingsActivity.this).clearSettings();
                                Toast.makeText(SettingsActivity.this, "Данные о пароле удалены", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });
        clearMinutes_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(SettingsActivity.this);
                dialog.setTitle("Вы уверены ?").setMessage("Очистить поле минут")
                        .setNegativeButton("Нет", (dialog1, which) ->
                                dialog1.cancel())
                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                TimingSizePreference.getInstance(SettingsActivity.this).clearSettings();
                                Toast.makeText(SettingsActivity.this, "Данные о минутах очищены", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });
    }
}
