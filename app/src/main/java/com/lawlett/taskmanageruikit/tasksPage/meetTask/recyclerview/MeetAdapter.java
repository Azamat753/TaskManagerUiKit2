package com.lawlett.taskmanageruikit.tasksPage.meetTask.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.tasksPage.data.model.MeetModel;
import com.lawlett.taskmanageruikit.utils.IMeetOnClickListener;

import java.util.ArrayList;
import java.util.List;

public class MeetAdapter extends RecyclerView.Adapter<MeetAdapter.MeetViewHolder> {
    List<MeetModel> list;
Context context;
IMeetOnClickListener listener;
    public MeetAdapter(ArrayList<MeetModel> list,Context context,IMeetOnClickListener listener) {
        this.list = list;
        this.listener=listener;
        this.context=context;
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

    public class MeetViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView meetTask;

        public MeetViewHolder(@NonNull View itemView) {
            super(itemView);
            meetTask = itemView.findViewById(R.id.meet_task);
            itemView.setOnLongClickListener(this);
        }

        public void onBind(MeetModel meetModel) {
            meetTask.setText(meetModel.getMeetTask());
        }

        @Override
        public boolean onLongClick(View v) {
            listener.onItemLongClick(getAdapterPosition());
            return false;
        }
    }
}
