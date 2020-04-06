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

/**
 * A simple {@link Fragment} subclass.
 */
public class BoardFragment extends Fragment {
    LottieAnimationView calendar_anim, notes_anim, todo_anim;
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


        int pos = getArguments().getInt("pos");
        switch (pos) {
            case 0:
                title_tv.setText("События по календарю");
                desc_tv.setText("Быстрое добавление ваших событий & быстрое переключения  между датами & просмотр недель");
                calendar_anim.setVisibility(View.VISIBLE);
                todo_anim.setVisibility(View.GONE);
                notes_anim.setVisibility(View.GONE);
                break;
            case 1:
                title_tv.setText("Выполнение задач");
                desc_tv.setText("Cписок задач поможет вам наполнить ваш день и добиться цели");
                calendar_anim.setVisibility(View.GONE);
                todo_anim.setVisibility(View.VISIBLE);
                notes_anim.setVisibility(View.GONE);
                break;
            case 2:
                title_tv.setText("Быстрые задачи");
                desc_tv.setText("Самый быстрый способ перенести идеи, мысли и задачи, не теряя внимания");
                calendar_anim.setVisibility(View.GONE);
                todo_anim.setVisibility(View.GONE);
                notes_anim.setVisibility(View.VISIBLE);
                start_tv.setVisibility(View.VISIBLE);
                break;

        }
        start_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Preference.getInstance(getContext()).saveShown();
                startActivity(new Intent(getContext(), MainActivity.class));
                getActivity().finish();

            }
        });

    }

}
