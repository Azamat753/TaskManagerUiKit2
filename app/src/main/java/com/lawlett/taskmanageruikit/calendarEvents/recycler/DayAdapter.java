package com.lawlett.taskmanageruikit.calendarEvents.recycler;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.calendarEvents.data.model.CalendarTaskModel;

import java.util.ArrayList;
import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.DayViewHolder> {

    List<CalendarTaskModel> list;

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


    public class DayViewHolder extends RecyclerView.ViewHolder {
        TextView task, time, dataDay,endTime;
        View left_view;

        public DayViewHolder(@NonNull View itemView) {
            super(itemView);
            dataDay = itemView.findViewById(R.id.data_day);
            task = itemView.findViewById(R.id.task_title);
            time = itemView.findViewById(R.id.data_time);
            left_view = itemView.findViewById(R.id.left_view);
            endTime=itemView.findViewById(R.id.end_time);
        }

        public void onBind(CalendarTaskModel calendarTaskModel) {
            dataDay.setText(calendarTaskModel.getDataTime());
            task.setText(calendarTaskModel.getTitle());
            time.setText(calendarTaskModel.getStartTime()+" -");
            left_view.setBackgroundColor(calendarTaskModel.chooseColor);
            endTime.setText(calendarTaskModel.getEndTime());
            Log.e("day", "onBind: " + calendarTaskModel.dataTime);

        }
    }
}
