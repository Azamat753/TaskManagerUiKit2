package com.lawlett.taskmanageruikit.calendarEvents;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.calendarEvents.data.model.CalendarTaskModel;
import com.lawlett.taskmanageruikit.calendarEvents.recycler.CalendarEventAdapter;
import com.lawlett.taskmanageruikit.utils.App;
import com.lawlett.taskmanageruikit.utils.ICalendarEventOnClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;
import devs.mulham.horizontalcalendar.utils.HorizontalCalendarListener;

public class CalendarEventsFragment extends Fragment implements ICalendarEventOnClickListener {
    RecyclerView recyclerViewToday;
    FloatingActionButton addEventBtn;
    List<CalendarTaskModel> list;
    CalendarEventAdapter adapter;
    View colorView;
    int position, pos;

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

        list = new ArrayList<>();

        App.getDataBase().dataDao().getAllLive().observe(this, calendarTaskModels -> {
            if (calendarTaskModels != null) {
                list.clear();
                list.addAll(calendarTaskModels);
                adapter.notifyDataSetChanged();
            }
        });

        return inflater.inflate(R.layout.fragment_calendar_events, container, false);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        /* starts before 1 month from now */


        colorView = view.findViewById(R.id.color_view);


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
//                Intent intent = new Intent(getContext(), TodayEvent.class);
//                intent.putExtra("month",String.valueOf(date.getTime().getMonth()));
//                intent.putExtra("day",String.valueOf(date.getTime().getDate()));
//                startActivity(intent);
            }

            @Override
            public void onCalendarScroll(HorizontalCalendarView calendarView, int dx, int dy) {

            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @SuppressLint("LogNotTimber")
            @Override
            public boolean onDateLongClicked(Calendar date, int position) {

                return true;
            }
        });

        recyclerViewToday = view.findViewById(R.id.today_recycler);
        adapter = new CalendarEventAdapter((ArrayList<CalendarTaskModel>) list, this, getContext());
        recyclerViewToday.setAdapter(adapter);

        addEventBtn = view.findViewById(R.id.add_task_btn);
        addEventBtn.setColorFilter(Color.WHITE);
        addEventBtn.setBackgroundColor(R.color.plus_background);
        addEventBtn.setOnClickListener(v -> startActivity(new Intent(getContext(), AddEventActivity.class)));


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();

                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(list, i, i + 1);

                        int order1 = (int) list.get(i).getId();
                        int order2 = (int) list.get(i + 1).getId();
                        list.get(i).setId(order2);
                        list.get(i + 1).setId(order1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(list, i, i - 1);

                        int order1 = (int) list.get(i).getId();
                        int order2 = (int) list.get(i - 1).getId();
                        list.get(i).setId(order2);
                        list.get(i - 1).setId(order1);
                    }
                }
                adapter.notifyItemMoved(fromPosition, toPosition);
                return true;
            }


            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                App.getDataBase().dataDao().updateWord(list);
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                pos = viewHolder.getAdapterPosition();
                App.getDataBase().dataDao().delete(list.get(pos));
                adapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Удалено", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerViewToday);

    }
    @Override
    public void onItemClick(int position) {
        this.position = position;
        Intent intent = new Intent(getContext(), AddEventActivity.class);
        intent.putExtra("calendar", list.get(position));
        adapter.notifyDataSetChanged();
        Objects.requireNonNull(getActivity()).startActivityForResult(intent, 42);
    }
}
