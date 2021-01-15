package com.lawlett.taskmanageruikit.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lawlett.taskmanageruikit.R;

import pl.droidsonroids.gif.GifImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HelpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HelpFragment extends Fragment {
    GifImageView changeGif, moveGif, deleteGif;
    TextView helpTextView;

    public HelpFragment() {
        // Required empty public constructor
    }
    public static HelpFragment newInstance(String param1, String param2) {
        HelpFragment fragment = new HelpFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_help, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        int pos = getArguments().getInt("position");
        initViews(view);
        switch (pos){
            case 0:
                helpTextView.setText(R.string.helper_dialog_text);
                changeGif.setVisibility(View.VISIBLE);
                moveGif.setVisibility(View.INVISIBLE);
                deleteGif.setVisibility(View.INVISIBLE);
                break;
            case 1:
                helpTextView.setText(R.string.move_tasks);
                changeGif.setVisibility(View.INVISIBLE);
                moveGif.setVisibility(View.VISIBLE);
                deleteGif.setVisibility(View.INVISIBLE);
                break;
            case 2:
                helpTextView.setText(R.string.delete_task);
                changeGif.setVisibility(View.INVISIBLE);
                moveGif.setVisibility(View.INVISIBLE);
                deleteGif.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void initViews(View view) {
        changeGif = view.findViewById(R.id.fragment_help_gif_change);
        moveGif = view.findViewById(R.id.fragment_help_gif_move);
        deleteGif = view.findViewById(R.id.fragment_help_gif_delete);
        helpTextView = view.findViewById(R.id.fragment_help_tv);
    }
}