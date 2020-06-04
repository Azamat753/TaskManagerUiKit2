package com.lawlett.taskmanageruikit.tasksPage.personalTask.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.quick.data.model.QuickModel;

import java.util.List;

public class PersonalAdapter extends RecyclerView.Adapter<PersonalAdapter.PersonalViewHolder> {
    List<QuickModel> list;

    public PersonalAdapter(List<QuickModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public PersonalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PersonalViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.personal_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PersonalViewHolder holder, int position) {
holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PersonalViewHolder extends RecyclerView.ViewHolder{
        TextView personalTask;
        public PersonalViewHolder(@NonNull View itemView) {
            super(itemView);
            personalTask =itemView.findViewById(R.id.personal_task);
        }
        public void onBind(QuickModel quickModel){
            personalTask.setText(quickModel.getPersonalTask());
        }
    }
}
