package com.lawlett.taskmanageruikit.main;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.calendarEvents.CalendarEventsFragment;
import com.lawlett.taskmanageruikit.calendarEvents.CalendarFragment;
import com.lawlett.taskmanageruikit.dashboard.DashboardFragment;
import com.lawlett.taskmanageruikit.quick.QuickFragment;
import com.lawlett.taskmanageruikit.quick.data.model.QuickModel;
import com.lawlett.taskmanageruikit.quick.recycler.QuickAdapter;
import com.lawlett.taskmanageruikit.settings.SettingsActivity;
import com.lawlett.taskmanageruikit.todo.TodoFragment;
import com.lawlett.taskmanageruikit.utils.App;
import com.lawlett.taskmanageruikit.utils.IOpenCalendar;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.luseen.luseenbottomnavigation.BottomNavigation.OnBottomNavigationItemClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements IOpenCalendar {
    TextView toolbar_title;
    ImageView more_btn,settings_view;
    private List<QuickModel> list;

    QuickAdapter adapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initBottomNavigation();
        changeFragment(new DashboardFragment());
        toolbar_title = findViewById(R.id.toolbar_title);
        more_btn = findViewById(R.id.more_btn);
        settings_view=findViewById(R.id.settings_view);

        list = new ArrayList<>();
        adapter = new QuickAdapter(list, null, this);
        list = App.getDataBase().taskDao().getAll();
        App.getDataBase().taskDao().getAllLive().observe(this, new Observer<List<QuickModel>>() {
            @Override
            public void onChanged(List<QuickModel> tasks) {
                list.clear();
                list.addAll(tasks);
                adapter.notifyDataSetChanged();


            }
        });

        settings_view.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, SettingsActivity.class), ActivityOptions.makeSceneTransitionAnimation(this).toBundle()));
    }


    public void changeFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    public void initBottomNavigation() {
        Calendar c = Calendar.getInstance();
        final int year = c.get(Calendar.YEAR);
        String[] monthName = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль",
                "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};

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
                        more_btn.setVisibility(View.GONE);
                        break;
                    case 1:
                        changeFragment(new CalendarEventsFragment());
                        more_btn.setVisibility(View.GONE);
                        toolbar_title.setText(month + " " + year);
                        break;
                    case 2:
                        changeFragment(new TodoFragment());
                        toolbar_title.setText("Задачи");
                        more_btn.setVisibility(View.GONE);
                        break;
                    case 3:
                        changeFragment(new QuickFragment());
                        toolbar_title.setText("Идеи");
                        more_btn.setVisibility(View.VISIBLE);
                        more_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                PopupMenu popupMenu = new PopupMenu(MainActivity.this, more_btn);
                                popupMenu.getMenuInflater().inflate(R.menu.popupmenu, popupMenu.getMenu());
                                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem item) {
                                        switch (item.getItemId()) {
                                            case R.id.delete_all_popup:
                                                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                                                dialog.setTitle("Вы уверены ?").setMessage("Очистить список")
                                                        .setNegativeButton("Нет", (dialog1, which) ->

                                                                dialog1.cancel())

                                                        .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                                            @Override
                                                            public void onClick(DialogInterface dialog, int which) {
//
                                                App.getDataBase().taskDao().deleteAll(list);
                                                adapter.notifyDataSetChanged();
                                                            }
                                                        }).show();

                                                break;
                                        }

                                        return false;
                                    }
                                });
                                popupMenu.show();
                            }
                        });
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
