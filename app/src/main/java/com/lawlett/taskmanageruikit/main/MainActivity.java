package com.lawlett.taskmanageruikit.main;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
import com.lawlett.taskmanageruikit.dashboard.DashboardFragment;
import com.lawlett.taskmanageruikit.idea.IdeasFragment;
import com.lawlett.taskmanageruikit.idea.data.model.QuickModel;
import com.lawlett.taskmanageruikit.idea.recycler.QuickAdapter;
import com.lawlett.taskmanageruikit.service.MessageService;
import com.lawlett.taskmanageruikit.settings.SettingsActivity;
import com.lawlett.taskmanageruikit.tasks.TasksFragment;
import com.lawlett.taskmanageruikit.timing.fragment.TimingFragment;
import com.lawlett.taskmanageruikit.utils.App;
import com.lawlett.taskmanageruikit.utils.LanguagePreference;
import com.lawlett.taskmanageruikit.utils.PasswordPassDonePreference;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationItem;
import com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView;
import com.luseen.luseenbottomnavigation.BottomNavigation.OnBottomNavigationItemClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView toolbar_title;
    ImageView  settings_view;
    ImageView btnGrid;
    private List<QuickModel> list;

    QuickAdapter adapter;

    AlarmManager mAlarm;
    long time;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setNavigationBarColor(getResources().getColor(R.color.statusBarC));

        initBottomNavigation();

        changeFragment(new DashboardFragment());

        toolbar_title = findViewById(R.id.toolbar_title);
        settings_view = findViewById(R.id.settings_view);
        btnGrid = findViewById(R.id.tool_btn_grid);

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
        settings_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        setNotification();
    }

    private void setNotification() {
        Intent i = new Intent(getBaseContext(), MessageService.class);
        i.putExtra("displayText", "sample text");
        i.putExtra(MessageService.TITLE, "Planner");
        i.putExtra(MessageService.TEXT, "пора ставить новые цели!");
        PendingIntent pi = PendingIntent.getBroadcast(this.getApplicationContext(), 0, i,PendingIntent.FLAG_UPDATE_CURRENT);
        mAlarm = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.HOUR, 24);
        time = calendar.getTimeInMillis();

        mAlarm.setInexactRepeating(AlarmManager.RTC_WAKEUP,time ,AlarmManager.INTERVAL_DAY,pi);
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
        String[] monthName = {getString(R.string.january), getString(R.string.february), getString(R.string.march), getString(R.string.april), getString(R.string.may), getString(R.string.june), getString(R.string.july),
                getString(R.string.august), getString(R.string.september), getString(R.string.october), getString(R.string.november), getString(R.string.december)};

        final String month = monthName[c.get(Calendar.MONTH)];

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigation);

        BottomNavigationItem bottomNavigationItem = new BottomNavigationItem
                (getString(R.string.progress), ContextCompat.getColor(this, R.color.navigation_background), R.drawable.ic_progress);
        BottomNavigationItem bottomNavigationItem1 = new BottomNavigationItem
                (getString(R.string.tasks), ContextCompat.getColor(this, R.color.navigation_background), R.drawable.ic_check);
        BottomNavigationItem bottomNavigationItem4 = new BottomNavigationItem
                (getString(R.string.timing), ContextCompat.getColor(this, R.color.navigation_background), R.drawable.ic_timer);
        BottomNavigationItem bottomNavigationItem2 = new BottomNavigationItem
                (getString(R.string.events), ContextCompat.getColor(this, R.color.navigation_background), R.drawable.ic_date_white);
        BottomNavigationItem bottomNavigationItem3 = new BottomNavigationItem
                (getString(R.string.ideas), ContextCompat.getColor(this, R.color.navigation_background), R.drawable.ic_idea);

        bottomNavigationView.addTab(bottomNavigationItem);
        bottomNavigationView.addTab(bottomNavigationItem1);
        bottomNavigationView.addTab(bottomNavigationItem4);
        bottomNavigationView.addTab(bottomNavigationItem2);
        bottomNavigationView.addTab(bottomNavigationItem3);

        bottomNavigationView.setOnBottomNavigationItemClickListener(new OnBottomNavigationItemClickListener() {
            @Override
            public void onNavigationItemClick(int index) {
                switch (index) {
                    case 0:
                        changeFragment(new DashboardFragment());
                        toolbar_title.setText(R.string.progress);
                        break;
                    case 1:
                        changeFragment(new TasksFragment());
                        toolbar_title.setText(R.string.tasks);
                        break;
                    case 2:
                        changeFragment(new TimingFragment());
                        toolbar_title.setText(R.string.timing);
                        break;
                    case 3:
                        changeFragment(new CalendarEventsFragment());
                        toolbar_title.setText(month + " " + year);
                        break;
                    case 4:
                        changeFragment(new IdeasFragment());
                        toolbar_title.setText(R.string.ideas);
                        btnGrid.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PasswordPassDonePreference.getInstance(MainActivity.this).clearSettings();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
        dialog.setTitle(R.string.are_you_sure).setMessage(R.string.complete_work)
                .setNegativeButton(R.string.no, (dialog1, which) ->
                        dialog1.cancel())
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                }).show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        LanguagePreference.getInstance(MainActivity.this).saveLanguage(lang);
    }

    private void loadLocale() {
        String language = LanguagePreference.getInstance(this).getLanguage();
        setLocale(language);
    }

}
