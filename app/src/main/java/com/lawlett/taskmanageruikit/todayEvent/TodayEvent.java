package com.lawlett.taskmanageruikit.todayEvent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.lawlett.taskmanageruikit.R;

public class TodayEvent extends AppCompatActivity {
    TextView day_tv, first,two,third;
    EditText editText,twoEd,thirdEd;
    String a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_today_event);
initView();
        String month = getIntent().getStringExtra("month");
        String day = getIntent().getStringExtra("day");

        String[] monthName = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль",
                "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};

        final String monthD = monthName[Integer.parseInt(month)];


        Log.e("ololo", "onCreate: " + monthD + "  " + day);

        day_tv.setText(day + " " + monthD);

         a = editText.getText().toString();


    }

    public void saveClick(View view) {
       first.setText(editText.getText().toString());
       editText.setVisibility(View.GONE);
       first.setVisibility(View.VISIBLE);
    }
public void initView(){
    day_tv = findViewById(R.id.day_tv);
    editText = findViewById(R.id.day_ed);
    first=findViewById(R.id.first_tv);
    two=findViewById(R.id.two_tv);
    third=findViewById(R.id.rd_tv);
    twoEd=findViewById(R.id.two_ed);
    thirdEd=findViewById(R.id.rd_ed);
}
}