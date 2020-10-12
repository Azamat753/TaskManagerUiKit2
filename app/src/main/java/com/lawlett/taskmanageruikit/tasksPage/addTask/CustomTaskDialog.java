package com.lawlett.taskmanageruikit.tasksPage.addTask;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.tasksPage.addTask.adapter.ImageAdapter;
import com.lawlett.taskmanageruikit.utils.TaskDialogPreference;

public class CustomTaskDialog extends Dialog implements View.OnClickListener {
    Button btnOk, btnCancel;
    EditText dialogEt;
    GridView dialogGridView;
    ImageAdapter imageAdapter;
    Integer dialogImg;

    public CustomTaskDialog(@NonNull Context context) {
        super(context);
        TaskDialogPreference.init(context);
        imageAdapter = new ImageAdapter(context, new Integer[]{R.drawable.ic_1,R.drawable.ic_2,
                R.drawable.ic_3,R.drawable.ic_4,R.drawable.ic_5,R.drawable.ic_6,R.drawable.ic_7,R.drawable.ic_8,
                R.drawable.ic_9,R.drawable.ic_10,R.drawable.ic_11,R.drawable.ic_12,});
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_dialog);
        initView();
        gridViewInit();
    }

    private void gridViewInit() {
        dialogGridView.setAdapter(imageAdapter);
        dialogGridView.setNumColumns(3);
        dialogGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialogImg = (Integer) adapterView.getItemAtPosition(i);
            }
        });
    }

    private void initView() {
        btnOk = findViewById(R.id.dialog_btn_ok);
        btnCancel = findViewById(R.id.dialog_btn_cancel);
        dialogEt = findViewById(R.id.dialog_et);
        dialogGridView = findViewById(R.id.dialog_gridView);

        btnCancel.setOnClickListener(this);
        btnOk.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_btn_ok:
                String title = dialogEt.getText().toString();
                if(title.isEmpty()){
                    Toast.makeText(getContext(),R.string.add_title, Toast.LENGTH_LONG).show();
                }
                else if(dialogImg == null){
                    Toast.makeText(getContext(),R.string.add_icon, Toast.LENGTH_LONG).show();
                }
                else {
                    TaskDialogPreference.saveImage(dialogImg);
                    TaskDialogPreference.saveTitle(title);

                hide();
                }
                break;
            case R.id.dialog_btn_cancel:
                dismiss();
                break;
        }
    }

}
