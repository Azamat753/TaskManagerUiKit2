package com.lawlett.taskmanageruikit.tasksPage.doneTask.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.tasksPage.data.model.DoneModel;
import com.lawlett.taskmanageruikit.utils.IDoneOnClickListener;

import java.util.List;

public class DoneAdapter extends RecyclerView.Adapter<DoneAdapter.DoneViewHolder> {
    List<DoneModel> list;
Context context;
IDoneOnClickListener listener;
    public DoneAdapter(List<DoneModel> list,Context context,IDoneOnClickListener listener) {
        this.list = list;
        this.context=context;
        this.listener=listener;
    }

    @NonNull
    @Override
    public DoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DoneViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.done_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DoneViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class DoneViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView title, description;

        public DoneViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.done_title);
            description = itemView.findViewById(R.id.done_desc);
            itemView.setOnLongClickListener(this);
        }

        public void onBind(DoneModel doneModel) {
            title.setText(doneModel.doneTitle );
            title.setTextColor(doneModel.getColor());
            description.setText(doneModel.doneDesc);
        }

        @Override
        public boolean onLongClick(View v) {
            listener.onItemLongClick(getAdapterPosition());
            return false;
        }
    }
}
