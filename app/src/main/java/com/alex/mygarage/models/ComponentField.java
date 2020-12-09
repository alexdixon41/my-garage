package com.alex.mygarage.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "componentField")
public class ComponentField {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;
    private String value;
    private long cid;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

}
