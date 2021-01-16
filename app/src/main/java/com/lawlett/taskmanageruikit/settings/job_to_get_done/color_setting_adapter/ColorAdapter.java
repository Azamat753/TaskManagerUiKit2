package com.lawlett.taskmanageruikit.settings.job_to_get_done.color_setting_adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.settings.job_to_get_done.ToChangeThemeDialog;
import com.lawlett.taskmanageruikit.settings.job_to_get_done.interfaces.OnClickListenerTheme;
import com.lawlett.taskmanageruikit.settings.job_to_get_done.view_model.ThemeViewModel;

import java.util.ArrayList;
import java.util.List;

public class ColorAdapter extends RecyclerView.Adapter<ColorAdapter.ViewHolder> {
    private OnClickListenerTheme listener;
    private List<ThemeViewModel> list = new ArrayList<>();
    private Context context;


    public ColorAdapter(List<ThemeViewModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_options_theme_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(list.get(position));
        holder.btnRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deselectedItems();
                list.get(position).setChanged(true);
                listener.onClickTheme(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void deselectedItems() {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).isChanged()) list.get(i).setChanged(false);
        }
    }

    public void setClickListener(ToChangeThemeDialog toChangeThemeDialog) {
        this.listener = toChangeThemeDialog;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageColorSet;
        private RadioButton btnRadio;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageColorSet = itemView.findViewById(R.id.image_theme);
            btnRadio = itemView.findViewById(R.id.btnRadio_theme);
//            btnRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if (isChecked){
//
//                    }
//                }
//            });
        }

        public void onBind(ThemeViewModel themeViewModel) {
            imageColorSet.setImageResource(themeViewModel.getImageColorSet());
            btnRadio.setChecked(themeViewModel.isChanged());
        }
    }
}
