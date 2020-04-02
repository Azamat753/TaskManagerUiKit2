package com.lawlett.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.bumptech.glide.annotation.Excludes;

import java.io.Serializable;

@Entity
public class QuickModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    private String description;
    private String image;
    private int color;

    public QuickModel(String title, String description) {
        this.title = title;
        this.description = description;

    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
