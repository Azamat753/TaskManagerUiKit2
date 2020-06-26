package com.lawlett.taskmanageruikit.tasksPage.personalTask.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.tasksPage.data.model.PersonalModel;
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class PersonalViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        TextView personalTask;

        public PersonalViewHolder(@NonNull View itemView) {
            super(itemView);
            personalTask = itemView.findViewById(R.id.personal_task);
//            mLeftAnim = AnimationUtils.loadAnimation(context, R.anim.slide_in_left);
//            mRightAnim = AnimationUtils.loadAnimation(context, R.anim.slide_in_right);
//            mAnimator = itemView.findViewById(R.id.animator);
//
//            mLeftAnim.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//
//                }
//
//                @Override
//                public void onAnimationEnd(Animation animation) {
//                    itemView.setOnTouchListener(new OnSwipeTouchListener(context) {
//
//                        public void onSwipeRight() {
//                            Toast.makeText(context, "right", Toast.LENGTH_SHORT).show();
//                        }
//                });
//                }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//
//                }
//            });
//
//            itemView.setOnLongClickListener(this);
//            itemView.setOnTouchListener(new OnSwipeTouchListener(context) {
//                public void onSwipeTop() {
//                    Toast.makeText(context, "top", Toast.LENGTH_SHORT).show();
//                }
////                public void onSwipeRight() {
////                    Toast.makeText(context, "right", Toast.LENGTH_SHORT).show();
////                }
////
//
//                public void onSwipeLeft() {
//                    Toast.makeText(context, "left", Toast.LENGTH_SHORT).show();
//                }
//
//                public void onSwipeBottom() {
//                    Toast.makeText(context, "bottom", Toast.LENGTH_SHORT).show();
//                }
//            });

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
