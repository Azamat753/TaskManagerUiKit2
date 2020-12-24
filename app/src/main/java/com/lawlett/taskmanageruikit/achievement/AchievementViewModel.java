package com.lawlett.taskmanageruikit.achievement;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.lawlett.taskmanageruikit.achievement.models.AchievementModel;
import com.lawlett.taskmanageruikit.utils.App;
import com.lawlett.taskmanageruikit.utils.HomeDoneSizePreference;
import com.lawlett.taskmanageruikit.utils.MeetDoneSizePreference;
import com.lawlett.taskmanageruikit.utils.PersonDoneSizePreference;
import com.lawlett.taskmanageruikit.utils.PrivateDoneSizePreference;
import com.lawlett.taskmanageruikit.utils.WorkDoneSizePreference;

import java.util.List;

public class AchievementViewModel extends ViewModel {

    LiveData<List<AchievementModel>> data;

    public AchievementViewModel() {
        data = App.getDataBase().achievementDao().getAll();
    }

    public void insertedAchievement(){

    }
}
