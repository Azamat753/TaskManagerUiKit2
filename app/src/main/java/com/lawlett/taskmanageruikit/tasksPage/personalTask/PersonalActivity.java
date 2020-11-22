package com.lawlett.taskmanageruikit.tasksPage.personalTask;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.tasksPage.data.model.PersonalModel;
import com.lawlett.taskmanageruikit.tasksPage.personalTask.recyclerview.PersonalAdapter;
import com.lawlett.taskmanageruikit.utils.App;
import com.lawlett.taskmanageruikit.utils.PersonDoneSizePreference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PersonalActivity extends AppCompatActivity implements PersonalAdapter.ICheckedListener {
    EditText editText;
    PersonalAdapter adapter;
    PersonalModel personalModel;
    List<PersonalModel> list;
    String personal;
    ImageView personalBack;
    int pos, previousPersonalDone, currentData, updateData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setNavigationBarColor(getResources().getColor(R.color.statusBarC));


        list = new ArrayList<>();
        adapter = new PersonalAdapter(this);

        App.getDataBase().personalDao().getAllLive().observe(this, personalModels -> {
            if (personalModels != null) {
                list.clear();
                list.addAll(personalModels);
                Collections.reverse(list);
                adapter.updateList(list);
            }
        });


        RecyclerView recyclerView = findViewById(R.id.recycler_personal);
        recyclerView.setAdapter(adapter);

        editText = findViewById(R.id.editText_personal);


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
                App.getDataBase().personalDao().updateWord(list);
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(PersonalActivity.this);
                dialog.setTitle(R.string.are_you_sure).setMessage(R.string.to_delete)
                        .setNegativeButton(R.string.no, (dialog1, which) ->
                                dialog1.cancel())
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                pos = viewHolder.getAdapterPosition();
                                personalModel = list.get(pos);
                                if (!personalModel.isDone) {
                                    App.getDataBase().personalDao().delete(list.get(pos));
                                } else {
                                    decrementDone();
                                    App.getDataBase().personalDao().update(list.get(pos));
                                    App.getDataBase().personalDao().delete(list.get(pos));
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(PersonalActivity.this, R.string.delete, Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).show();
                adapter.notifyDataSetChanged();
                }
        }).attachToRecyclerView(recyclerView);


        personalBack = findViewById(R.id.personal_back);
        personalBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void addPersonalTask(View view) {
        recordDataRoom();
    }

    public void recordDataRoom() {
        if (editText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, R.string.empty, Toast.LENGTH_SHORT).show();
        } else {
            personal = editText.getText().toString().trim();
            personalModel = new PersonalModel(personal, false);
            App.getDataBase().personalDao().insert(personalModel);
            editText.setText("");
        }
    }

    @Override
    public void onItemCheckClick(int id) {
        personalModel = list.get(id);
        if (!personalModel.isDone) {
            personalModel.isDone = true;
            incrementDone();
        } else {
            personalModel.isDone = false;
            decrementDone();
        }
        App.getDataBase().personalDao().update(list.get(id));
    }

    private void incrementDone() {
        previousPersonalDone = PersonDoneSizePreference.getInstance(this).getPersonalSize();
        PersonDoneSizePreference.getInstance(this).savePersonalSize(previousPersonalDone + 1);
    }

    private void decrementDone() {
        currentData = PersonDoneSizePreference.getInstance(this).getPersonalSize();
        updateData = currentData - 1;
        PersonDoneSizePreference.getInstance(this).savePersonalSize(updateData);
    }

}