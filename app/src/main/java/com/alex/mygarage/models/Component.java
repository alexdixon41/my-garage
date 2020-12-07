package com.alex.mygarage.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "component")
public class Component {

    @PrimaryKey (autoGenerate = true)
    private long id;

    private long vehicleId;
    private String name;
    private String iconName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

}
