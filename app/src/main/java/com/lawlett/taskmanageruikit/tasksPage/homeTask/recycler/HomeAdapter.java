package com.lawlett.taskmanageruikit.tasksPage.homeTask.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.tasksPage.data.model.HomeModel;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    List<HomeModel> list = new ArrayList<>();
    IHCheckedListener listener;

    public HomeAdapter(IHCheckedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void updateList(List<HomeModel> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        CheckBox homeTask;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            homeTask = itemView.findViewById(R.id.home_task);
        }

        public void onBind(HomeModel homeModel) {
            homeTask.setText(homeModel.getHomeTask());
            homeTask.setChecked(homeModel.isDone);
            homeTask.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemCheckClick(getAdapterPosition());
                }
            });
        }
    }

    public interface IHCheckedListener {
        void onItemCheckClick(int id);
    }
}

