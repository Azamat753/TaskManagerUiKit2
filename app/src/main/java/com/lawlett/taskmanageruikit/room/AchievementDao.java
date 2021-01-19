package com.lawlett.taskmanageruikit.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.lawlett.taskmanageruikit.achievement.models.AchievementModel;

import java.util.List;

import static com.lawlett.taskmanageruikit.achievement.models.AchievementModel.*;

@Dao
public interface AchievementDao {

    @Query("SELECT * FROM achievement")
    LiveData<List<AchievementModel>> getAll();

    @Insert()
    void insert(AchievementModel achievementModel);

    @Query("DELETE FROM achievement")
    void deleteAll();

    @Query("SELECT * FROM achievement WHERE category = :category")
    List<AchievementModel> getAllByCategory(Category category);
}
