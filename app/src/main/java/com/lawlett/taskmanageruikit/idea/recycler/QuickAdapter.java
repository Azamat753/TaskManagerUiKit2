package com.lawlett.taskmanageruikit.idea.recycler;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.idea.data.model.QuickModel;
import com.lawlett.taskmanageruikit.utils.IQuickOnClickListener;
import com.lawlett.taskmanageruikit.utils.IdeaViewPreference;

import java.util.List;

public class QuickAdapter extends RecyclerView.Adapter<QuickAdapter.QuickViewHolder> {
    List<QuickModel> list;
    Context context;
    IQuickOnClickListener listener;
    public MutableLiveData<Boolean> isChange = new MutableLiveData<>();

    public QuickAdapter(List<QuickModel> list, IQuickOnClickListener listener, Context context) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }


    @NonNull
    @Override
    public QuickViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new QuickViewHolder(LayoutInflater.from(context).inflate(R.layout.quick_item, parent, false), listener);
    }

    @Override
    public void onBindViewHolder(@NonNull QuickViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class QuickViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, desc, data_created;
        TextView title2, desc2, data_created2;
        ImageView imageDesc;
        ImageView imageDesc2;
        IQuickOnClickListener listeneer;
        FrameLayout leftView;
        ConstraintLayout firstConst, secondConst;
        Boolean isGrid;



        public QuickViewHolder(@NonNull View itemView, IQuickOnClickListener listeneer) {
            super(itemView);
            title = itemView.findViewById(R.id.title_quick);
            desc = itemView.findViewById(R.id.desc_quick);
            data_created = itemView.findViewById(R.id.data_quick);
            imageDesc = itemView.findViewById(R.id.image_desc);

            title2 = itemView.findViewById(R.id.title_quick2);
            desc2 = itemView.findViewById(R.id.desc_quick2);
            data_created2 = itemView.findViewById(R.id.data_quick2);
            imageDesc2 = itemView.findViewById(R.id.image_desc2);

            firstConst = itemView.findViewById(R.id.constr);
            secondConst = itemView.findViewById(R.id.constr2);
//            leftView = itemView.findViewById(R.id.quItem_left_view);
            itemView.setOnClickListener(this);

            this.listeneer = listeneer;

            isChange.observeForever(aBoolean -> checkView());
            checkView();
        }

        public void onBind(QuickModel quickModel) {
            title.setText(quickModel.getTitle());
            desc.setText(quickModel.getDescription());
            data_created.setText(quickModel.getCreateData());

            title2.setText(quickModel.getTitle());
            desc2.setText(quickModel.getDescription());
            data_created2.setText(quickModel.getCreateData());

            if (quickModel.getColor() == 0) {
                quickModel.setColor(R.color.myWhite);
            } else {
                title.setTextColor(quickModel.getColor());
//                leftView.setBackgroundColor(quickModel.getColor());
            }
            Glide.with(context).load(quickModel.getImage()).into(imageDesc);
            Glide.with(context).load(quickModel.getImage()).into(imageDesc2);


        }

        @Override
        public void onClick(View v) {
            listeneer.onItemClick(getAdapterPosition());
        }
        public void checkView(){
            isGrid= IdeaViewPreference.getInstance(context).getView();

            if (isGrid){
                secondConst.setVisibility(View.VISIBLE);
                firstConst.setVisibility(View.INVISIBLE);
                Toast.makeText(context, "grid", Toast.LENGTH_SHORT).show();
            }else {
                secondConst.setVisibility(View.INVISIBLE);
                firstConst.setVisibility(View.VISIBLE);
                Toast.makeText(context, "linear", Toast.LENGTH_SHORT).show();
            }
            Log.e("ololo", "checkView: " + isGrid);

        }
    }
}
