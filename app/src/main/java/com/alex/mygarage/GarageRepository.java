package com.alex.mygarage;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.alex.mygarage.models.Component;
import com.alex.mygarage.models.ComponentField;
import com.alex.mygarage.models.Vehicle;
import com.alex.mygarage.models.VehicleField;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class GarageRepository {

    private GarageDao garageDao;
    private LiveData<List<Vehicle>> allVehicles;

    public GarageRepository(Application application) {
        GarageRoomDatabase db = GarageRoomDatabase.getDatabase(application);
        garageDao = db.garageDao();
        allVehicles = garageDao.getVehicles();
    }

    public LiveData<List<Vehicle>> getAllVehicles() {
        return allVehicles;
    }

    public long insertVehicle(Vehicle vehicle) {
        long insertId = 0;
        Callable<Long> insertCallable = () ->
            garageDao.insertVehicle(vehicle);
        Future<Long> future = GarageRoomDatabase.databaseWriteExecutor.submit(insertCallable);
        try {
            insertId = future.get();
        }
        catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }

        return insertId;
    }

    public void deleteVehicle(long vid) {
        GarageRoomDatabase.databaseWriteExecutor.execute(() -> {
            garageDao.deleteVehicle(vid);
        });
    }

    public void updateVehicle(Vehicle vehicle) {
        GarageRoomDatabase.databaseWriteExecutor.execute(() -> {
            garageDao.updateVehicle(vehicle);
        });
    }

    public void updateVehicle(Vehicle vehicle, List<VehicleField> customFields) {
        GarageRoomDatabase.databaseWriteExecutor.execute(() -> {
            garageDao.updateVehicle(vehicle);
            System.out.println("Vehicle id is: " + vehicle.getId());
            for (VehicleField field : customFields) {
                System.out.println("inserting field");
                field.setVid(vehicle.getId());
                garageDao.insertVehicleField(field);
            }
        });
    }

    public LiveData<List<VehicleField>> getVehicleFields(long vid) {
        System.out.println("The id to retrieve is : " + vid);
        return garageDao.getVehicleFields(vid);
    }

    public void deleteVehicleField(VehicleField field) {
        GarageRoomDatabase.databaseWriteExecutor.execute(() -> {
            garageDao.deleteVehicleField(field);
        });
    }

    public void updateVehicleFields(List<VehicleField> fields) {
        GarageRoomDatabase.databaseWriteExecutor.execute(() -> {
            garageDao.updateVehicleFields(fields);
        });
    }

    public LiveData<List<Component>> getVehicleComponents(long vid) {
        return garageDao.getVehicleComponents(vid);
    }

    public LiveData<Component> getComponentById(long cid) {
        System.out.println("Repository: " + cid);
        System.out.println(garageDao.getComponent(1).getValue());
        return garageDao.getComponent(cid);
    }

    public long insertVehicleComponent(Component component) {
        long insertId = 0;
        Callable<Long> insertCallable = () ->
                garageDao.insertVehicleComponent(component);
        Future<Long> future = GarageRoomDatabase.databaseWriteExecutor.submit(insertCallable);
        try {
            insertId = future.get();
        }
        catch (InterruptedException | ExecutionException ex) {
            ex.printStackTrace();
        }

        return insertId;
    }

    public void insertComponentField(ComponentField field) {
        GarageRoomDatabase.databaseWriteExecutor.execute(() -> {
            garageDao.insertComponentField(field);
        });
    }

    public void insertComponentFields(List<ComponentField> fields) {
        GarageRoomDatabase.databaseWriteExecutor.execute(() -> {
            garageDao.insertComponentFields(fields);
        });
    }

    public LiveData<List<ComponentField>> getComponentFields(long cid) {
        LiveData<List<ComponentField>> fields = garageDao.getComponentFields(cid);
        System.out.println(fields.getValue() == null ? "NULL" : "FIELD COUNT: " + fields.getValue().size());
        return garageDao.getComponentFields(cid);
    }
}
