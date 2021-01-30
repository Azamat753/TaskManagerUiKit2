package com.lawlett.taskmanageruikit.tasksPage.workTask;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.tasksPage.data.model.WorkModel;
import com.lawlett.taskmanageruikit.tasksPage.workTask.recycler.WorkAdapter;
import com.lawlett.taskmanageruikit.utils.App;
import com.lawlett.taskmanageruikit.utils.DoneTasksPreferences;
import com.lawlett.taskmanageruikit.utils.WorkDoneSizePreference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorkActivity extends AppCompatActivity implements WorkAdapter.IWCheckedListener {
    WorkAdapter adapter;
    EditText editText;
    WorkModel workModel;
    List<WorkModel> list;
    int pos, previousData, currentData, updateData;
    ImageView workBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);
        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setNavigationBarColor(getResources().getColor(R.color.statusBarC));

        changeView();

        list = new ArrayList<>();
        adapter = new WorkAdapter(this);

        App.getDataBase().workDao().getAllLive().observe(this, workModels -> {
            if (workModels != null) {
                list.clear();
                list.addAll(workModels);
                adapter.updateList(list);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_work);
        recyclerView.setAdapter(adapter);

        editText = findViewById(R.id.editText_work);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                return makeMovementFlags(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();
                int toPosition = target.getAdapterPosition();

                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(list, i, i + 1);

                        int order1 = (int) list.get(i).getId();
                        int order2 = (int) list.get(i + 1).getId();
                        list.get(i).setId(order2);
                        list.get(i + 1).setId(order1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(list, i, i - 1);

                        int order1 = (int) list.get(i).getId();
                        int order2 = (int) list.get(i - 1).getId();
                        list.get(i).setId(order2);
                        list.get(i - 1).setId(order1);
                    }
                }
                adapter.notifyItemMoved(fromPosition, toPosition);
                return true;

            }

            @Override
            public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                App.getDataBase().workDao().updateWord(list);
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(WorkActivity.this);
                dialog.setTitle(R.string.are_you_sure).setMessage(R.string.to_delete)
                        .setNegativeButton(R.string.no, (dialog1, which) ->
                                dialog1.cancel())
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                pos = viewHolder.getAdapterPosition();
                                workModel = list.get(pos);
                                if (!workModel.isDone) {
                                    App.getDataBase().workDao().delete(list.get(pos));
                                } else {
                                    decrementDone();
                                    App.getDataBase().workDao().update(list.get(pos));
                                    App.getDataBase().workDao().delete(list.get(pos));
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(WorkActivity.this, "Удалено", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).show();
                adapter.notifyDataSetChanged();
            }

            @SuppressLint("ResourceAsColor")
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                final int DIRECTION_RIGHT = 1;
                final int DIRECTION_LEFT = 0;

                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE && isCurrentlyActive){
                    int direction = dX > 0? DIRECTION_RIGHT : DIRECTION_LEFT;
                    int absoluteDisplacement = Math.abs((int)dX);

                    switch (direction){

                        case DIRECTION_RIGHT:

                            View itemView = viewHolder.itemView;
                            final ColorDrawable background = new ColorDrawable(Color.RED);
                            background.setBounds(0, itemView.getTop(), (int) (itemView.getLeft() + dX), itemView.getBottom());
                            background.draw(c);

                            break;

                        case DIRECTION_LEFT:

                            View itemView2 = viewHolder.itemView;
                            final ColorDrawable background2 = new ColorDrawable(Color.RED);
                            background2.setBounds(itemView2.getRight(), itemView2.getBottom(), (int) (itemView2.getRight() + dX), itemView2.getTop());
                            background2.draw(c);

                            break;
                    }

                }
            }
        }).attachToRecyclerView(recyclerView);

        workBack = findViewById(R.id.personal_back);
        workBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void addWorkTask(View view) {
        recordDataRoom();
    }

    public void recordDataRoom() {
        if (editText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Пусто", Toast.LENGTH_SHORT).show();
        } else {
            workModel = new WorkModel(editText.getText().toString().trim(), false);
            App.getDataBase().workDao().insert(workModel);
            editText.setText("");
        }
    }

    public void changeView() {
        TextView toolbar = findViewById(R.id.toolbar_title);
        toolbar.setText(R.string.work);

    }

    @Override
    public void onItemCheckClick(int id) {
        workModel = list.get(id);
        if (!workModel.isDone) {
            workModel.isDone = true;
            incrementDone();
        } else {
            workModel.isDone = false;
            decrementDone();
        }
        App.getDataBase().workDao().update(list.get(id));
    }

    private void setLevel(int size) {
        if (size < 26) {
            if (size % 5 == 0) {
                String level = "Молодец " + size / 5;
                showDialogLevel(level);
            }
        }
    }

    private void showDialogLevel(String l) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Важное сообщение!")
                .setMessage("Вы получили звание: " + l)
                .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Закрываем окно
                        dialog.cancel();
                    }
                });
        builder.create();
        builder.show();
    }

    private void incrementAllDone(){
        DoneTasksPreferences.getInstance(this).saveDataSize(previousData + 1);
        setLevel(DoneTasksPreferences.getInstance(this).getDataSize());
    }

    private void decrementAllDone(){
        int currentSize = DoneTasksPreferences.getInstance(this).getDataSize();
        int updateSize = currentSize - 1;
        DoneTasksPreferences.getInstance(this).saveDataSize(updateSize);
    }

    private void incrementDone() {
        previousData = WorkDoneSizePreference.getInstance(this).getDataSize();
        WorkDoneSizePreference.getInstance(this).saveDataSize(previousData + 1);
        incrementAllDone();
    }

    private void decrementDone() {
        currentData = WorkDoneSizePreference.getInstance(this).getDataSize();
        updateData = currentData - 1;
        WorkDoneSizePreference.getInstance(this).saveDataSize(updateData);
        decrementAllDone();
    }
}

