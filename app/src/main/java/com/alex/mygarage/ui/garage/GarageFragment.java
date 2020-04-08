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

import com.alex.mygarage.GarageRecyclerTouchListener;
import com.alex.mygarage.GarageRepository;
import com.alex.mygarage.GenericClickListener;
import com.alex.mygarage.R;
import com.alex.mygarage.VehicleLookup;
import com.alex.mygarage.VehicleKeyProvider;
import com.alex.mygarage.adapters.GarageViewAdapter;
import com.alex.mygarage.models.Vehicle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
        garageRecyclerView.addOnItemTouchListener(new GarageRecyclerTouchListener(getContext(), garageRecyclerView, new GenericClickListener() {
            @Override
            public void onClick(View view, int position) {
                Vehicle selectedVehicle = garageAdapter.getVehicle(position);
                if (selectedVehicle == null) {
                    Toast.makeText(getContext(), "Vehicle data not loaded yet", Toast.LENGTH_SHORT).show();
                }
                else {
                    garageViewModel.select(selectedVehicle);
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
            return repository.insertVehicle(newVehicle);            // insert the new vehicle so its id will be set
        }

        @Override
        protected void onPostExecute(Long result) {
            if (result == null) {
                Log.w("GarageFragment.java", "INSERT ID IS NULL!");
            }
            else {
                newVehicle.setId(result);
                garageViewModel.select(newVehicle);
                System.out.println("THE ID IS: " + newVehicle.getId());
                GarageFragmentDirections.AddVehicleAction action = GarageFragmentDirections.addVehicleAction();
                nav.navigate(action);
            }
        }
    }

}