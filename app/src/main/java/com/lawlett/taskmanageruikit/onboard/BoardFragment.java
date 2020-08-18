package com.lawlett.taskmanageruikit.onboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.main.MainActivity;
import com.lawlett.taskmanageruikit.utils.Preference;

import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoardFragment extends Fragment {
    LottieAnimationView calendar_anim, notes_anim, todo_anim, time_anim;
    TextView title_tv, desc_tv, start_tv;

    public BoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title_tv = view.findViewById(R.id.title_tv);
        desc_tv = view.findViewById(R.id.desc_tv);
        start_tv = view.findViewById(R.id.start_tv);

        calendar_anim = view.findViewById(R.id.calendar_animation);
        todo_anim = view.findViewById(R.id.todo_animation);
        notes_anim = view.findViewById(R.id.notes_animation);
        time_anim = view.findViewById(R.id.time_animation);

        int pos = getArguments().getInt("pos");
        switch (pos) {
            case 0:
                title_tv.setText(R.string.event_calendar);
                desc_tv.setText(R.string.fast_add_your_event);
                calendar_anim.setVisibility(View.VISIBLE);
                todo_anim.setVisibility(View.GONE);
                notes_anim.setVisibility(View.GONE);
                break;
            case 1:
                title_tv.setText(R.string.done_tasks);
                desc_tv.setText(R.string.list_tasks_help_you);
                calendar_anim.setVisibility(View.GONE);
                todo_anim.setVisibility(View.VISIBLE);
                notes_anim.setVisibility(View.GONE);
                break;
            case 2:
                title_tv.setText(R.string.record_idea_simple);
                desc_tv.setText(R.string.most_effect_idea);
                calendar_anim.setVisibility(View.GONE);
                todo_anim.setVisibility(View.GONE);
                notes_anim.setVisibility(View.VISIBLE);
                break;
            case 3:
                title_tv.setText(R.string.check_timing);
                desc_tv.setText(R.string.plus_you_kpd);
                calendar_anim.setVisibility(View.GONE);
                todo_anim.setVisibility(View.GONE);
                notes_anim.setVisibility(View.GONE);
                time_anim.setVisibility(View.VISIBLE);
                start_tv.setVisibility(View.VISIBLE);
                break;

        }

        start_tv.setOnClickListener(v -> {

            Preference.getInstance(getContext()).saveShown();
            startActivity(new Intent(getContext(), MainActivity.class));
            Objects.requireNonNull(getActivity()).finish();
        });

    }
}
