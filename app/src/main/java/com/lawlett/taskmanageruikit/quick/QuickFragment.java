package com.lawlett.taskmanageruikit.quick;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lawlett.data.model.QuickModel;
import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.quick.recycler.QuickAdapter;
import com.lawlett.taskmanageruikit.utils.App;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuickFragment extends Fragment {
    QuickAdapter adapter;
    private List<QuickModel> list;
    FloatingActionButton addQuickBtn;

    public QuickFragment() {
        // Required empty public constructor
    }

    @SuppressLint("FragmentLiveDataObserve")
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_quick, container, false);

        list = new ArrayList<>();

            App.getDataBase().taskDao().getAllLive().observe(this, new Observer<List<QuickModel>>() {
                @Override
                public void onChanged(List<QuickModel> quickModels) {
                list.clear();
                list.addAll(quickModels);
                adapter.notifyDataSetChanged();
                }
            });
//
        RecyclerView recyclerViewQuick = root.findViewById(R.id.quick_recycler);
        adapter = new QuickAdapter(list,getContext());
        recyclerViewQuick.setAdapter(adapter);

        addQuickBtn = root.findViewById(R.id.add_quick_btn);
        addQuickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), QuickActivity.class));
            }
        });
        return root;
    }


}
