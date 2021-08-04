package com.mahmoudbashir.taskepsj.pojo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "data_table")
public class DataModel {
    @PrimaryKey(autoGenerate = true)
    int id;
    String imgUri;
    String optNumber;
    String title;
    String address;

    public DataModel(String imgUri, String optNumber, String title, String address) {
        this.imgUri = imgUri;
        this.optNumber = optNumber;
        this.title = title;
        this.address = address;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    public String getOptNumber() {
        return optNumber;
    }

    public void setOptNumber(String optNumber) {
        this.optNumber = optNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
