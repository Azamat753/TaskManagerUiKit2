package com.lawlett.taskmanageruikit.settings.job_to_get_done;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.settings.job_to_get_done.color_setting_adapter.ColorAdapter;
import com.lawlett.taskmanageruikit.settings.job_to_get_done.view_model.ColorViewModel;

import java.util.ArrayList;

public class ToChangeTheme extends DialogFragment {
    private RecyclerView recyclerView;
    private ArrayList<ColorViewModel> list;
    private ColorAdapter colorAdapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
                .setView(R.layout.options_to_theme_view)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (dialog != null)
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }
    




}
