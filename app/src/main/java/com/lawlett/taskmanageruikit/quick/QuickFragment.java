package com.lawlett.taskmanageruikit.quick;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.quick.data.model.QuickModel;
import com.lawlett.taskmanageruikit.quick.recycler.QuickAdapter;
import com.lawlett.taskmanageruikit.utils.App;
import com.lawlett.taskmanageruikit.utils.IQuickOnClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuickFragment extends Fragment implements IQuickOnClickListener {
    QuickAdapter adapter;
    private List<QuickModel> list;
    FloatingActionButton addQuickBtn;
    int position;
    int pos;
    RecyclerView recyclerViewQuick;

    public QuickFragment() {
        // Required empty public constructor

    }

    @SuppressLint("FragmentLiveDataObserve")
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_quick, container, false);

        list = new ArrayList<>();

        App.getDataBase().taskDao().getAllLive().observe(this, quickModels -> {
            if (quickModels != null)
                list.clear();
            list.addAll(quickModels);
            adapter.notifyDataSetChanged();

        });
        addQuickBtn = root.findViewById(R.id.add_quick_btn);
        addQuickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), QuickActivity.class));
            }
        });
        return root;
    }

    @Override
    public void onItemClick(final int position) {
        this.position = position;
        Intent intent = new Intent(getActivity(), QuickActivity.class);
        intent.putExtra("task", list.get(position));
        getActivity().startActivityForResult(intent, 42);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewQuick = view.findViewById(R.id.quick_recycler);
        adapter = new QuickAdapter(list, this, getContext());
        recyclerViewQuick.setAdapter(adapter);
        recyclerViewQuick.setLayoutManager(new GridLayoutManager(getContext(), 1));

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                pos = viewHolder.getAdapterPosition();
                App.getDataBase().taskDao().delete(list.get(pos));
                adapter.notifyDataSetChanged();
                Toast.makeText(getContext(), "Удалено", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerViewQuick);
    }

}
