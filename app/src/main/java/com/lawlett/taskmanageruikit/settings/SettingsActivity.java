package com.lawlett.taskmanageruikit.settings;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.utils.PasswordDonePreference;
import com.lawlett.taskmanageruikit.utils.PasswordPreference;

public class SettingsActivity extends AppCompatActivity {
    TextView clearPassword_tv;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        clearPassword_tv = findViewById(R.id.clear_password);
        back = findViewById(R.id.back_view);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        clearPassword_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PasswordPreference.getInstance(SettingsActivity.this).clearPassword();
                PasswordDonePreference.getInstance(SettingsActivity.this).clearSettings();
                Toast.makeText(SettingsActivity.this, "Данные о пароле удалены", Toast.LENGTH_SHORT).show();
            }
        });
    }
//
//    public void clearPersonal(View view) {
//        AlertDialog.Builder dialog = new AlertDialog.Builder(SettingsActivity.this);
//        dialog.setTitle("Очистить данные категории Персональные?").setMessage("Очистить")
//                .setNegativeButton("Нет", (dialog1, which) ->
//
//                        dialog1.cancel())
//
//                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        PersonalSizePreference.getInstance(SettingsActivity.this).clearSettings();
//                    }
//                }).show();
//    }
//
//    public void clearWork(View view) {
//        AlertDialog.Builder dialog = new AlertDialog.Builder(SettingsActivity.this);
//        dialog.setTitle("Очистить данные категории Работа?").setMessage("Очистить")
//                .setNegativeButton("Нет", (dialog1, which) ->
//
//                        dialog1.cancel())
//
//                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        WorkSizePreference.getInstance(SettingsActivity.this).clearSettings();
//                    }
//                }).show();
//    }
//
//    public void clearMeet(View view) {
//        AlertDialog.Builder dialog = new AlertDialog.Builder(SettingsActivity.this);
//        dialog.setTitle("Очистить данные категории Встречи?").setMessage("Очистить")
//                .setNegativeButton("Нет", (dialog1, which) ->
//
//                        dialog1.cancel())
//
//                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        MeetSizePreference.getInstance(SettingsActivity.this).clearSettings();
//                    }
//                }).show();
//    }
//
//    public void clearHome(View view) {
//        AlertDialog.Builder dialog = new AlertDialog.Builder(SettingsActivity.this);
//        dialog.setTitle("Очистить данные категории Дом?").setMessage("Очистить")
//                .setNegativeButton("Нет", (dialog1, which) ->
//
//                        dialog1.cancel())
//
//                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        HomeSizePreference.getInstance(SettingsActivity.this).clearSettings();
//                    }
//                }).show();
//    }
//
//    public void clearPrivate(View view) {
//        AlertDialog.Builder dialog = new AlertDialog.Builder(SettingsActivity.this);
//        dialog.setTitle("Очистить данные категории Приватные?").setMessage("Очистить")
//                .setNegativeButton("Нет", (dialog1, which) ->
//
//                        dialog1.cancel())
//
//                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        PrivateSizePreference.getInstance(SettingsActivity.this).clearSettings();
//                    }
//                }).show();
    }
