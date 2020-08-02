package com.lawlett.taskmanageruikit.calendarEvents.recycler;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.calendarEvents.data.model.CalendarTaskModel;
import com.lawlett.taskmanageruikit.utils.IDayOnClickListener;

import java.util.ArrayList;
import java.util.List;

public class CalendarEventAdapter extends RecyclerView.Adapter<CalendarEventAdapter.DayViewHolder> {

    List<CalendarTaskModel> list;
    Context context;
    IDayOnClickListener listener;

    public CalendarEventAdapter(ArrayList<CalendarTaskModel> list, IDayOnClickListener listener, Context context) {
        this.list = list;
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DayViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class DayViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView task, time, dataDay, endTime;
        View left_view;
        IDayOnClickListener listener;

        public DayViewHolder(@NonNull View itemView, IDayOnClickListener listener) {
            super(itemView);
            dataDay = itemView.findViewById(R.id.data_day);
            task = itemView.findViewById(R.id.task_title);
            time = itemView.findViewById(R.id.data_time);
            left_view = itemView.findViewById(R.id.left_view);
            endTime = itemView.findViewById(R.id.end_time);
            itemView.setOnClickListener((View.OnClickListener) this);
          itemView.setOnLongClickListener(this);
            this.listener = listener;
        }

        @SuppressLint("ResourceAsColor")
        public void onBind(CalendarTaskModel calendarTaskModel) {
            dataDay.setText(calendarTaskModel.getDataTime());
            task.setText(calendarTaskModel.getTitle());
            time.setText(calendarTaskModel.getStartTime());
            left_view.setBackgroundColor(calendarTaskModel.chooseColor);
            endTime.setText(calendarTaskModel.getEndTime());
        }
        @Override
        public boolean onLongClick(View v) {
            listener.onItemLongClick(getAdapterPosition());
            return false;
        }
        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition());
        }
    }
}