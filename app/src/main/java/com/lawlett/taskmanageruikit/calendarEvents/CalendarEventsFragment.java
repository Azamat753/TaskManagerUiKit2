package com.lawlett.taskmanageruikit.calendarEvents;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.calendarEvents.data.model.CalendarTaskModel;
import com.lawlett.taskmanageruikit.calendarEvents.recycler.DayAdapter;
import com.lawlett.taskmanageruikit.utils.CalendarEventsStorage;
import com.lawlett.taskmanageruikit.utils.IOpenCalendar;

import java.util.ArrayList;
import java.util.Calendar;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class CalendarEventsFragment extends Fragment {
    CalendarView allCalendar;
    RecyclerView recyclerViewToday, recyclerViewTomorrow, recyclerViewAfterTomorrow;
    FloatingActionButton addEventBtn;
    ArrayList<CalendarTaskModel> list = new ArrayList<>();
    CalendarTaskModel calendarTaskModel = new CalendarTaskModel();
    DayAdapter adapter;
    TextView todayTv;

    public CalendarEventsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_calendar_events, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        allCalendar = view.findViewById(R.id.all_calendar_view);


        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);

        final HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(getActivity(), R.id.calendarView)
                .range(startDate, endDate)
                .datesNumberOnScreen(5)
                .build();

        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @SuppressLint({"LogNotTimber", "NewApi"})
            @Override
            public void onDateSelected(Calendar date, int position) {
                Toast.makeText(getContext(), "" + date.getTime(), Toast.LENGTH_SHORT).show();
                Log.e("date", "onDateSelected: " + "weekyear" + date.getWeekYear() + "firstdayofweek" + date.getFirstDayOfWeek() + "getTime" + date.getTime());
            }

            @Override
            public void onCalendarScroll(HorizontalCalendarView calendarView,
                                         int dx, int dy) {
                Log.e("date", "onCalendarScroll: " + dx + dy);
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @SuppressLint("LogNotTimber")
            @Override
            public boolean onDateLongClicked(Calendar date, int position) {
                Toast.makeText(getContext(), "" + date.getTime(), Toast.LENGTH_SHORT).show();
                Log.e("date", "onDateLongClicked: " + "weekyear" + date.getWeekYear() + "firstdayofweek" + date.getFirstDayOfWeek() + "getTime" + date.getTime());

                IOpenCalendar listener = (IOpenCalendar) getActivity();
                listener.openCalendar();
                return true;
            }
        });

        recyclerViewToday = view.findViewById(R.id.today_recycler);
        adapter = new DayAdapter(list);
        recyclerViewToday.setAdapter(adapter);

        addEventBtn = view.findViewById(R.id.add_task_btn);
        addEventBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddEventActivity.class));
            }
        });
        loadData();
    }

    public void changeFragment(Fragment fragment) {
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

    public void loadData() {
        list.addAll(CalendarEventsStorage.read(getContext()));
        adapter.notifyDataSetChanged();

    }
}
