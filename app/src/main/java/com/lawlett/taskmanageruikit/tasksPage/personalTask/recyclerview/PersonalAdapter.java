package com.lawlett.taskmanageruikit.tasksPage.personalTask.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.tasksPage.data.model.PersonalModel;
import com.lawlett.taskmanageruikit.utils.App;
import com.lawlett.taskmanageruikit.utils.IPersonalOnClickListener;

import java.util.ArrayList;
import java.util.List;

public class PersonalAdapter extends RecyclerView.Adapter<PersonalAdapter.PersonalViewHolder> {
    List<PersonalModel> list;
    IPersonalOnClickListener listener;
    Context context;

    public PersonalAdapter(ArrayList<PersonalModel> list, Context context, IPersonalOnClickListener listener) {
        this.list = list;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public PersonalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PersonalViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.personal_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PersonalViewHolder holder, int position) {
        holder.onBind(list.get(position));
        holder.personalTask.setChecked(list.get(position).isDone=false);

        if (holder.personalTask.isChecked()){
            holder.personalTask.setChecked(true);
            PersonalModel personalModel = null;
            personalModel.setDone(true);
            App.getDataBase().personalDao().insert(personalModel);
        }

//        holder.personalTask.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                list.get(position).isDone=true;
//                PersonalModel personalModel = null;
//                personalModel.setDone(list.get(position).isDone=true);
//                App.getDataBase().personalDao().insert(personalModel);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class PersonalViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        CheckBox personalTask;

        public PersonalViewHolder(@NonNull View itemView) {
            super(itemView);
            personalTask = itemView.findViewById(R.id.personal_task);
            itemView.setOnLongClickListener(this);

        }

        public void onBind(PersonalModel personalModel) {
            personalTask.setText(personalModel.getPersonalTask());

        }


        @Override
        public boolean onLongClick(View v) {
            listener.onItemLongClick(getAdapterPosition());
            return false;
        }
    }
}
