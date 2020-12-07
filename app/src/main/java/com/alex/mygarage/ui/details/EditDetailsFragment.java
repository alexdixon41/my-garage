package com.alex.mygarage.ui.details;

import android.database.DataSetObserver;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.alex.mygarage.GarageRepository;
import com.alex.mygarage.GenericClickListener;
import com.alex.mygarage.R;
import com.alex.mygarage.RecyclerClickListener;
import com.alex.mygarage.adapters.CustomFieldAdapter;
import com.alex.mygarage.adapters.VehicleComponentsAdapter;
import com.alex.mygarage.models.Component;
import com.alex.mygarage.models.Vehicle;
import com.alex.mygarage.models.VehicleField;
import com.alex.mygarage.ui.garage.GarageViewModel;

import java.util.ArrayList;
import java.util.List;


public class EditDetailsFragment extends Fragment {

    private Vehicle mVehicle;             // the vehicle being edited

    private List<VehicleField> vehicleFields;
    private List<Component> vehicleComponents;

    private EditText nameText;
    private EditText yearText;
    private EditText makeText;
    private EditText modelText;
    private EditText trimText;
    private EditText bodyTypeText;
    private Spinner doorsSpinner;
    private EditText colorText;
    private Spinner driveTypeSpinner;

    private GarageViewModel garageViewModel;
    private GarageRepository garageRepository;

    private RecyclerView vehicleFieldsRecyclerView;
    private RecyclerView vehicleComponentsRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        assert getActivity() != null;
        assert getArguments() != null;

        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_edit_details, container, false);
        setHasOptionsMenu(true);

        garageViewModel = ViewModelProviders.of(getActivity()).get(GarageViewModel.class);

        assert getActivity() != null;
        garageRepository = new GarageRepository(getActivity().getApplication());

        // configure recycler view for custom vehicle fields
        vehicleFieldsRecyclerView = root.findViewById(R.id.vehicleFieldsRecyclerView);
        vehicleFieldsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        CustomFieldAdapter customFieldAdapter = new CustomFieldAdapter(garageRepository);
        vehicleFieldsRecyclerView.setAdapter(customFieldAdapter);

        // configure recycler view for vehicle components
        vehicleComponentsRecyclerView = root.findViewById(R.id.editVehicleComponentsRecyclerView);
        vehicleComponentsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        VehicleComponentsAdapter vehicleComponentsAdapter = new VehicleComponentsAdapter();
        vehicleComponentsRecyclerView.setAdapter(vehicleComponentsAdapter);

        // get default field EditTexts
        nameText = root.findViewById(R.id.nameEditText);
        yearText = root.findViewById(R.id.yearEditText);
        makeText = root.findViewById(R.id.makeEditText);
        modelText = root.findViewById(R.id.modelEditText);
        trimText = root.findViewById(R.id.trimEditText);
        bodyTypeText = root.findViewById(R.id.bodyTypeEditText);
        doorsSpinner = root.findViewById(R.id.doorsSpinner);
        colorText = root.findViewById(R.id.colorEditText);
        driveTypeSpinner = root.findViewById(R.id.driveTypeSpinner);

        // style and populate spinners
        Spinner doorsSpinner = root.findViewById(R.id.doorsSpinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.doors_spinner_array, R.layout.info_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        doorsSpinner.setAdapter(adapter);

        // set custom spinner adapter for drive type
        Spinner driveTypeSpinner = root.findViewById(R.id.driveTypeSpinner);
        DriveTypeSpinnerAdapter mAdapter = new DriveTypeSpinnerAdapter();
        driveTypeSpinner.setAdapter(mAdapter);

        // set click listener for add component button
        Button addComponentButton = root.findViewById(R.id.addComponentButton);
        ConstraintLayout editConstraintLayout = root.findViewById(R.id.editConstraintLayout);
        addComponentButton.setOnClickListener(new AddComponentOnClickListener(addComponentButton, mVehicle, editConstraintLayout));

        // update vehicle component list in response to changes to the selected vehicle's custom fields
        garageViewModel.getVehicleComponents().observe(this, components -> {
            EditDetailsFragment.this.vehicleComponents = components;
            vehicleComponentsAdapter.setVehicleComponents(components);
        });

        // update vehicle fields in response to changes to the selected vehicle's custom fields
        garageViewModel.getVehicleFields().observe(this, vehicleFields -> {
            EditDetailsFragment.this.vehicleFields = vehicleFields;
            customFieldAdapter.setCustomFields(vehicleFields);
        });

        // respond to changes in the selected vehicle
        garageViewModel.getSelectedVehicle().observe(this, vehicle -> {
            mVehicle = vehicle;
            loadVehicle();
        });

        // set click listener to add new vehicle field
        NewCustomFieldView newVehicleFieldView = root.findViewById(R.id.newFieldView);
        newVehicleFieldView.getAddFieldButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                VehicleField newField = new VehicleField();
                newField.setName(newVehicleFieldView.getFieldName());
                newField.setValue(newVehicleFieldView.getFieldValue());
                ArrayList<VehicleField> newFields = new ArrayList<>();
                newFields.add(newField);
                garageRepository.updateVehicle(mVehicle, newFields);

                // clear the text for the new field view after the field is added
                newVehicleFieldView.getFieldNameEditText().setText("");
                newVehicleFieldView.getFieldValueEditText().setText("");
            }
        });

        // set click listener to go to editComponent fragment when a component is selected from the recyclerview
        vehicleComponentsRecyclerView.addOnItemTouchListener(new RecyclerClickListener(getContext(),
                vehicleComponentsRecyclerView, new GenericClickListener() {
                    @Override
                    public void onClick(View view, int position) {
                        Component selectedComponent = vehicleComponentsAdapter.getComponent(position);
                        if (selectedComponent == null) {
                            Toast.makeText(getContext(), "Component data not loaded yet", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            garageViewModel.selectComponent(selectedComponent);
                            assert getActivity() != null;
                            NavController c = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                            c.navigate(R.id.editComponentFragment);
                        }
                    }
                }));


        return root;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.edit_vehicle_toolbar_menu, menu);
        MenuItem saveButton = menu.findItem(R.id.saveVehicleButton);
        assert saveButton != null;
        saveButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                saveVehicle();

                garageRepository.updateVehicle(mVehicle);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void loadVehicle() {
        assert getActivity() != null;

        nameText.setText(mVehicle.getName());
        yearText.setText(mVehicle.getYear());
        makeText.setText(mVehicle.getMake());
        modelText.setText(mVehicle.getModel());
        trimText.setText(mVehicle.getTrim());
        bodyTypeText.setText(mVehicle.getBodyType());

        // set doors spinner
        String[] doorArray = getResources().getStringArray(R.array.doors_spinner_array);
        String doors = mVehicle.getDoors();
        int i = 0;
        for (String doorItem : doorArray) {
            if (doorItem.equals(doors))
                break;
            i++;
        }
        if (i < doorArray.length)
            doorsSpinner.setSelection(i);

        colorText.setText(mVehicle.getColor());

        // set driveType spinner
        String[] driveTypeArray = getResources().getStringArray(R.array.drive_type_spinner_dropdown_array);
        String driveType = mVehicle.getDriveType();
        i = 0;
        for (String driveTypeItem : driveTypeArray) {
            if (driveTypeItem.equals(driveType))
                break;
            i++;
        }
        if (i < driveTypeArray.length) {
            driveTypeSpinner.setSelection(i);
        }
    }

    private void saveVehicle() {
        String name = nameText.getText().toString();
        if (name.length() > 0)
            mVehicle.setName(name);

        String year = yearText.getText().toString();
        if (year.length() > 0)
            mVehicle.setYear(year);

        String make = makeText.getText().toString();
        if (make.length() > 0)
            mVehicle.setMake(make);

        String model = modelText.getText().toString();
        if (model.length() > 0)
            mVehicle.setModel(model);

        String trim = trimText.getText().toString();
        if (trim.length() > 0)
            mVehicle.setTrim(trim);

        String bodyType = bodyTypeText.getText().toString();
        if (bodyType.length() > 0)
            mVehicle.setBodyType(bodyType);

        String doors = doorsSpinner.getSelectedItem().toString();
        if (doors.length() > 0)
            mVehicle.setDoors(doors);

        String color = colorText.getText().toString();
        if (color.length() > 0)
            mVehicle.setColor(color);

        String driveType = driveTypeSpinner.getSelectedItem().toString();
        if (driveType.length() > 0)
            mVehicle.setDriveType(driveType);

        ArrayList<VehicleField> fieldsToUpdate = new ArrayList<VehicleField>();
        CustomFieldAdapter adapter = (CustomFieldAdapter) vehicleFieldsRecyclerView.getAdapter();
        assert adapter != null;
        for (int i = 0; i < adapter.getItemCount(); i++) {
            CustomFieldAdapter.CustomFieldViewHolder viewHolder = (CustomFieldAdapter.CustomFieldViewHolder) vehicleFieldsRecyclerView.findViewHolderForAdapterPosition(i);
            if (viewHolder != null) {
                String[] info = viewHolder.getFieldInfo();
                VehicleField field = (VehicleField) adapter.getItemAt(i);
                field.setName(info[0]);
                field.setValue(info[1]);
                fieldsToUpdate.add(field);
            }
        }

        garageRepository.updateVehicleFields(fieldsToUpdate);
    }

    // custom spinner adapter to display shortened items (AWD, FWD, ...) in the spinner
    class DriveTypeSpinnerAdapter implements SpinnerAdapter {

        String[] dropDownItems = getResources().getStringArray(R.array.drive_type_spinner_dropdown_array);
        String[] spinnerItems = getResources().getStringArray(R.array.drive_type_spinner_array);


        @Override
        public View getDropDownView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                TextView text = (TextView) getLayoutInflater().inflate(android.R.layout.simple_spinner_dropdown_item, viewGroup, false);
                text.setText(dropDownItems[i]);
                return text;
            }
            return view;
        }

        @Override
        public void registerDataSetObserver(DataSetObserver dataSetObserver) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

        }

        @Override
        public int getCount() {
            return dropDownItems.length;
        }

        @Override
        public Object getItem(int i) {
            return dropDownItems[i];
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                TextView text = (TextView) getLayoutInflater().inflate(R.layout.info_spinner_item, viewGroup, false);
                text.setText(spinnerItems[i]);
                return text;
            }
            return view;
        }

        @Override
        public int getItemViewType(int i) {
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public boolean isEmpty() {
            return dropDownItems.length == 0;
        }
    }

    class AddComponentOnClickListener implements Button.OnClickListener {

        Button newComponentButton;
        Vehicle selectedVehicle;
        ConstraintLayout editConstraintLayout;

        // IDs for each type of component
        private final int ENGINE_ID = 1;
        private final int TRANSMISSION_ID = 2;
        private final int WHEELS_ID = 3;
        private final int TIRES_ID = 4;

        AddComponentOnClickListener(Button newComponentButton, Vehicle selectedVehicle, ConstraintLayout editConstraintLayout) {
            this.newComponentButton = newComponentButton;
            this.selectedVehicle = selectedVehicle;
            this.editConstraintLayout = editConstraintLayout;
        }

        @Override
        public void onClick(View view) {
            assert getActivity() != null;

            PopupMenu popup = new PopupMenu(getActivity(), newComponentButton);
            popup.getMenuInflater().inflate(R.menu.component_selection_menu, popup.getMenu());

            // set onClick events for each menu item to add the selected type of component
            popup.getMenu().findItem(R.id.engineMenuItem).setOnMenuItemClickListener(result -> {
                Toast.makeText(getActivity(), "Engine selected", Toast.LENGTH_SHORT).show();
                return false;
            });

            popup.getMenu().findItem(R.id.transmissionMenuItem).setOnMenuItemClickListener(result -> {
                Toast.makeText(getActivity(), "Transmission selected", Toast.LENGTH_SHORT).show();
                return false;
            });

            popup.getMenu().findItem(R.id.wheelsMenuItem).setOnMenuItemClickListener(result -> {
                Toast.makeText(getActivity(), "Wheels selected", Toast.LENGTH_SHORT).show();
                return false;
            });

            popup.getMenu().findItem(R.id.tiresMenuItem).setOnMenuItemClickListener(result -> {
                Toast.makeText(getActivity(), "Tires selected", Toast.LENGTH_SHORT).show();
                return false;
            });

            popup.getMenu().findItem(R.id.otherMenuItem).setOnMenuItemClickListener(result -> {
                Toast.makeText(getActivity(), "Other selected", Toast.LENGTH_SHORT).show();
                return false;
            });

            popup.show();
        }
    }
}
