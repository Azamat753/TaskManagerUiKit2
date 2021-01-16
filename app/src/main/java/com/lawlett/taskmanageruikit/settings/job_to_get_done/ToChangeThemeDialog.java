package com.lawlett.taskmanageruikit.settings.job_to_get_done;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.settings.job_to_get_done.color_setting_adapter.ColorAdapter;
import com.lawlett.taskmanageruikit.settings.job_to_get_done.interfaces.OnClickListenerTheme;
import com.lawlett.taskmanageruikit.settings.job_to_get_done.interfaces.ThemePosCarrier;
import com.lawlett.taskmanageruikit.settings.job_to_get_done.view_model.SettingViewModel;
import com.lawlett.taskmanageruikit.settings.job_to_get_done.view_model.ThemeViewModel;
import com.lawlett.taskmanageruikit.utils.ThemePreference;

import java.util.ArrayList;

public class ToChangeThemeDialog extends DialogFragment implements OnClickListenerTheme {

    private SettingViewModel mViewModel;
    private RecyclerView recyclerView;
    private ArrayList<ThemeViewModel> list;
    private ColorAdapter themeAdapter;
    private ThemePosCarrier themePosCarrier;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.options_to_theme_view, container);
        themePosCarrier = (ThemePosCarrier) getActivity();
        setRecyclerView(view);
        return view;
    }


//    public void setClickListener(ThemePosCarrier themePosCarrier){
//        this.themePosCarrier = themePosCarrier;
//    }

    private void setRecyclerView(View view) {
        list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            if (i == 0) list.add(new ThemeViewModel(R.drawable.ic_light_mode, false));
            if (i == 1) list.add(new ThemeViewModel(R.drawable.ic_dark_mode, false));
            if (i == 2) list.add(new ThemeViewModel(R.drawable.ic_purpule, false));
            if (i == 3) list.add(new ThemeViewModel(R.drawable.ic_orange_mode, false));
            if (i == 4) list.add(new ThemeViewModel(R.drawable.ic_red_mode, false));
        }

        /*recyclerview*/
        recyclerView = view.findViewById(R.id.recycler_select_theme);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));
        themeAdapter = new ColorAdapter(list, requireContext());
        recyclerView.setAdapter(themeAdapter);
        themeAdapter.setClickListener(this);
    }

    @Override
    public void onClickTheme(int position) {
        themePosCarrier.applyTheme(position);
        }
}
