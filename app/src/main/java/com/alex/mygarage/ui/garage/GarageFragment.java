package com.alex.mygarage.ui.garage;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.selection.SelectionPredicates;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.selection.StorageStrategy;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.mygarage.RecyclerClickListener;
import com.alex.mygarage.GarageRepository;
import com.alex.mygarage.GenericClickListener;
import com.alex.mygarage.R;
import com.alex.mygarage.VehicleLookup;
import com.alex.mygarage.VehicleKeyProvider;
import com.alex.mygarage.adapters.GarageViewAdapter;
import com.alex.mygarage.models.Component;
import com.alex.mygarage.models.ComponentField;
import com.alex.mygarage.models.Vehicle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class GarageFragment extends Fragment {

    private GarageViewModel garageViewModel;
    private GarageViewAdapter garageAdapter;
    private SelectionTracker selectionTracker;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        assert getActivity() != null;
        garageViewModel = ViewModelProviders.of(getActivity()).get(GarageViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_garage, container, false);

        final RecyclerView garageRecyclerView = root.findViewById(R.id.garageRecyclerView);
        garageRecyclerView.setLayoutManager(new LinearLayoutManager(container.getContext()));

        garageAdapter = new GarageViewAdapter();
        garageRecyclerView.setAdapter(garageAdapter);           // set the view adapter for the garage

        // floating action button to add a vehicle
        FloatingActionButton fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                assert getActivity() != null;

                // create navcontroller and create vehicle object
                // once vehicle is inserted and id properly updated, navigate to details view
                NavController nav = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                Vehicle newVehicle = new Vehicle();
                (new InsertNewVehicleTask(newVehicle, new GarageRepository(getActivity().getApplication()),
                        garageViewModel, nav)).execute();
            }
        });

        // track multiple item selection triggered by an initial long press
        selectionTracker = new SelectionTracker.Builder<Long>("vehicle_selection",
                garageRecyclerView,
                new VehicleKeyProvider(garageRecyclerView),
                new VehicleLookup(garageRecyclerView),
                StorageStrategy.createLongStorage())
                .withSelectionPredicate(SelectionPredicates.<Long>createSelectAnything())
                .build();

        // detect click events on a single item to launch the details fragment for the selected vehicle
        garageRecyclerView.addOnItemTouchListener(new RecyclerClickListener(getContext(), garageRecyclerView, new GenericClickListener() {
            @Override
            public void onClick(View view, int position) {
                Vehicle selectedVehicle = garageAdapter.getVehicle(position);
                if (selectedVehicle == null) {
                    Toast.makeText(getContext(), "Vehicle data not loaded yet", Toast.LENGTH_SHORT).show();
                }
                else {
                    garageViewModel.selectVehicle(selectedVehicle);
                    Activity activity = getActivity();
                    assert activity != null;
                    NavController c = Navigation.findNavController(activity, R.id.nav_host_fragment);
                    c.navigate(R.id.details_fragment);
                }
            }
        }));

        garageAdapter.setSelectionTracker(selectionTracker);

        // update RecyclerView in response to changes to the garage
        garageViewModel.getVehicleList().observe(getActivity(), new Observer<List<Vehicle>>() {
            @Override
            public void onChanged(@Nullable List<Vehicle> vehicles) {
                garageAdapter.setGarageVehicles(vehicles);
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (selectionTracker != null) {
            selectionTracker.onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (selectionTracker != null) {
            selectionTracker.onSaveInstanceState(outState);
        }
    }

    /**
     * Task to insert new vehicle, then update the object's id once insert is complete, and finally
     * navigate to the details view with the addVehicleAction
      */
    static class InsertNewVehicleTask extends AsyncTask<Vehicle, Long, Long> {

        Vehicle newVehicle;
        GarageRepository repository;
        GarageViewModel garageViewModel;
        NavController nav;

        InsertNewVehicleTask(Vehicle newVehicle, GarageRepository repository, GarageViewModel garageViewModel, NavController nav) {
            this.newVehicle = newVehicle;
            this.repository = repository;
            this.garageViewModel = garageViewModel;
            this.nav = nav;
        }

        @Override
        protected Long doInBackground(Vehicle... vehicles) {
            long vehicleInsertId = repository.insertVehicle(newVehicle);            // insert the new vehicle so its id will be set

            // insert engine component
            Component engine = new Component();
            engine.setIconName("ic_engine_icon_vector");
            engine.setName("Engine");
            engine.setVehicleId(vehicleInsertId);
            long componentInsertId = repository.insertVehicleComponent(engine);

            // create engine component default fields and add to list so they can all be inserted at once
            ArrayList<ComponentField> engineFields = new ArrayList<>();
            ComponentField displacement = new ComponentField();
            displacement.setName("Displacement");
            displacement.setCid(componentInsertId);
            engineFields.add(displacement);
            ComponentField config = new ComponentField();
            config.setName("Configuration");
            config.setCid(componentInsertId);
            engineFields.add(config);
            ComponentField valvesPerCylinder = new ComponentField();
            valvesPerCylinder.setName("Valves per cylinder");
            valvesPerCylinder.setCid(componentInsertId);
            engineFields.add(valvesPerCylinder);
            ComponentField cylinderHeadConfig = new ComponentField();
            cylinderHeadConfig.setName("Cylinder head configuration");
            cylinderHeadConfig.setCid(componentInsertId);
            engineFields.add(cylinderHeadConfig);

            // insert default engine fields
            repository.insertComponentFields(engineFields);

            // insert transmission component
            Component transmission = new Component();
            transmission.setIconName("ic_transmission_icon_vector");
            transmission.setName("Transmission");
            transmission.setVehicleId(vehicleInsertId);
            componentInsertId = repository.insertVehicleComponent(transmission);

            // create transmission component default fields and add to list so they can all be inserted at once
            ArrayList<ComponentField> transmissionFields = new ArrayList<>();
            ComponentField gears = new ComponentField();
            gears.setName("Gears");
            gears.setCid(componentInsertId);
            transmissionFields.add(gears);
            ComponentField type = new ComponentField();
            type.setName("Type");
            type.setCid(componentInsertId);
            transmissionFields.add(type);
            ComponentField manufacturer = new ComponentField();
            manufacturer.setName("Manufacturer");
            manufacturer.setCid(componentInsertId);
            transmissionFields.add(manufacturer);
            ComponentField name = new ComponentField();
            name.setName("Name");
            name.setCid(componentInsertId);
            transmissionFields.add(name);
            repository.insertComponentFields(transmissionFields);

            // insert tires component
            Component tires = new Component();
            tires.setIconName("ic_tires_icon_vector");
            tires.setName("Tires");
            tires.setVehicleId(vehicleInsertId);
            repository.insertVehicleComponent(tires);

            // create tires component default fields and add to list so they can all be inserted at once
            ArrayList<ComponentField> tiresFields = new ArrayList<>();
            ComponentField tireName = new ComponentField();
            tireName.setName("Name");
            tireName.setCid(componentInsertId);
            tiresFields.add(tireName);
            ComponentField size = new ComponentField();
            size.setName("Size");
            size.setCid(componentInsertId);
            tiresFields.add(size);
            repository.insertComponentFields(tiresFields);

            return vehicleInsertId;
        }

        @Override
        protected void onPostExecute(Long result) {
            if (result == null) {
                Log.w("GarageFragment.java", "INSERT ID IS NULL!");
            }
            else {
                newVehicle.setId(result);
                garageViewModel.selectVehicle(newVehicle);
                GarageFragmentDirections.AddVehicleAction action = GarageFragmentDirections.addVehicleAction();
                nav.navigate(action);
            }
        }
    }

}