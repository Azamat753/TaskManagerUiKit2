package com.lawlett.taskmanageruikit.calendarEvents;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.Toast;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.calendarEvents.recycler.AfterTomorrowAdapter;
import com.lawlett.taskmanageruikit.calendarEvents.recycler.TodayAdapter;
import com.lawlett.taskmanageruikit.calendarEvents.recycler.TomorrowAdapter;
import com.lawlett.taskmanageruikit.utils.IOpenCalendar;

import java.util.Calendar;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class CalendarEventsFragment extends Fragment {
    CalendarView allCalendar;
    RecyclerView recyclerViewToday,recyclerViewTomorrow,recyclerViewAfterTomorrow;

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
            @Override
            public void onDateSelected(Calendar date, int position) {
                Toast.makeText(getContext(), "dataSelected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCalendarScroll(HorizontalCalendarView calendarView,
                                         int dx, int dy) {
                Toast.makeText(getContext(), "dataScroll", Toast.LENGTH_SHORT).show();
            }

            @Override
            public boolean onDateLongClicked(Calendar date, int position) {
                Toast.makeText(getContext(), "datalongClicked", Toast.LENGTH_SHORT).show();
                IOpenCalendar listener = (IOpenCalendar)getActivity();
                listener.openCalendar();
                return true;
            }
        });


        recyclerViewToday = view.findViewById(R.id.today_recycler);
        TodayAdapter adapter = new TodayAdapter();
        recyclerViewToday.setAdapter(adapter);

        recyclerViewTomorrow = view.findViewById(R.id.tomorrow_recycler);
        TomorrowAdapter adapterTomorrow = new TomorrowAdapter();
        recyclerViewTomorrow.setAdapter(adapterTomorrow);

        recyclerViewAfterTomorrow = view.findViewById(R.id.after_tomorrow_recycler);
        AfterTomorrowAdapter adapterAfterTomorrow = new AfterTomorrowAdapter();
        recyclerViewAfterTomorrow.setAdapter(adapterAfterTomorrow);


    }
    public void changeFragment(Fragment fragment) {
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}
