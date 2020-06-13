package com.lawlett.taskmanageruikit.tasksPage.privateTask.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.tasksPage.data.model.TaskModel;

import java.util.ArrayList;

public class PrivateAdapter extends RecyclerView.Adapter<PrivateAdapter.PrivateViewHolder> {
    ArrayList<TaskModel> list;

    public PrivateAdapter(ArrayList<TaskModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public PrivateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PrivateViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.private_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PrivateViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PrivateViewHolder extends RecyclerView.ViewHolder {
        TextView privateTask;

        public PrivateViewHolder(@NonNull View itemView) {
            super(itemView);
            privateTask = itemView.findViewById(R.id.private_task);
        }

        public void onBind(TaskModel taskModel) {
            privateTask.setText(taskModel.privateTask);
        }
    }
}
