package com.lawlett.taskmanageruikit.splash;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.main.MainActivity;
import com.lawlett.taskmanageruikit.onboard.BoardActivity;
import com.lawlett.taskmanageruikit.utils.Preference;
import com.lawlett.taskmanageruikit.utils.ThemePreference;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setNavigationBarColor(getResources().getColor(R.color.statusBarC));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean isShown = Preference.getInstance(getApplication()).isShown();
                if (isShown) {
                    startActivity(new Intent(getApplication(), MainActivity.class));
                } else {
                    startActivity(new Intent(getApplication(), BoardActivity.class));
                }
                finish();

            }
        }, 1000);

        checkedTheme();
    }

    public void checkedTheme() {
        boolean booleanValue = ThemePreference.getInstance(this).isTheme();
        if (booleanValue) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            ThemePreference.getInstance(SplashActivity.this).saveThemeTrue();
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            ThemePreference.getInstance(SplashActivity.this).saveThemeFalse();
        }
    }
}