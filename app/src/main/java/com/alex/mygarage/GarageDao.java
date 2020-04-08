package com.alex.mygarage;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.alex.mygarage.models.Vehicle;
import com.alex.mygarage.models.VehicleField;

import java.util.List;

@Dao
public interface GarageDao {
    @Insert
    long insertVehicle(Vehicle vehicle);

    @Query("SELECT * FROM vehicle")
    LiveData<List<Vehicle>> getVehicles();

    @Query("DELETE FROM vehicle WHERE id = :vid")
    void deleteVehicle(long vid);

    @Update
    void updateVehicle(Vehicle vehicle);

    @Query("SELECT * FROM vehicleField WHERE vid = :vid")
    LiveData<List<VehicleField>> getVehicleFields(long vid);

    @Insert
    void insertVehicleField(VehicleField field);

    @Update
    void updateVehicleFields(List<VehicleField> fields);

    @Query("SELECT * FROM vehicleField")
    List<VehicleField> getAllFields();

    @Delete
    void deleteVehicleField(VehicleField field);
 }
