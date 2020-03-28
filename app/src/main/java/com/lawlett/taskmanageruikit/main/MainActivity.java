package com.lawlett.taskmanageruikit.main;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView.OnNavigationItemSelectedListener;
import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.calendarEvents.CalendarEventsFragment;
import com.lawlett.taskmanageruikit.dashboard.DashboardFragment;
import com.lawlett.taskmanageruikit.quick.QuickFragment;
import com.lawlett.taskmanageruikit.todo.TodoFragment;
import com.lawlett.taskmanageruikit.utils.PagerAdapter;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.luseen.luseenbottomnavigation.BottomNavigation.OnBottomNavigationItemClickListener;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    MenuItem prevMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changeFragment(new DashboardFragment());

        //todo viewPager    final ViewPager viewPager = findViewById(R.id.view_pager);
        //  viewPager.setAdapter(new PagerAdapter(getSupportFragmentManager()));
//        viewPager.setOffscreenPageLimit(3);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Dashboard");

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigation);

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
                        getSupportActionBar().setTitle("Dashboard");
                        changeFragment(new DashboardFragment());
                        Toast.makeText(MainActivity.this, "0", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        changeFragment(new CalendarEventsFragment());
                        Toast.makeText(MainActivity.this, "1", Toast.LENGTH_SHORT).show();

                        break;
                    case 2:
                        changeFragment(new TodoFragment());
                        getSupportActionBar().setTitle("Задачи");
                        Toast.makeText(MainActivity.this, "2", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        changeFragment(new QuickFragment());
                        Toast.makeText(MainActivity.this, "3", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
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
        if (item.getItemId() == R.id.action_settings) {
            //todo  code
        }
        return true;
    }


}
