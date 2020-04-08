package com.alex.mygarage.ui.garage;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alex.mygarage.GarageRepository;
import com.alex.mygarage.models.Vehicle;
import com.alex.mygarage.models.VehicleField;

import java.util.ArrayList;
import java.util.List;

public class GarageViewModel extends AndroidViewModel {

    private GarageRepository repository;
    private LiveData<List<Vehicle>> vehicleList;
    private final MutableLiveData<Vehicle> selected = new MutableLiveData<Vehicle>();

    public GarageViewModel(@NonNull Application application) {
        super(application);

        repository = new GarageRepository(application);
        vehicleList = repository.getAllVehicles();
    }

    public LiveData<List<Vehicle>> getVehicleList() {
        return vehicleList;
    }

    public void select(Vehicle vehicle) {
        selected.setValue(vehicle);
    }

    public LiveData<Vehicle> getSelected() {
        return selected;
    }

    public LiveData<List<VehicleField>> getVehicleFields() {
        System.out.println("HERE");
        if (selected.getValue() != null)
            return repository.getVehicleFields(selected.getValue().getId());

        System.out.println("No vehicle selected!!!!");
        return repository.getVehicleFields(0);
    }
}