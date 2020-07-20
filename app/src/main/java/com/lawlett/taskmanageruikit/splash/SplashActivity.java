package com.lawlett.taskmanageruikit.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

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
//                if (FirebaseAuth.getInstance().getCurrentUser()==null){
//                    startActivity(new Intent(SplashActivity.this, RegistrationActivity.class));
//                    finish();
//                    return;
//                }
                if (isShown) {
                    startActivity(new Intent(getApplication(), MainActivity.class));
                    finish();
                }else {
                    startActivity(new Intent(getApplication(), BoardActivity.class));
                    finish();
                }

            }
        }, 10);
    }
}