package com.lawlett.taskmanageruikit.settings.job_to_get_done.view_model;

import android.app.Activity;
import android.content.Intent;

import com.lawlett.taskmanageruikit.R;

public class ThemeUtils {

    private static  int cTheme;

    public final  static int DARK_GREEN =0;
    public final static int DARK = 1;
    public final static int PURPULE = 2;
    public final static int ORANGE = 3;
    public final static int RED = 4;


    public static void changeThemeTo(Activity activity, int theme){
        cTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity,activity.getClass()));
        activity.overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out);

    }

    public static void onActivityCreateSetTheme(Activity activity){
        switch (cTheme){
            default:
            case DARK_GREEN:
                activity.setTheme(R.style.AppTheme);
                break;
            case DARK:
                activity.setTheme(R.style.AppDark);
            case PURPULE:
                activity.setTheme(R.style.PurpuleTheme);
                break;
            case ORANGE:
                activity.setTheme(R.style.AppOrangeTheme);
                break;
            case RED:
                activity.setTheme(R.style.AppRedTheme);
                break;

        }
    }




}
