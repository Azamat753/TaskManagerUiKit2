package com.lawlett.taskmanageruikit.tasksPage.workTask.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.tasksPage.data.model.TaskModel;

import java.util.ArrayList;

public class WorkAdapter extends RecyclerView.Adapter<WorkAdapter.WorkViewHolder> {
    ArrayList<TaskModel> list;

    public WorkAdapter(ArrayList<TaskModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public WorkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WorkViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.work_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WorkViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class WorkViewHolder extends RecyclerView.ViewHolder {
        TextView workTask;

        public WorkViewHolder(@NonNull View itemView) {
            super(itemView);
            workTask = itemView.findViewById(R.id.work_task);
        }

        public void onBind(TaskModel taskModel) {
            workTask.setText(taskModel.workTask);
        }
    }
}
