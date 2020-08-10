package com.lawlett.taskmanageruikit.timing.activity;

import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

public class TimerActivity extends AppCompatActivity {
    private TextView countdownText;
    private Button countdownButton, exitButton, timerTaskApply, applyDone;
    MediaPlayer mp;
    ImageView icanchor, backButton, phoneImage;
    EditText timerTaskEdit;
    String timeLeftText;
    Animation roundingalone, atg, btgone, btgtwo;
    TimingModel timingModel;
    String myTask;
    EditText editText;
    ConstraintLayout imageConst, timerConst;
    private Integer timeLeftInMilliseconds = 0;//600.000  10min ||1000//1 second
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setNavigationBarColor(getResources().getColor(R.color.black));


        phoneImage = findViewById(R.id.image_timerPhone);
        atg = AnimationUtils.loadAnimation(this, R.anim.atg);
        btgone = AnimationUtils.loadAnimation(this, R.anim.btgone);
        btgtwo = AnimationUtils.loadAnimation(this, R.anim.btgtwo);
        backButton = findViewById(R.id.back_button);
        timerTaskEdit = findViewById(R.id.timer_task_edit);
        editText = findViewById(R.id.editText);
        timerTaskApply = findViewById(R.id.timer_task_apply);
        applyDone = findViewById(R.id.apply_button);
        icanchor = findViewById(R.id.icanchor);
        countdownText = findViewById(R.id.countdown_text);
        countdownButton = findViewById(R.id.countdown_button);
        exitButton = findViewById(R.id.exit_button);
        roundingalone = AnimationUtils.loadAnimation(this, R.anim.roundingalone);
        imageConst = findViewById(R.id.image_const);
        timerConst = findViewById(R.id.timerConst);

        phoneImage.startAnimation(atg);
        countdownButton.startAnimation(btgone);
        timerTaskApply.startAnimation(btgtwo);
        timerTaskEdit.startAnimation(btgone);

        Typeface medium = Typeface.createFromAsset(getAssets(), "MMedium.ttf");
        Typeface mLight = Typeface.createFromAsset(getAssets(), "MLight.ttf");
        Typeface mRegular = Typeface.createFromAsset(getAssets(), "MRegular.ttf");

        countdownButton.setTypeface(medium);
        timerTaskApply.setTypeface(mLight);//try
        timerTaskEdit.setTypeface(mRegular);


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countDownTimer != null)
                    countDownTimer.cancel();
                finish();
            }
        });

        timerTaskApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timerTaskEdit.getText().toString().isEmpty()) {
                    Toast.makeText(TimerActivity.this, "Пусто", Toast.LENGTH_SHORT).show();

                } else {
                    myTask = timerTaskEdit.getText().toString();
                    imageConst.setVisibility(View.GONE);
                    timerConst.setVisibility(View.VISIBLE);
                }
            }
        });

        applyDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editText.getText().toString().equals("") || Integer.parseInt(editText.getText().toString()) < 1) {
                    Toast.makeText(TimerActivity.this, "0 минут прошло", Toast.LENGTH_SHORT).show();
                } else {
                    timeLeftInMilliseconds = Integer.parseInt(editText.getText().toString()) * 60000;
                    timeLeftText = timeLeftInMilliseconds.toString();
                    applyDone.setVisibility(View.GONE);
                    editText.setVisibility(View.GONE);
                    countdownText.setText("Готовы ?");
                    countdownButton.setVisibility(View.VISIBLE);
                    countdownText.setVisibility(View.VISIBLE);
                    Log.e("myTime", "onClick: " + timeLeftInMilliseconds);
                }
            }
        });
        countdownButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String myTime = countdownText.getText().toString();
                if (myTime.equals("0:00") || myTime.equals("0:01") || myTime.equals("0:02")) {
                    dataRoom();
                    if (mp != null)
                        mp.stop();
                    countDownTimer.cancel();
                    finish();
                } else {
                    Toast.makeText(TimerActivity.this, "Таймер ещё не окончен", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void startTimer() {
        icanchor.startAnimation(roundingalone);
        countdownButton.animate().alpha(0).setDuration(300).start();

        countDownTimer = new CountDownTimer(timeLeftInMilliseconds, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                int minutes = (int) millisUntilFinished / 60000;
                int seconds = (int) millisUntilFinished % 60000 / 1000;

                timeLeftText = "" + minutes;
                timeLeftText += ":";
                if (seconds < 10) timeLeftText += "0";
                timeLeftText += seconds;

                countdownText.setText(timeLeftText);
                countdownButton.setVisibility(View.GONE);
                exitButton.setVisibility(View.VISIBLE);

            }

            @Override
            public void onFinish() {
                Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                mp = MediaPlayer.create(getApplicationContext(), notification);
                if (mp != null) {
                    mp.start();
                } else {
                    Toast.makeText(TimerActivity.this, "Таймер закончил свою работу", Toast.LENGTH_SHORT).show();
                }
                icanchor.clearAnimation();
                countdownButton.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    @Override
    public void onBackPressed() {
    }

    public void dataRoom() {
        Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        String[] monthName = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль",
                "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
        final String month = monthName[c.get(Calendar.MONTH)];
        String currentDate = new SimpleDateFormat("dd ", Locale.getDefault()).format(new Date());
        int previousTime = TimingSizePreference.getInstance(this).getTimingSize();
        int a = Integer.parseInt(editText.getText().toString());
        int timerTime = a;
        TimingSizePreference.getInstance(this).saveTimingSize(timerTime + previousTime);
        timingModel = new TimingModel(myTask, timerTime, currentDate + " " + month + " " + year, null, null, null);
        App.getDataBase().timingDao().insert(timingModel);
        finish();
        Log.e("myTask1", "dataRoom: " + myTask + "timeleft:" + timerTime);
    }
}
