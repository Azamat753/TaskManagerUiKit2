package com.lawlett.taskmanageruikit.todo;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.tasksPage.homeTask.HomeActivity;
import com.lawlett.taskmanageruikit.tasksPage.meetTask.MeetActivity;
import com.lawlett.taskmanageruikit.tasksPage.personalTask.PersonalActivity;
import com.lawlett.taskmanageruikit.tasksPage.privateTask.PrivateActivity;
import com.lawlett.taskmanageruikit.tasksPage.workTask.WorkActivity;


public class TodoFragment extends Fragment {
    ImageView personalImage, workImage, meetImage, homeImage, privateImage, addNewImage;
View dotsPerson,dotsWork,dotsMeet,dotsHome,dotsPrivate;
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

        dotsPerson=view.findViewById(R.id.more_1);
        dotsWork=view.findViewById(R.id.more_2);
        dotsMeet =view.findViewById(R.id.more_3);
        dotsHome=view.findViewById(R.id.more_4);
        dotsPrivate=view.findViewById(R.id.more_5);

        dotsPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(getContext(),dotsPerson);
                popupMenu.getMenuInflater().inflate(R.menu.popupmenutodo,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.delete_all_list:

                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }

        });
        dotsWork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(getContext(),dotsWork);
                popupMenu.getMenuInflater().inflate(R.menu.popupmenutodo,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.delete_all_list:

                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }

        });
        dotsMeet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(getContext(),dotsMeet);
                popupMenu.getMenuInflater().inflate(R.menu.popupmenutodo,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.delete_all_list:

                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }

        });
        dotsHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(getContext(),dotsHome);
                popupMenu.getMenuInflater().inflate(R.menu.popupmenutodo,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.delete_all_list:

                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        dotsPrivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu=new PopupMenu(getContext(),dotsPrivate);
                popupMenu.getMenuInflater().inflate(R.menu.popupmenutodo,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.delete_all_list:

                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }

        });

        personalImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PersonalActivity.class));
            }
        });
        workImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), WorkActivity.class));
            }
        });
        meetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MeetActivity.class));
            }
        });

        homeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), HomeActivity.class));
            }
        });
        privateImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), PrivateActivity.class));
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
