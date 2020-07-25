package com.lawlett.taskmanageruikit.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.lawlett.taskmanageruikit.HomeDoneModel;
import com.lawlett.taskmanageruikit.MeetDoneModel;
import com.lawlett.taskmanageruikit.PersonalDoneModel;
import com.lawlett.taskmanageruikit.PrivateDoneModel;
import com.lawlett.taskmanageruikit.calendarEvents.data.model.CalendarTaskModel;
import com.lawlett.taskmanageruikit.WorkDoneModel;
import com.lawlett.taskmanageruikit.quick.data.model.QuickModel;
import com.lawlett.taskmanageruikit.tasksPage.data.model.DoneModel;
import com.lawlett.taskmanageruikit.tasksPage.data.model.HomeModel;
import com.lawlett.taskmanageruikit.tasksPage.data.model.MeetModel;
import com.lawlett.taskmanageruikit.tasksPage.data.model.PersonalModel;
import com.lawlett.taskmanageruikit.tasksPage.data.model.PrivateModel;
import com.lawlett.taskmanageruikit.tasksPage.data.model.WorkModel;

@Database(entities = {QuickModel.class, PersonalModel.class, WorkModel.class, MeetModel.class,
        HomeModel.class, PrivateModel.class, CalendarTaskModel.class, DoneModel.class,
        PersonalDoneModel.class, WorkDoneModel.class, MeetDoneModel.class,
        HomeDoneModel.class, PrivateDoneModel.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    public abstract QuickDao taskDao();

    public abstract DataDao dataDao();

    public abstract PersonalDao personalDao();

    public abstract WorkDao workDao();

    public abstract MeetDao meetDao();

    public abstract HomeDao homeDao();

    public abstract PrivateDao privateDao();

    public abstract DoneTaskDao doneTaskDao();

    public abstract PersonalDoneTaskDao personalDoneTaskDao();

    public abstract WorkDoneTaskDao workDoneTaskDao();

    public abstract MeetDoneTaskDao meetDoneTaskDao();

    public abstract HomeDoneTaskDao homeDoneTaskDao();

    public abstract PrivateDoneTaskDao privateDoneTaskDao();
}
