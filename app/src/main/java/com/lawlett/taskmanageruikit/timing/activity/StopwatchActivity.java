package com.lawlett.taskmanageruikit.timing.activity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.timing.model.TimingModel;
import com.lawlett.taskmanageruikit.utils.App;
import com.lawlett.taskmanageruikit.utils.TimingSizePreference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class StopwatchActivity extends AppCompatActivity {
    Button btnstart, btnstop, applyClick;
    ImageView icanchor, phoneImage;
    Animation roundingalone, atg, btgone, btgtwo;
    Chronometer timerHere;
    EditText taskEdit;
    long elapsedMillis;
    TimingModel timingModel;
    String myTask;
    ConstraintLayout imageConstrain, stopwatchConstraint;
    String stopwatchTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        phoneImage = findViewById(R.id.image_phone);
        btnstart = findViewById(R.id.btnstart);
        applyClick = findViewById(R.id.stopwatch_task_apply);
        btnstop = findViewById(R.id.btnstop);
        icanchor = findViewById(R.id.icanchor);
        taskEdit = findViewById(R.id.stopwatch_task_edit);
        timerHere = findViewById(R.id.timerHere);
        imageConstrain = findViewById(R.id.imageconst);
        stopwatchConstraint = findViewById(R.id.stopWatchConst);
        atg = AnimationUtils.loadAnimation(this, R.anim.atg);
        btgone = AnimationUtils.loadAnimation(this, R.anim.btgone);
        btgtwo = AnimationUtils.loadAnimation(this, R.anim.btgtwo);
        phoneImage.startAnimation(atg);
        btnstart.startAnimation(btgone);
        applyClick.startAnimation(btgtwo);
        taskEdit.startAnimation(btgone);
        btnstop.setAlpha(0);

        roundingalone = AnimationUtils.loadAnimation(this, R.anim.roundingalone);

        Typeface MMedium = Typeface.createFromAsset(getAssets(), "MMedium.ttf");
        Typeface MLight = Typeface.createFromAsset(getAssets(), "MLight.ttf");
        Typeface MRegular = Typeface.createFromAsset(getAssets(), "MRegular.ttf");

        btnstart.setTypeface(MMedium);
        applyClick.setTypeface(MLight);
        taskEdit.setTypeface(MRegular);


        applyClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTask = taskEdit.getText().toString();
                imageConstrain.setVisibility(View.GONE);
                stopwatchConstraint.setVisibility(View.VISIBLE);
            }
        });

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
                showElapsedTime();
                dataRoom();
            }
        });
    }

    private void showElapsedTime() {
        elapsedMillis = SystemClock.elapsedRealtime() - timerHere.getBase();
        stopwatchTime = String.valueOf(elapsedMillis / 60000);
        Toast.makeText(this, "Elapsed milliseconds: " + elapsedMillis / 1000,
                Toast.LENGTH_SHORT).show();
    }

    public void dataRoom() {
        Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        String[] monthName = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль",
                "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
        final String month = monthName[c.get(Calendar.MONTH)];
        String currentDate = new SimpleDateFormat("dd ", Locale.getDefault()).format(new Date());
        int stopwatchTimePref = Integer.parseInt(stopwatchTime);
        int previousTimePref=TimingSizePreference.getInstance(this).getTimingSize();
        TimingSizePreference.getInstance(this).saveTimingSize(stopwatchTimePref+previousTimePref);
        timingModel = new TimingModel(null, null, null, myTask, Integer.valueOf(stopwatchTime), currentDate + " " + month + " " + year);
        Log.e("stopwatchMinutes", "dataRoom: " + stopwatchTime);
        App.getDataBase().timingDao().insert(timingModel);
        finish();
    }
}