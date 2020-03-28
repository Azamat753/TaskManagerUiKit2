package com.lawlett.taskmanageruikit.utils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.lawlett.taskmanageruikit.calendarEvents.CalendarEventsFragment;
import com.lawlett.taskmanageruikit.dashboard.DashboardFragment;
import com.lawlett.taskmanageruikit.quick.QuickFragment;
import com.lawlett.taskmanageruikit.todo.TodoFragment;

public class PagerAdapter extends FragmentPagerAdapter {
    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case 0:
                fragment =new DashboardFragment();
                break;
            case 1:
                fragment = new CalendarEventsFragment();
                break;
            case 2:
                fragment = new TodoFragment();
                break;
            case 3:
                fragment=new QuickFragment();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }
        return fragment;
    }
    @Override
    public int getCount() {
        return 4;
    }
}
