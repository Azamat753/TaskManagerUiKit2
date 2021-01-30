package com.lawlett.taskmanageruikit.room;

import androidx.room.TypeConverter;

import java.util.Date;

import static com.lawlett.taskmanageruikit.achievement.models.AchievementModel.*;

public class Converters {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

    @TypeConverter
    public static Category fromCategory(String name){
        return Category.valueOf(name);
    }

    @TypeConverter
    public static String categoryToName(Category category){
        return category.name();
    }

}
