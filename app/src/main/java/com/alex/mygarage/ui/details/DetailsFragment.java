package com.alex.mygarage.ui.details;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager2.widget.ViewPager2;

import com.alex.mygarage.GarageRepository;
import com.alex.mygarage.MainActivity;
import com.alex.mygarage.R;
import com.alex.mygarage.adapters.ComponentPagerAdapter;
import com.alex.mygarage.models.Component;
import com.alex.mygarage.models.Vehicle;
import com.alex.mygarage.ui.garage.GarageViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class DetailsFragment extends Fragment {

    private GarageViewModel garageViewModel;

    private CardView vehicleImageView;
    private List<Component> components;

    private void setComponents(List<Component> components) {
        this.components = components;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        assert getActivity() != null;
        garageViewModel = ViewModelProviders.of(getActivity()).get(GarageViewModel.class);
        View root = inflater.inflate(R.layout.vehicle_fragment, container, false);

//        vehicleImageView = root.findViewById(R.id.vehicleMainImage);

//        TextView nameTextView = root.findViewById(R.id.vehicleNameText);
//        TextView yearMakeModelTextView = root.findViewById(R.id.yearMakeModelText);
//        TextView trimBodyDoorsTextView = root.findViewById(R.id.trimBodyDoorsText);
//        TextView colorTextView = root.findViewById(R.id.colorText);
//        TextView driveTypeTextView = root.findViewById(R.id.driveTypeText);
//        TextView generalOtherTextView = root.findViewById(R.id.generalOtherText);

        // get the id of the vehicle to display details for
        garageViewModel.getSelectedVehicle().observe(getViewLifecycleOwner(), (vehicle) -> {
            Toolbar toolbar = root.findViewById(R.id.pinnedToolbar);
            toolbar.setTitle(vehicle.getName());
        });


        ViewPager2 viewPager = root.findViewById(R.id.viewPager);
        TabLayout tabLayout = root.findViewById(R.id.tabLayout);

        // update vehicle component list in response to changes to the selected vehicle's custom fields
        garageViewModel.getVehicleComponents().observe(this, components -> {
            ComponentPagerAdapter componentPagerAdapter = new ComponentPagerAdapter(this, components);
            viewPager.setAdapter(componentPagerAdapter);

            new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
                tab.setText(components.get(position).getName());

            }).attach();
        });

        /*if (selectedVehicle != null) {
            nameTextView.setText(selectedVehicle.getName());

            String yearMakeModel = selectedVehicle.getYear() + " " + selectedVehicle.getMake() + " " + selectedVehicle.getModel();
            yearMakeModelTextView.setText(yearMakeModel);

            String trimBodyDoors = selectedVehicle.getTrim() + " " + selectedVehicle.getBodyType() + " " + selectedVehicle.getDoors();
            trimBodyDoorsTextView.setText(trimBodyDoors);

            colorTextView.setText(selectedVehicle.getColor());
            driveTypeTextView.setText(selectedVehicle.getDriveType());
            generalOtherTextView.setText(selectedVehicle.getGeneralOther());
        } else {
            Toast.makeText(getContext(), "No vehicle selected", Toast.LENGTH_SHORT).show();
            assert getActivity() != null;
            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigateUp();
        }*/

        // change image
//        vehicleImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                assert getActivity() != null;
//                PopupMenu popup = new PopupMenu(getActivity(), vehicleImageView);
//                MenuInflater inflater = popup.getMenuInflater();
//                inflater.inflate(R.menu.set_vehicle_image_menu, popup.getMenu());
//                popup.getMenu().findItem(R.id.setImageMenuItem).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem menuItem) {
//                        // TODO - launch activity to set an image for the vehicle
//                        Toast.makeText(getActivity(), "Cannot change image at this time", Toast.LENGTH_SHORT).show();
//                        return false;
//                    }
//                });
//                popup.show();
//            }
//        });

//        Button editButton = root.findViewById(R.id.editButton);
//        editButton.setOnClickListener(new EditOnClickListener(editButton, selectedVehicle));

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavController navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();
        Toolbar toolbar = view.findViewById(R.id.pinnedToolbar);

        NavigationUI.setupWithNavController(
                toolbar, navController, appBarConfiguration);
    }

    @Override
    public void onResume() {
        super.onResume();

//        ((MainActivity)getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();

//        ((MainActivity)getActivity()).getSupportActionBar().show();
    }


    class EditOnClickListener implements Button.OnClickListener {

        Button editButton;
        Vehicle selectedVehicle;

        EditOnClickListener(Button editButton, Vehicle selectedVehicle) {
            this.editButton = editButton;
            this.selectedVehicle = selectedVehicle;
        }

        @Override
        public void onClick(View view) {
            assert getActivity() != null;
            PopupMenu popup = new PopupMenu(getActivity(), editButton);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.edit_vehicle_menu, popup.getMenu());

            // set onClick listener for each menu item

            popup.getMenu().findItem(R.id.editVehicleInfoMenuItem).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    NavController nav = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                    nav.navigate(R.id.actionEditVehicle);

                    return false;
                }
            });

            popup.getMenu().findItem(R.id.deleteVehicleMenuItem).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    new AlertDialog.Builder(getActivity()).setMessage(R.string.confirm_vehicle_delete_message)
                            .setNegativeButton(R.string.cancel_delete_choice, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .setPositiveButton(R.string.confirm_delete_choice, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    (new GarageRepository(getActivity().getApplication())).deleteVehicle(selectedVehicle.getId());
                                    NavController nav = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                                    nav.navigateUp();
                                }
                            })
                            .show();

                    return false;
                }
            });

            popup.show();
        }
    }
}