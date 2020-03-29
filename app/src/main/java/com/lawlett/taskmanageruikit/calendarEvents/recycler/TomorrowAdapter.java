package com.lawlett.taskmanageruikit.calendarEvents.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.R;

import java.util.ArrayList;

public class TomorrowAdapter extends RecyclerView.Adapter<TomorrowAdapter.TomorrowViewHolder> {
    ArrayList<String> list;

    public TomorrowAdapter() {
        list = new ArrayList<>();
        list.add("Concert");
    }
    @NonNull
    @Override
    public TomorrowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new TomorrowViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull TomorrowViewHolder holder, int position) {
holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TomorrowViewHolder extends RecyclerView.ViewHolder {
        TextView task, data;

        public TomorrowViewHolder(@NonNull View itemView) {
            super(itemView);
            task = itemView.findViewById(R.id.task_tv);
            data = itemView.findViewById(R.id.data_tv);

        }

        public void onBind(String s) {
            task.setText(s);
            data.setText(s);
        }
    }
}
