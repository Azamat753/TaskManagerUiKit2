package com.lawlett.taskmanageruikit.tasksPage.doneTask.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.tasksPage.data.model.DoneModel;

import java.util.List;

public class DoneAdapter extends RecyclerView.Adapter<DoneAdapter.DoneViewHolder> {
    List<DoneModel> list;

    public DoneAdapter(List<DoneModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public DoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DoneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.done_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull DoneViewHolder holder, int position) {
holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DoneViewHolder extends RecyclerView.ViewHolder {
        TextView title,description;
        public DoneViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.done_title);
            description=itemView.findViewById(R.id.done_desc);
        }
        public void onBind(DoneModel doneModel){
            title.setText(doneModel.doneTitle);
            description.setText(doneModel.doneDesc);
        }
    }
}
