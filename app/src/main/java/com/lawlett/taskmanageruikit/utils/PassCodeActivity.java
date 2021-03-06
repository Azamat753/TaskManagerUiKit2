package com.lawlett.taskmanageruikit.utils;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.hanks.passcodeview.PasscodeView;
import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.tasksPage.privateTask.PrivateActivity;

public class PassCodeActivity extends AppCompatActivity {
    PasscodeView passcodeView;
    String password;
    EditText editPassword;
    Button button;

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_code);

        editPassword = findViewById(R.id.edit_password);
        button = findViewById(R.id.ok_btn);
        passcodeView = findViewById(R.id.passcode_view);
        password = PasswordPreference.getInstance(this).returnPassword();

        boolean isShown = PasswordDonePreference.getInstance(getApplication()).isShown();
        if (isShown) {
            passcodeView.setVisibility(View.VISIBLE);
            editPassword.setVisibility(View.GONE);
            button.setVisibility(View.GONE);
        } else {
            passcodeView.setVisibility(View.GONE);
            editPassword.setVisibility(View.VISIBLE);
            button.setVisibility(View.VISIBLE);
        }
        passcodeView.setPasscodeLength(4)
                .setFirstInputTip(getString(R.string.of_symbols))
                .setSecondInputTip(getString(R.string.re_enter))
                .setWrongLengthTip(getString(R.string.of_symbols))
                .setWrongInputTip(getString(R.string.do_not_match))
                .setCorrectInputTip(getString(R.string.correct_password))
                .setLocalPasscode(password)
                .setListener(new PasscodeView.PasscodeViewListener() {
                    @Override
                    public void onFail() {
                        Toast.makeText(PassCodeActivity.this, R.string.Wrong, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(String number) {
                        startActivity(new Intent(PassCodeActivity.this, PrivateActivity.class));
                        PasswordPassDonePreference.getInstance(PassCodeActivity.this).savePass();
                        finish();
                    }
                });
    }

    public void saveBtn(View view) {
        PasswordPreference.getInstance(this).savePassword(editPassword.getText().toString().trim());
        passcodeView.setVisibility(View.VISIBLE);
        editPassword.setVisibility(View.GONE);
        button.setVisibility(View.GONE);
        PasswordDonePreference.getInstance(this).saveShown();
        finish();
    }
}