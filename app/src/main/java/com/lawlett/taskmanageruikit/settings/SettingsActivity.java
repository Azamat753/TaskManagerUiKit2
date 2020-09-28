package com.lawlett.taskmanageruikit.settings;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;
import com.lawlett.taskmanageruikit.R;
import com.lawlett.taskmanageruikit.main.MainActivity;
import com.lawlett.taskmanageruikit.utils.LanguagePreference;
import com.lawlett.taskmanageruikit.utils.PasswordDonePreference;
import com.lawlett.taskmanageruikit.utils.PasswordPreference;
import com.lawlett.taskmanageruikit.utils.ThemePreference;
import com.lawlett.taskmanageruikit.utils.TimingSizePreference;

import java.util.Locale;

public class SettingsActivity extends AppCompatActivity {
    ImageView back;
    LinearLayout language_tv, clear_password_layout, clearMinutes_layout, theme_layout, share_layout;
    ReviewManager manager;
    ReviewInfo reviewInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_settings);
        reviewInApp();

        clear_password_layout = findViewById(R.id.first_layout);
        clearMinutes_layout = findViewById(R.id.second_layout);
        theme_layout = findViewById(R.id.third_layout);
        back = findViewById(R.id.back_view);
        language_tv = findViewById(R.id.four_layout);
        share_layout = findViewById(R.id.five_layout);

        if (Build.VERSION.SDK_INT >= 21)
            getWindow().setNavigationBarColor(getResources().getColor(R.color.statusBarC));

        theme_layout.setOnClickListener(v -> showChangeThemeDialog());

        back.setOnClickListener(v -> onBackPressed());

        share_layout.setOnClickListener(v -> {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Planner");
                String shareMessage = "\nPlanner\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=com.lawlett.taskmanageruikit";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "Выберите приложение"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        clear_password_layout.setOnClickListener(v -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(SettingsActivity.this);
            dialog.setTitle(R.string.are_you_sure).setMessage(R.string.clear_password)
                    .setNegativeButton(R.string.no, (dialog1, which) ->
                            dialog1.cancel())
                    .setPositiveButton(R.string.yes, (dialog13, which) -> {
                        PasswordPreference.getInstance(SettingsActivity.this).clearPassword();
                        PasswordDonePreference.getInstance(SettingsActivity.this).clearSettings();
                        Toast.makeText(SettingsActivity.this, R.string.data_of_password_delete, Toast.LENGTH_SHORT).show();
                    }).show();
        });
        clearMinutes_layout.setOnClickListener(v -> {
            AlertDialog.Builder dialog = new AlertDialog.Builder(SettingsActivity.this);
            dialog.setTitle(R.string.are_you_sure).setMessage(R.string.clear_minute)
                    .setNegativeButton(R.string.no, (dialog1, which) ->
                            dialog1.cancel())
                    .setPositiveButton(R.string.yes, (dialog12, which) -> {
                        TimingSizePreference.getInstance(SettingsActivity.this).clearSettings();
                        Toast.makeText(SettingsActivity.this, R.string.data_about_minutes_clear, Toast.LENGTH_SHORT).show();
                    }).show();
        });

        language_tv.setOnClickListener(v -> showChangeLanguageDialog());
    }

    public void reviewInApp() {
        manager = ReviewManagerFactory.create(SettingsActivity.this);
        Task<ReviewInfo> request = manager.requestReviewFlow();

        request.addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                reviewInfo = task.getResult();
                Task<Void> flow = manager.launchReviewFlow(SettingsActivity.this, reviewInfo);
                flow.addOnSuccessListener(result -> {

                });
            } else {
                Toast.makeText(SettingsActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }

        });
    }

    public void showChangeThemeDialog() {
        final String[] listItems = {getString(R.string.light_theme), getString(R.string.dark_theme)};
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
        builder.setTitle(R.string.choose_theme);
        builder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (i == 0) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    ThemePreference.getInstance(SettingsActivity.this).saveThemeTrue();

                } else if (i == 1) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    ThemePreference.getInstance(SettingsActivity.this).saveThemeFalse();

                }

                dialog.dismiss();
            }
        });
        AlertDialog mDialog = builder.create();
        mDialog.show();
    }

    private void showChangeLanguageDialog() {
        final String[] listItems = {"English", "Русский", "Кыргызча"};
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(SettingsActivity.this);
        mBuilder.setTitle(R.string.choose_language);
        mBuilder.setSingleChoiceItems(listItems, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (i == 0) {
                    setLocale("en");
                    recreate();
                } else if (i == 1) {
                    setLocale("ru");
                    recreate();
                } else if (i == 2) {
                    setLocale("ky");
                    recreate();
                }
                dialog.dismiss();
            }
        });
        AlertDialog mDialog = mBuilder.create();
        mDialog.show();
    }

    private void setLocale(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        LanguagePreference.getInstance(SettingsActivity.this).saveLanguage(lang);
    }

    private void loadLocale() {
        String language = LanguagePreference.getInstance(this).getLanguage();
        setLocale(language);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
