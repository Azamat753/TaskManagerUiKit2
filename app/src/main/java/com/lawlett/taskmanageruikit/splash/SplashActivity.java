package com.lawlett.taskmanageruikit.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.main.MainActivity;
import com.lawlett.taskmanageruikit.onboard.BoardActivity;
import com.lawlett.taskmanageruikit.utils.Preference;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

                boolean isShown = Preference.getInstance(getApplication()).isShown();
                if (isShown) {
                    startActivity(new Intent(getApplication(), MainActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(getApplication(), BoardActivity.class));
                    finish();
                }
            }
        }, 1000);
    }
}