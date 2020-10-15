package com.lawlett.taskmanageruikit.calendarEvents;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.calendarEvents.data.model.CalendarTaskModel;
import com.lawlett.taskmanageruikit.service.MessageService;
import com.lawlett.taskmanageruikit.utils.App;
import com.lawlett.taskmanageruikit.utils.DatePickerFragment;
import com.lawlett.taskmanageruikit.utils.LanguagePreference;
import com.lawlett.taskmanageruikit.utils.TimePickerFragment;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import petrov.kristiyan.colorpicker.ColorPicker;

public class AddEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    TextView startData, startTime, endTime, startDataText, startTimeNumber, endTimeNumber;
    EditText title;
    ConstraintLayout constraintLayout;
    CalendarTaskModel calendarTaskModel;
    ImageView backView, doneView, back;
    String currentDataString;
    String titleT, getStart, getDataTime, getEndTime;
    View colorView;
    int choosedColor;
    AlarmManager mAlarm;
    long time;
    Calendar baseCalendar = Calendar.getInstance();
    String startHour, endingHour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        loadLocale();

        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setNavigationBarColor(getResources().getColor(R.color.statusBarC));

        initViews();
        getIncomingIntent();
        startDataText.setOnClickListener(v -> {
            DialogFragment dataPicker = new DatePickerFragment();
            dataPicker.show(getSupportFragmentManager(), "data picker");
        });

        startTime.setOnClickListener(v -> {
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(), "timePicker");
        });

        endTime.setOnClickListener(v -> {
            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(AddEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(0, 0, 0, selectedHour, selectedMinute);
                    endTimeNumber.setText(android.text.format.DateFormat.format("HH:mm", calendar));
                    endingHour = String.valueOf(android.text.format.DateFormat.format("HH:mm", calendar));
                }
            }, hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle(getString(R.string.select_time));
            mTimePicker.show();

        });

        doneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recordDataRoom();
                setNotification();
            }
        });

        back = findViewById(R.id.back_view_event);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        baseCalendar.set(Calendar.YEAR,year);
        baseCalendar.set(Calendar.MONTH,month);
        baseCalendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
        currentDataString = DateFormat.getDateInstance().format(c.getTime());
        startData.setText(currentDataString);
    }

    private void setNotification() {
        Intent i = new Intent(getBaseContext(), MessageService.class);
        i.putExtra("displayText", "sample text");
        List<CalendarTaskModel> listA= App.getDataBase().dataDao().getAll();
        int idOfP = listA.size();
        PendingIntent pi = PendingIntent.getBroadcast(this.getApplicationContext(), idOfP, i,PendingIntent.FLAG_CANCEL_CURRENT);
        mAlarm = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);

//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeInMillis(System.currentTimeMillis());
//        calendar.add(Calendar.SECOND, 10);
//        time = calendar.getTimeInMillis();

        mAlarm.set(AlarmManager.RTC_WAKEUP,time,pi);
//        mAlarm.setInexactRepeating(AlarmManager.RTC_WAKEUP,time,AlarmManager.INTERVAL_DAY,pi);
    }

    @SuppressLint({"LogNotTimber", "SetTextI18n"})
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(0, 0, 0, hourOfDay, minute);
        baseCalendar.set(Calendar.HOUR_OF_DAY,hourOfDay);
        baseCalendar.set(Calendar.MINUTE,minute);

        time = baseCalendar.getTimeInMillis();

        startTimeNumber.setText(android.text.format.DateFormat.format("HH:mm", calendar));
        startHour = String.valueOf(android.text.format.DateFormat.format("HH:mm", calendar));
    }

    public void initViews() {
        title = findViewById(R.id.edit_title);
        startData = findViewById(R.id.start_date_number);
        startTime = findViewById(R.id.start_time);
        startTimeNumber = findViewById(R.id.start_time_number);
        endTimeNumber = findViewById(R.id.end_time_number);
        endTime = findViewById(R.id.end_time);
        constraintLayout = findViewById(R.id.topconstraint);
        backView = findViewById(R.id.back_view_event);
        doneView = findViewById(R.id.done_view_event);
        colorView = findViewById(R.id.color_view);
        startDataText = findViewById(R.id.start_date);
    }

    public void recordDataRoom() {
        String myTitle = title.getText().toString();
        String myStartData = startData.getText().toString();
        String myStartTime = startTimeNumber.getText().toString();
        String myEndTime = endTimeNumber.getText().toString();
        int myColor = choosedColor;
        if (calendarTaskModel != null) {
            calendarTaskModel.setTitle(myTitle);
            calendarTaskModel.setDataTime(myStartData);
            calendarTaskModel.setStartTime(myStartTime);
            calendarTaskModel.setEndTime(myEndTime);
            calendarTaskModel.setChooseColor(myColor);
            App.getDataBase().dataDao().update(calendarTaskModel);
            finish();
        } else {
            titleT = title.getText().toString();
            if (currentDataString != null && titleT != null && startHour != null && endingHour != null) {
                calendarTaskModel = new CalendarTaskModel(currentDataString, title.getText().toString().trim(),
                        startHour, endingHour, choosedColor);
                App.getDataBase().dataDao().insert(calendarTaskModel);
                finish();
            } else {
                Toast.makeText(this, R.string.need_all_fields, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void chooseColor(View view) {
        final ColorPicker colorPicker = new ColorPicker(AddEventActivity.this);
        colorPicker.setTitle(getString(R.string.choose_color));
        ArrayList<String> colors = new ArrayList<>();
        colors.add("#82B926");
        colors.add("#a276eb");
        colors.add("#6a3ab2");
        colors.add("#666666");
        colors.add("#FFFFFF");
        colors.add("#3C8D2F");
        colors.add("#FA9F00");
        colors.add("#FF0000");
        colors.add("#03DAC5");
        colors.add("#005EFF");

        colorPicker
                .setDefaultColorButton(Color.parseColor("#f84c44"))
                .setColors(colors)
                .setColumns(5)
                .setRoundColorButton(true)
                .setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
                    @Override
                    public void onChooseColor(int position, int color) {
                        if (color == 0) {
                            colorView.setBackgroundColor(Color.parseColor("#03BADA"));
                            Toast.makeText(AddEventActivity.this, R.string.color_dont_choosed, Toast.LENGTH_SHORT).show();
                        } else {
                            choosedColor = color;
                            colorView.setBackgroundColor(color);
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                })
                .addListenerButton(getString(R.string.try_color), (v1, position, color) -> {
                    colorView.setBackgroundColor(color);
                }).show();
    }

    public void getIncomingIntent() {
        Intent intent = getIntent();
        calendarTaskModel = (CalendarTaskModel) intent.getSerializableExtra("calendar");
        if (calendarTaskModel != null) {
            titleT = calendarTaskModel.getTitle();
            title.setText(titleT);

            getStart = calendarTaskModel.getStartTime();
            startTimeNumber.setText(getStart);

            getEndTime = calendarTaskModel.getEndTime();
            endTimeNumber.setText(getEndTime);

            choosedColor = calendarTaskModel.getChooseColor();
            colorView.setBackgroundColor(calendarTaskModel.getChooseColor());

            getDataTime = calendarTaskModel.getDataTime();
            startData.setText(getDataTime);
        }
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        LanguagePreference.getInstance(AddEventActivity.this).saveLanguage(lang);
    }

    public void loadLocale() {
        String language = LanguagePreference.getInstance(this).getLanguage();
        setLocale(language);
    }
}

