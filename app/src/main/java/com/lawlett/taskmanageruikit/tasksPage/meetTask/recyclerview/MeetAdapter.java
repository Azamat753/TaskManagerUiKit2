package com.lawlett.taskmanageruikit.tasksPage.meetTask.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.tasksPage.data.model.MeetModel;

import java.util.ArrayList;
import java.util.List;

public class MeetAdapter extends RecyclerView.Adapter<MeetAdapter.MeetViewHolder> {
    List<MeetModel> list;

    public MeetAdapter(ArrayList<MeetModel> list) {
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

        public void onBind(MeetModel meetModel) {
            meetTask.setText(meetModel.getMeetTask());
        }
    }
}
