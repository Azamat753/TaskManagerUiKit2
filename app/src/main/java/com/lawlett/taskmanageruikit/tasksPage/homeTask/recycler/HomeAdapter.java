package com.lawlett.taskmanageruikit.tasksPage.homeTask.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.tasksPage.data.model.HomeModel;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {
    List<HomeModel> list;

    public HomeAdapter(ArrayList<HomeModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HomeViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        TextView homeTask;
        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            homeTask=itemView.findViewById(R.id.home_task);
        }
        public void onBind(HomeModel homeModel){
            homeTask.setText(homeModel.getHomeTask());
        }
    }
}
