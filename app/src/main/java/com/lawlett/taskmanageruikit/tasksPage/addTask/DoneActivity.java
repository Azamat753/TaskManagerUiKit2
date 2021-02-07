package com.lawlett.taskmanageruikit.tasksPage.addTask;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.tasksPage.addTask.adapter.DoneAdapter;
import com.lawlett.taskmanageruikit.tasksPage.data.model.DoneModel;
import com.lawlett.taskmanageruikit.utils.ActionForDialog;
import com.lawlett.taskmanageruikit.utils.AddDoneSizePreference;
import com.lawlett.taskmanageruikit.utils.App;
import com.lawlett.taskmanageruikit.utils.DialogHelper;
import com.lawlett.taskmanageruikit.utils.TaskDialogPreference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class DoneActivity extends AppCompatActivity implements DoneAdapter.IMCheckedListener, ActionForDialog {
    DoneAdapter adapter;
    List<DoneModel> list;
    DoneModel doneModel;
    EditText editText;
    int pos, previousData, currentData, updateData;
    ImageView doneBack, addTask,imageMic;
    private static final int REQUEST_CODE_SPEECH_INPUT = 22;
    boolean knopka = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);
        TaskDialogPreference.init(this);
        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setNavigationBarColor(getResources().getColor(R.color.statusBarC));

        changeView();
        list = new ArrayList<>();
        adapter = new DoneAdapter(this);
        App.getDataBase().doneDao().getAllLive().observe(this, doneModels -> {
            if (doneModels != null) {
                list.clear();
                list.addAll(doneModels);
                Collections.reverse(list);
                adapter.updateList(list);
            }
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_done);
        recyclerView.setAdapter(adapter);

        editText = findViewById(R.id.editText_done);
        addTask = findViewById(R.id.add_task_done);
        imageMic=findViewById(R.id.mic_task_done);
        editListener();

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
                App.getDataBase().doneDao().updateWord(list);
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(DoneActivity.this);
                dialog.setTitle(R.string.are_you_sure).setMessage(R.string.to_delete)
                        .setNegativeButton(R.string.no, (dialog1, which) -> {
                            adapter.notifyDataSetChanged();
                            dialog1.cancel();
                        })
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                pos = viewHolder.getAdapterPosition();
                                doneModel = list.get(pos);
                                if (!doneModel.isDone) {
                                    App.getDataBase().doneDao().delete(list.get(pos));
                                } else {
                                    decrementDone();
                                    App.getDataBase().doneDao().update(list.get(pos));
                                    App.getDataBase().doneDao().delete(list.get(pos));
                                    adapter.notifyDataSetChanged();
                                    Toast.makeText(DoneActivity.this, "Удалено", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).show();
            }
        }).attachToRecyclerView(recyclerView);
        doneBack = findViewById(R.id.personal_back);
        doneBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        findViewById(R.id.settings_for_task).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogHelper dialogHelper = new DialogHelper();
                dialogHelper.myDialog(DoneActivity.this, DoneActivity.this);
            }
        });
    }

    public void addDoneTask(View view) {
        recordDataRoom();
    }

    public void recordDataRoom() {
        if (editText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Пусто", Toast.LENGTH_SHORT).show();
        } else {
            doneModel = new DoneModel(editText.getText().toString().trim(), false);
            App.getDataBase().doneDao().insert(doneModel);
            editText.setText("");
        }
    }

    public void changeView() {
        TextView toolbar = findViewById(R.id.toolbar_title);
        toolbar.setText(TaskDialogPreference.getTitle());
    }

    @Override
    public void onItemCheckClick(int id) {
        doneModel = list.get(id);
        if (!doneModel.isDone) {
            doneModel.isDone = true;
            incrementDone();
        } else {
            doneModel.isDone = false;
            decrementDone();
        }
        App.getDataBase().doneDao().update(list.get(id));
    }

    private void incrementDone() {
        previousData = AddDoneSizePreference.getInstance(this).getDataSize();
        AddDoneSizePreference.getInstance(this).saveDataSize(previousData + 1);

    }

    private void decrementDone() {
        currentData = AddDoneSizePreference.getInstance(this).getDataSize();
        updateData = currentData - 1;
        AddDoneSizePreference.getInstance(this).saveDataSize(updateData);
    }

    @Override
    public void pressOk() {
        App.getDataBase().doneDao().deleteAll(list);
        AddDoneSizePreference.getInstance(DoneActivity.this).clearSettings();
    }

    private void editListener() {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence != null && !knopka && !editText.getText().toString().trim().isEmpty()) {
//                    imageAdd.startAnimation(animationAlpha);
                    imageMic.setVisibility(View.INVISIBLE);
                    addTask.setVisibility(View.VISIBLE);
                    knopka = true;
                }
                if (editText.getText().toString().isEmpty() && knopka) {
//                    imageMic.startAnimation(animationAlpha);
                    addTask.setVisibility(View.INVISIBLE);
                    imageMic.setVisibility(View.VISIBLE);
                    knopka = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

    }

    public void micDoneTask(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hi speak something");
        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT);
        } catch (Exception e) {
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SPEECH_INPUT && resultCode == RESULT_OK && data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            assert result != null;
            editText.setText(editText.getText() + " " + result.get(0));
        }
    }
}