package com.lawlett.taskmanageruikit.timing;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lawlett.taskmanageruikit.R;

public class TimingActivity extends AppCompatActivity {
    Button btnstart, btnstop;
    ImageView icanchor;
    Animation roundingalone;
    Chronometer timerHere;
    long elapsedMillis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timing);
        btnstart = findViewById(R.id.btnstart);
        btnstop = findViewById(R.id.btnstop);
        icanchor = findViewById(R.id.icanchor);
        timerHere = findViewById(R.id.timerHere);

        btnstop.setAlpha(0);

        roundingalone = AnimationUtils.loadAnimation(this, R.anim.roundingalone);

        Typeface MMedium = Typeface.createFromAsset(getAssets(), "MMedium.ttf");

        btnstart.setTypeface(MMedium);

        btnstart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                icanchor.startAnimation(roundingalone);
                btnstop.animate().alpha(1).translationY(-80).setDuration(300).start();
                btnstart.animate().alpha(0).setDuration(300).start();
                btnstop.setVisibility(View.VISIBLE);
                btnstart.setVisibility(View.GONE);
                timerHere.setBase(SystemClock.elapsedRealtime());

                timerHere.start();
            }
        });
        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnstart.setVisibility(View.VISIBLE);
                btnstart.animate().alpha(1).translationY(-80).setDuration(300).start();
                timerHere.stop();
                btnstop.setVisibility(View.GONE);
                icanchor.clearAnimation();
                showElapsedTime();
            }
        });
    }

    private void showElapsedTime() {
        elapsedMillis = SystemClock.elapsedRealtime() - timerHere.getBase();
        Toast.makeText(this, "Elapsed milliseconds: " + elapsedMillis / 1000,
                Toast.LENGTH_SHORT).show();
    }
}