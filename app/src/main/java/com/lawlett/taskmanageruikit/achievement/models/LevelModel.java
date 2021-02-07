package com.lawlett.taskmanageruikit.achievement.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "level")
public class LevelModel {

    @PrimaryKey
    private int id;
    private Date date;
    private String level;
    private String imageURL;

    public LevelModel(int id, Date date, String level) {
        this.id = id;
        this.date = date;
        this.level = level;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

}
