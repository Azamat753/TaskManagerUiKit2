package com.lawlett.taskmanageruikit.calendarEvents;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
import com.lawlett.taskmanageruikit.utils.App;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import petrov.kristiyan.colorpicker.ColorPicker;

public class AddEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    TextView startData, startTime, endTime,startDataText;
    EditText title;
    ConstraintLayout constraintLayout;
    CalendarTaskModel calendarTaskModel;
    ImageView backView, doneView;
    String currentDataString;
    String hour, endHour, minuteCurrent, endMinute;
    View colorView;
    int choosedColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event);

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
                    endHour = String.valueOf(selectedHour);
                    endMinute = String.valueOf(selectedMinute);
                    endTime.setText(selectedHour + ":" + selectedMinute);

                }
            }, hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle("Select Time");
            mTimePicker.show();

        });

        doneView.setOnClickListener(v -> recordDataRoom());
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        currentDataString = DateFormat.getDateInstance().format(c.getTime());
        startData.setText(currentDataString);
    }

    @SuppressLint({"LogNotTimber", "SetTextI18n"})
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        hour = String.valueOf(hourOfDay);
        minuteCurrent = String.valueOf(minute);
        startTime.setText(hour + ":" + minute);
        Log.e("current", "onTimeSet: " + hourOfDay + "   " + minute);
    }

    public void initViews() {
        title = findViewById(R.id.edit_title);
        startData = findViewById(R.id.start_date_number);
        startTime = findViewById(R.id.start_time_number);
        endTime = findViewById(R.id.end_time_number);
        constraintLayout = findViewById(R.id.topconstraint);
        backView = findViewById(R.id.back_view_event);
        doneView = findViewById(R.id.done_view_event);
        colorView = findViewById(R.id.color_view);
        startDataText=findViewById(R.id.start_date);
    }

    public void recordDataRoom() {
        calendarTaskModel = new CalendarTaskModel(currentDataString, title.getText().toString().trim(), hour + ":" + minuteCurrent, endHour + ":" + endMinute, choosedColor);
        App.getDataBase().dataDao().insert(calendarTaskModel);
        finish();
    }

    public void chooseColor(View view) {
        final ColorPicker colorPicker = new ColorPicker(AddEventActivity.this);
        ArrayList<String> colors = new ArrayList<>();
        colors.add("#82B926");
        colors.add("#a276eb");
        colors.add("#6a3ab2");
        colors.add("#666666");
        colors.add("#FFFF00");
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
                        Toast.makeText(AddEventActivity.this, position + "", Toast.LENGTH_SHORT).show();
                        choosedColor = color;
                        colorView.setBackgroundColor(color);
                    }

                    @Override
                    public void onCancel() {

                    }
                })
                .addListenerButton("Попробовать", (v1, position, color) -> {
                    Toast.makeText(AddEventActivity.this, position + "", Toast.LENGTH_SHORT).show();
                    colorView.setBackgroundColor(color);
                }).show();
    }

    public void getIncomingIntent() {
        Intent intent = getIntent();
        calendarTaskModel = (CalendarTaskModel) intent.getSerializableExtra("calendar");
        if (calendarTaskModel != null) {
            title.setText(calendarTaskModel.getTitle());
            startTime.setText(calendarTaskModel.getStartTime());
            endTime.setText(calendarTaskModel.getEndTime());
            colorView.setBackgroundColor(calendarTaskModel.getChooseColor());
            startData.setText(calendarTaskModel.getDataTime());

        }
    }

}
