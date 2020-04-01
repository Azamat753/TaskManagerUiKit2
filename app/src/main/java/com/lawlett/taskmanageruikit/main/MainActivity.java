package com.lawlett.taskmanageruikit.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.calendarEvents.CalendarEventsFragment;
import com.lawlett.taskmanageruikit.calendarEvents.CalendarFragment;
import com.lawlett.taskmanageruikit.dashboard.DashboardFragment;
import com.lawlett.taskmanageruikit.onboard.BoardActivity;
import com.lawlett.taskmanageruikit.quick.QuickFragment;
import com.lawlett.taskmanageruikit.todo.TodoFragment;
import com.lawlett.taskmanageruikit.utils.IOpenCalendar;
import com.lawlett.taskmanageruikit.utils.Preference;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.luseen.luseenbottomnavigation.BottomNavigation.OnBottomNavigationItemClickListener;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements IOpenCalendar {
    MenuItem prevMenuItem;
    TextView toolbar_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //showBoard();


        initBottomNavigation();
        changeFragment(new DashboardFragment());
        toolbar_title = findViewById(R.id.toolbar_title);


    }

    public void changeFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(this, "click..!!", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void initBottomNavigation() {
        Calendar c = Calendar.getInstance();
        String[] monthName = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль",
                "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
        final int year = c.get(Calendar.YEAR);

        final String month = monthName[c.get(Calendar.MONTH)];

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);

        BottomNavigationItem bottomNavigationItem = new BottomNavigationItem
                ("Прогресс", ContextCompat.getColor(this, R.color.diagramaEvents), R.drawable.diagrama);
        BottomNavigationItem bottomNavigationItem1 = new BottomNavigationItem
                ("События", ContextCompat.getColor(this, R.color.calendarEvents), R.drawable.ic_date);
        BottomNavigationItem bottomNavigationItem2 = new BottomNavigationItem
                ("Задачи", ContextCompat.getColor(this, R.color.taskEvents), R.drawable.check);
        BottomNavigationItem bottomNavigationItem3 = new BottomNavigationItem
                ("Идеи", ContextCompat.getColor(this, R.color.quickEvents), R.drawable.notes);

        bottomNavigationView.addTab(bottomNavigationItem);
        bottomNavigationView.addTab(bottomNavigationItem1);
        bottomNavigationView.addTab(bottomNavigationItem2);
        bottomNavigationView.addTab(bottomNavigationItem3);

        bottomNavigationView.setOnBottomNavigationItemClickListener(new OnBottomNavigationItemClickListener() {
            @Override
            public void onNavigationItemClick(int index) {
                switch (index) {
                    case 0:
                        changeFragment(new DashboardFragment());
                        toolbar_title.setText("Прогресс");
                        break;
                    case 1:
                        changeFragment(new CalendarEventsFragment());

                        toolbar_title.setText(month + " " + year);
                        break;
                    case 2:
                        changeFragment(new TodoFragment());
                        toolbar_title.setText("Задачи");

                        break;
                    case 3:
                        changeFragment(new QuickFragment());
                        toolbar_title.setText("Идеи");
                        break;
                }
            }
        });
    }


    @Override
    public void openCalendar() {
        changeFragment(new CalendarFragment());
    }

    @Override
    public void back() {
        changeFragment(new CalendarEventsFragment());
    }
}
