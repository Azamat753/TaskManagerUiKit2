package com.lawlett.taskmanageruikit.achievement.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.achievement.models.AchievementModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AchievementAdapter extends RecyclerView.Adapter<AchievementAdapter.AchievementViewHolder> {

    private List<AchievementModel> data = new ArrayList<>();

    public List<AchievementModel> getData() {
        return data;
    }

    public void setData(List<AchievementModel> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public AchievementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AchievementViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_achievement, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AchievementViewHolder holder, int position) {
        holder.onBind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addItem(AchievementModel item){
        this.data.add(item);
        notifyDataSetChanged();
    }

    public void clearAll() {
        data.clear();
        notifyDataSetChanged();
    }

    static class AchievementViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewAchievementAmount, textViewAchievementDate;

        public AchievementViewHolder(@NonNull View itemView) {
            super(itemView);
            init(itemView);
        }

        private void init(View view) {
            textViewAchievementAmount = view.findViewById(R.id.text_view_amount_achievement);
            textViewAchievementDate = view.findViewById(R.id.text_view_date);
        }

        public void onBind(AchievementModel model){

            String pattern = "MM/dd/yyyy HH:mm";
            @SuppressLint("SimpleDateFormat") String todayAsString = new SimpleDateFormat(pattern).format(model.getDate());

            textViewAchievementDate.setText(todayAsString);
            textViewAchievementAmount.setText(String.valueOf(model.getAchievementAmount()));
        }

    }

}
