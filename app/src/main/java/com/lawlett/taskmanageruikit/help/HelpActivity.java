package com.lawlett.taskmanageruikit.help;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Build;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.main.HelpFragment;
import com.lawlett.taskmanageruikit.onboard.BoardActivity;
import com.lawlett.taskmanageruikit.onboard.BoardFragment;

public class HelpActivity extends AppCompatActivity {
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        viewPager = findViewById(R.id.help_view_pager);
        viewPager.setAdapter(new HelpViewPagerAdapter(getSupportFragmentManager()));
        TabLayout tabLayout = findViewById(R.id.help_tab_layout);
        tabLayout.setupWithViewPager(viewPager, true);

        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setNavigationBarColor(getResources().getColor(R.color.statusBarC));

    }

    class HelpViewPagerAdapter extends FragmentPagerAdapter {
        public HelpViewPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new HelpFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}