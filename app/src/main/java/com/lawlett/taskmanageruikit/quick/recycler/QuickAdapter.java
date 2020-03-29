package com.lawlett.taskmanageruikit.quick.recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lawlett.taskmanageruikit.R;

import java.util.ArrayList;

public class QuickAdapter extends RecyclerView.Adapter<QuickAdapter.QuickViewHolder> {
    ArrayList<String> list;
    Context context;
    public QuickAdapter(Context context){
        this.context= context;
        list= new ArrayList<>();
    }
    @NonNull
    @Override
    public QuickViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new QuickViewHolder(LayoutInflater.from(context).inflate(R.layout.quick_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull QuickViewHolder holder, int position) {
holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class QuickViewHolder extends RecyclerView.ViewHolder {
        TextView title,desc;
        ImageView imageDesc;
        public QuickViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_quick);
            desc=itemView.findViewById(R.id.desc_quick);
            imageDesc=itemView.findViewById(R.id.image_desc);
        }
        public void onBind(String s){
            title.setText(s);
            desc.setText(s);
            Glide.with(context).load(s).into(imageDesc);

        }
    }
}
