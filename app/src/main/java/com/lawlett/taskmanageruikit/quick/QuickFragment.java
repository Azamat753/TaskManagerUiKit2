package com.lawlett.taskmanageruikit.quick;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.quick.data.model.QuickModel;
import com.lawlett.taskmanageruikit.quick.recycler.QuickAdapter;
import com.lawlett.taskmanageruikit.utils.App;
import com.lawlett.taskmanageruikit.utils.IOnClickListener;
import com.lawlett.taskmanageruikit.utils.ISetLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuickFragment extends Fragment  implements IOnClickListener,ISetLayoutManager {
    QuickAdapter adapter;
    private List<QuickModel> list;
    FloatingActionButton addQuickBtn;
    int position;
    QuickModel quickModel= new QuickModel();

    public QuickFragment() {
        // Required empty public constructor

    }

    @SuppressLint("FragmentLiveDataObserve")
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_quick, container, false);

        list = new ArrayList<>();
        RecyclerView recyclerViewQuick = root.findViewById(R.id.quick_recycler);
        adapter = new QuickAdapter(list, this,getContext());
        recyclerViewQuick.setAdapter(adapter);
        if (quickModel.getTitle()!=null)
        App.getDataBase().taskDao().getAllLive().observe(this, quickModels -> {
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

        AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());
        dialog.setTitle("Вы хотите отредактировать ?").setMessage("Редактировать задачу")
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getActivity(), QuickActivity.class);
                intent.putExtra("task", list.get(position));
                getActivity().startActivityForResult(intent, 42);
                App.getDataBase().taskDao().delete(list.get(position));
                adapter.notifyDataSetChanged();
            }
        }).show();
    }

    @Override
    public void onItemLongClick(final int position) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(requireContext());
        dialog.setTitle("Вы хотите удалить ?").setMessage("Удалить задачу")
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                App.getDataBase().taskDao().delete(list.get(position));
                adapter.notifyDataSetChanged();
            }
        }).show();
    }


    @Override
    public void grid(ViewGroup container) {
        Toast.makeText(getContext(), "all ready", Toast.LENGTH_SHORT).show();
    }
}
