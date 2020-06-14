package com.lawlett.taskmanageruikit.calendarEvents;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.calendarEvents.data.model.CalendarTaskModel;
import com.lawlett.taskmanageruikit.calendarEvents.recycler.DayAdapter;
import com.lawlett.taskmanageruikit.utils.CalendarEventsStorage;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddEventActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    TextView startData, endData, startTime, endTime;
    EditText title;
    ConstraintLayout constraintLayout;
    CalendarTaskModel calendarTaskModel = new CalendarTaskModel();
     ArrayList<CalendarTaskModel> list = new ArrayList<>();
    ImageView backView,doneView;
    String currentDataString;
    String hour,minuteCurrent;
    ArrayList<CalendarTaskModel> list2;
    DayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_event);

        initViews();

        startData.setOnClickListener(v -> {
            DialogFragment dataPicker = new DatePickerFragment();
            dataPicker.show(getSupportFragmentManager(), "data picker");

        });
        startTime.setOnClickListener(v -> {
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(), "timePicker");
        });
        doneView.setOnClickListener(v -> saveData() );
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
        endData = findViewById(R.id.end_date_number);
        startTime = findViewById(R.id.start_time_number);
        endTime = findViewById(R.id.end_time_number);
        constraintLayout = findViewById(R.id.topconstraint);
        backView= findViewById(R.id.back_view_event);
        doneView=findViewById(R.id.done_view_event);
    }

    public void saveData() {
//        adapter= new DayAdapter(list);
        calendarTaskModel.title = title.getText().toString().trim();
        calendarTaskModel.dataTime=currentDataString;
        calendarTaskModel.startTime=hour+":"+minuteCurrent;
        list.add(calendarTaskModel);
//        adapter.addText(calendarTaskModel);
        CalendarEventsStorage.save(list, this);
        finish();
    }
}