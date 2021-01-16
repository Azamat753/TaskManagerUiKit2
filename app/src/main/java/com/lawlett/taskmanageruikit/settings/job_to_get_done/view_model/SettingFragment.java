package com.lawlett.taskmanageruikit.settings.job_to_get_done.view_model;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.settings.job_to_get_done.ToChangeThemeDialog;


public class SettingFragment extends Fragment {
    private ImageView back;
    private LinearLayout language_tv, clear_password_layout, clearMinutes_layout, theme_layout, share_layout, achievement_layout;
    private ImageView magick;
    private ListView listView;
    public static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public SettingFragment() {
        // Required empty public constructor
    }


    public static SettingFragment newInstance(String param1, String param2) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
        clickListeners();


    }

    private void initView(View view) {
        clear_password_layout = view.findViewById(R.id.first_layout);
        clearMinutes_layout = view.findViewById(R.id.second_layout);
        theme_layout = view.findViewById(R.id.third_layout);
        back = view.findViewById(R.id.back_view);
        language_tv = view.findViewById(R.id.four_layout);
        share_layout = view.findViewById(R.id.five_layout);
        achievement_layout = view.findViewById(R.id.achievement_layout);
        magick = view.findViewById(R.id.btn_magick);
        listView = view.findViewById(R.id.listView);

        if (Build.VERSION.SDK_INT >= 21)
            getActivity().getWindow().setNavigationBarColor(getResources().getColor(R.color.statusBarC));

    }

    private void clickListeners() {
        theme_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChangeThemeDialog();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().finish();

            }
        });
    }


    public void showChangeThemeDialog() {
//        final String[] listItems = {getString(R.string.light_theme), getString(R.string.dark_theme)};
        FragmentManager fr = getFragmentManager();
        ToChangeThemeDialog dialog = new ToChangeThemeDialog();
        dialog.show(fr, "");
    }

}