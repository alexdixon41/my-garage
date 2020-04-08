package com.alex.mygarage.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "vehicle")
public class Vehicle {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name = "Unnamed";
    private String imageName = "";
    private String year = "";
    private String make = "";
    private String model = "";
    private String trim = "";
    private String bodyType = "";
    private String doors = "";
    private String color = "";
    private String driveType = "";
    private String generalOther = "";

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public String getDoors() {
        return doors;
    }

    public void setDoors(String doors) {
        this.doors = doors;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public void setTrim(String trim) {
        this.trim = trim;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setDriveType(String driveType) {
        this.driveType = driveType;
    }

    public void setGeneralOther(String generalOther) {
        this.generalOther = generalOther;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageName() {
        return imageName;
    }

    public String getTrim() {
        return trim;
    }

    public String getColor() {
        return color;
    }

    public String getDriveType() {
        return driveType;
    }

    public String getGeneralOther() {
        return generalOther;
    }
}
