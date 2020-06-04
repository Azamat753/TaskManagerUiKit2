package com.lawlett.taskmanageruikit.tasksPage.todo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.tasksPage.personalTask.PersonalActivity;

public class TodoFragment extends Fragment {
    ImageView personalImage, workImage, meetImage, homeImage, privateImage, addNewImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        personalImage = view.findViewById(R.id.person_image);
        workImage = view.findViewById(R.id.work_image);
        meetImage = view.findViewById(R.id.meet_image);
        homeImage = view.findViewById(R.id.home_image);
        privateImage = view.findViewById(R.id.private_image);
        addNewImage = view.findViewById(R.id.add_image);

        personalImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
startActivity(new Intent(getContext(),PersonalActivity.class));
        }
        });
    }

    public void changeFragment(Fragment fragment) {
        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}
