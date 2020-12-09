package com.alex.mygarage.ui.garage;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alex.mygarage.GarageRepository;
import com.alex.mygarage.models.Component;
import com.alex.mygarage.models.ComponentField;
import com.alex.mygarage.models.Vehicle;
import com.alex.mygarage.models.VehicleField;

import java.util.List;

public class GarageViewModel extends AndroidViewModel {

    private GarageRepository repository;
    private LiveData<List<Vehicle>> vehicleList;
    private final MutableLiveData<Vehicle> selectedVehicle = new MutableLiveData<>();
    private final MutableLiveData<Component> selectedComponent = new MutableLiveData<>();

    public GarageViewModel(@NonNull Application application) {
        super(application);

        repository = new GarageRepository(application);
        vehicleList = repository.getAllVehicles();
    }

    public LiveData<List<Vehicle>> getVehicleList() {
        return vehicleList;
    }

    public void selectVehicle(Vehicle vehicle) {
        selectedVehicle.setValue(vehicle);
    }

    public LiveData<Vehicle> getSelectedVehicle() {
        return selectedVehicle;
    }

    public LiveData<List<VehicleField>> getVehicleFields() {
        if (selectedVehicle.getValue() != null)
            return repository.getVehicleFields(selectedVehicle.getValue().getId());

        System.out.println("No vehicle selected!!!!");
        return repository.getVehicleFields(0);
    }

    public LiveData<List<Component>> getVehicleComponents() {
        if (selectedVehicle.getValue() != null)
            return repository.getVehicleComponents(selectedVehicle.getValue().getId());

        System.out.println("No vehicle selected when retrieving components!!!!");
        return repository.getVehicleComponents(0);
    }

    public void selectComponentWithId(long id) {
        Component selected = repository.getComponentById(id).getValue();
        if (selected != null) {
            System.out.println(selected.getName());
        }
        selectedComponent.setValue(selected);
    }

    public void selectComponent(Component component) {
        selectedComponent.setValue(component);
    }

    public LiveData<Component> getSelectedComponent() {
        return selectedComponent;
    }

    public LiveData<List<ComponentField>> getComponentFields(Component component) {
        System.out.println("Component to get fields for: " + component.getName() + " - " + component.getId());
        return repository.getComponentFields(component.getId());
    }

}