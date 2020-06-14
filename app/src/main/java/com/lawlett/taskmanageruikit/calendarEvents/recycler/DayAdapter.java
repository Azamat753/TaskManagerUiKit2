package com.lawlett.taskmanageruikit.calendarEvents.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.calendarEvents.data.model.CalendarTaskModel;

import java.util.ArrayList;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayViewHolder> {

    ArrayList<CalendarTaskModel> list;

    public DayAdapter(ArrayList<CalendarTaskModel> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DayViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addText(CalendarTaskModel calendarTaskModel) {
        list.add(calendarTaskModel);
        notifyDataSetChanged();
    }

    public class DayViewHolder extends RecyclerView.ViewHolder {
        TextView task, data, dataDay;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            dataDay = itemView.findViewById(R.id.data_day);
            task = itemView.findViewById(R.id.task_tv);
            data = itemView.findViewById(R.id.data_tv);
        }

        public void onBind(CalendarTaskModel calendarTaskModel) {
            dataDay.setText(calendarTaskModel.dataTime);
            task.setText(calendarTaskModel.title);
            data.setText(calendarTaskModel.startTime);

        }
    }
}
