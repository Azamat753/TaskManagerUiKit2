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
        clearPassword_tv=findViewById(R.id.clear_password);
back=findViewById(R.id.back_view);
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
}