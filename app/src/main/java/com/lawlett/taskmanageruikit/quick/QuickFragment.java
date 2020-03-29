package com.lawlett.taskmanageruikit.quick;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.quick.recycler.QuickAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuickFragment extends Fragment {

    public QuickFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quick, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerViewQuick = view.findViewById(R.id.quick_recycler);
        QuickAdapter adapter = new QuickAdapter(getContext());
        recyclerViewQuick.setAdapter(adapter);
    }
}
