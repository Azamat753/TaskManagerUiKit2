package com.lawlett.taskmanageruikit.tasksPage.addTask;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
    final Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.rotate);
    Button btnOk, btnCancel;
    EditText dialogEt;
    GridView dialogGridView;
    ImageAdapter imageAdapter;
    Integer dialogImg;
    CustomDialogListener customDialogListener;

    public CustomTaskDialog(@NonNull Context context) {
        super(context);
        TaskDialogPreference.init(context);
        imageAdapter = new ImageAdapter(context, new Integer[]{R.drawable.ic_01,R.drawable.ic_14,
                R.drawable.ic_02, R.drawable.ic_03,R.drawable.ic_04, R.drawable.ic_05,
                R.drawable.ic_06,R.drawable.ic_07, R.drawable.ic_08, R.drawable.ic_09,
                R.drawable.ic_10,R.drawable.ic_11,R.drawable.ic_12, R.drawable.ic_13,
                R.drawable.ic_15,R.drawable.ic_16,R.drawable.ic_17, R.drawable.ic_18,
                R.drawable.ic_19,R.drawable.ic_20,R.drawable.ic_21});
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
//        dialogGridView.setNumColumns(3);
        dialogGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialogImg = (Integer) adapterView.getItemAtPosition(i);
                view.startAnimation(animation);
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
                    customDialogListener.addInformation(title, dialogImg, View.VISIBLE, View.GONE);
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

    public void setDialogResult(CustomDialogListener listener){
        customDialogListener = listener;
    }

    public interface CustomDialogListener{
        void addInformation(String title, Integer image, int visible, int gone);
    }

}