package com.alex.mygarage.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "componentField")
public class ComponentField implements CustomField {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;
    private String value;
    private long cid;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
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
