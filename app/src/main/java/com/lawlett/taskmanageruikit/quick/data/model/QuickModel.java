package com.lawlett.taskmanageruikit.quick.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.lawlett.taskmanageruikit.quick.data.converters.DataConverter;

import java.io.Serializable;
import java.util.Date;

@Entity
public class QuickModel implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    private String description;
    private String image;
    private int color;
    //  @TypeConverters({DataConverter.class})
    private String createData;



    public QuickModel(String title, String description,String createData,String image,int color) {
        this.title = title;
        this.description = description;
        this.createData = createData;
        this.image=image;
        this.color= color;
    }

    public long getId() {
        return id;
    }

    public String getCreateData() {
        return createData;
    }

    public void setCreateData(String createData) {
        this.createData = createData;
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
