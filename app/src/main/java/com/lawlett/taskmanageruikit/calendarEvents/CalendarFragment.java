package com.lawlett.taskmanageruikit.calendarEvents;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.utils.IOpenCalendar;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class CalendarFragment extends Fragment {
    Calendar calendar;

    public CalendarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.back_btn).setOnClickListener(v -> {
            IOpenCalendar listener= (IOpenCalendar)getActivity();
            listener.back();
        });
    }
}
