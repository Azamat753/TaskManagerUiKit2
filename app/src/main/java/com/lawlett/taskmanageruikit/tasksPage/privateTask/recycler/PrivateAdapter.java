package com.lawlett.taskmanageruikit.tasksPage.privateTask.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.tasksPage.data.model.PrivateModel;
import com.lawlett.taskmanageruikit.utils.IPrivateOnClickListener;

import java.util.ArrayList;
import java.util.List;

public class PrivateAdapter extends RecyclerView.Adapter<PrivateAdapter.PrivateViewHolder> {
    List<PrivateModel> list;
    Context context;
    IPrivateOnClickListener listener;

    public PrivateAdapter(ArrayList<PrivateModel> list,Context context,IPrivateOnClickListener listener) {
        this.list = list;
        this.context=context;
        this.listener=listener;
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

    public class PrivateViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView privateTask;

        public PrivateViewHolder(@NonNull View itemView) {
            super(itemView);
            privateTask = itemView.findViewById(R.id.private_task);
            itemView.setOnLongClickListener(this);
        }

        public void onBind(PrivateModel privateModel) {
            privateTask.setText(privateModel.getPrivateTask());
        }

        @Override
        public boolean onLongClick(View v) {
            listener.onItemLongClick(getAdapterPosition());
            return false;
        }
    }
}
