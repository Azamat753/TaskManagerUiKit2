package com.lawlett.taskmanageruikit.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.lawlett.taskmanageruikit.achievement.models.AchievementModel;
import com.lawlett.taskmanageruikit.calendarEvents.data.model.CalendarTaskModel;
import com.lawlett.taskmanageruikit.idea.data.model.QuickModel;
import com.lawlett.taskmanageruikit.tasksPage.data.model.HomeModel;
import com.lawlett.taskmanageruikit.tasksPage.data.model.MeetModel;
import com.lawlett.taskmanageruikit.tasksPage.data.model.PersonalModel;
import com.lawlett.taskmanageruikit.tasksPage.data.model.PrivateModel;
import com.lawlett.taskmanageruikit.tasksPage.data.model.WorkModel;
import com.lawlett.taskmanageruikit.timing.model.TimingModel;

@TypeConverters(Converters.class)
@Database(entities = {QuickModel.class, PersonalModel.class, WorkModel.class, MeetModel.class,
        HomeModel.class, PrivateModel.class, CalendarTaskModel.class,
        TimingModel.class, AchievementModel.class}, version = 2, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    public abstract QuickDao taskDao();

    public abstract CalendarDao dataDao();

    public abstract PersonalDao personalDao();

    public abstract WorkDao workDao();

    public abstract MeetDao meetDao();

    public abstract HomeDao homeDao();

    public abstract PrivateDao privateDao();

    public abstract TimingDao timingDao();

    public abstract AchievementDao achievementDao();

}
