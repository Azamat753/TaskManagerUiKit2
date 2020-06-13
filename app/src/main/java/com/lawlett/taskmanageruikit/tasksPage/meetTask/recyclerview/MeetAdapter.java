package com.lawlett.taskmanageruikit.tasksPage.meetTask.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.tasksPage.data.model.TaskModel;

import java.util.ArrayList;

public class MeetAdapter extends RecyclerView.Adapter<MeetAdapter.MeetViewHolder> {
    ArrayList<TaskModel> list;

    public MeetAdapter(ArrayList<TaskModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MeetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MeetViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.meet_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MeetViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MeetViewHolder extends RecyclerView.ViewHolder {
        TextView meetTask;

        public MeetViewHolder(@NonNull View itemView) {
            super(itemView);
            meetTask = itemView.findViewById(R.id.meet_task);
        }

        public void onBind(TaskModel taskModel) {
            meetTask.setText(taskModel.meetTask);
        }
    }
}
