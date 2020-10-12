package com.lawlett.taskmanageruikit.tasksPage.addTask.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.tasksPage.data.model.DoneModel;
import com.lawlett.taskmanageruikit.tasksPage.data.model.MeetModel;

import java.util.ArrayList;
import java.util.List;

public class DoneAdapter extends RecyclerView.Adapter<DoneAdapter.DoneViewHolder>  {
    List<DoneModel> list = new ArrayList<>();
    DoneAdapter.IMCheckedListener listener;

    public DoneAdapter(IMCheckedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public DoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DoneAdapter.DoneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.done_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DoneViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateList(List<DoneModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    public class DoneViewHolder extends RecyclerView.ViewHolder {
        CheckBox doneTask;

        public DoneViewHolder(@NonNull View itemView) {
            super(itemView);
            doneTask = itemView.findViewById(R.id.done_task);
        }

        public void onBind(DoneModel doneModel) {
            doneTask.setText(doneModel.getDoneTask());
            doneTask.setChecked(doneModel.isDone);
            doneTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemCheckClick(getAdapterPosition());
                }
            });
        }
    }
    public interface IMCheckedListener {
        void onItemCheckClick(int id);
    }
}
