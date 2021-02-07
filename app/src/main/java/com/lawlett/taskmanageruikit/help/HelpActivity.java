package com.lawlett.taskmanageruikit.help;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.help.recycler.HelpAdapter;
import com.lawlett.taskmanageruikit.help.recycler.HelpModel;
import com.lawlett.taskmanageruikit.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifImageView;

public class HelpActivity extends AppCompatActivity  {
    RecyclerView recyclerView;
    List<HelpModel>helpModelList = new ArrayList<>();
    HelpModel helpModel;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        dialog = new Dialog(this);

//        if (Build.VERSION.SDK_INT >= 21)
//            getWindow().setNavigationBarColor(getResources().getColor(R.color.statusBarC));

        helpModelList.add(new HelpModel(getResources().getString(R.string.helper_dialog_text), R.drawable.change1));
        helpModelList.add(new HelpModel(getResources().getString(R.string.move_tasks), R.drawable.move1));
        helpModelList.add(new HelpModel(getResources().getString(R.string.delete_task), R.drawable.delete1));
        recyclerView = findViewById(R.id.helpRecyclerView);
        HelpAdapter helpAdapter = new HelpAdapter(this, helpModelList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(helpAdapter);

        helpAdapter.setOnItemClickListener(new HelpAdapter.MyOnItemClickListener() {
            @Override
            public void onItemClick(int id) {
                Toast.makeText(getApplicationContext(), String.valueOf(id), Toast.LENGTH_LONG).show();
                dialog.setContentView(R.layout.fragment_help);
                TextView title = dialog.findViewById(R.id.fragment_help_tv);
                GifImageView gif = dialog.findViewById(R.id.fragment_help_gif);
                Button button = dialog.findViewById(R.id.fragment_help_button);

                title.setText(helpModelList.get(id).getTitle());
                gif.setImageResource(helpModelList.get(id).getImage());
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("help", "fromHelp");
        startActivity(intent);
    }

}