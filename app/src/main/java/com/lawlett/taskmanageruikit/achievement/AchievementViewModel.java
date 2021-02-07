package com.lawlett.taskmanageruikit.achievement;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.lawlett.taskmanageruikit.achievement.models.AchievementModel;
import com.lawlett.taskmanageruikit.achievement.models.LevelModel;
import com.lawlett.taskmanageruikit.utils.App;
import com.lawlett.taskmanageruikit.utils.HomeDoneSizePreference;
import com.lawlett.taskmanageruikit.utils.MeetDoneSizePreference;
import com.lawlett.taskmanageruikit.utils.PersonDoneSizePreference;
import com.lawlett.taskmanageruikit.utils.PrivateDoneSizePreference;
import com.lawlett.taskmanageruikit.utils.WorkDoneSizePreference;

import java.util.List;

public class AchievementViewModel extends ViewModel {

    LiveData<List<LevelModel>> data;

    public AchievementViewModel() {
        data = App.getDataBase().levelDao().getAll();
    }

    public void insertedAchievement(){

    }
}
