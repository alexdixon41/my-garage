package com.alex.mygarage.ui.details;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.alex.mygarage.R;
import com.alex.mygarage.models.Component;
import com.alex.mygarage.models.ComponentField;
import com.alex.mygarage.ui.garage.GarageViewModel;

import java.util.List;

public class EditComponentFragment extends Fragment {

    private GarageViewModel garageViewModel;
    private Component selectedComponent;
    private List<ComponentField> componentFields;

    private EditText componentNameEditText;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        assert getActivity() != null;
        garageViewModel = ViewModelProviders.of(getActivity()).get(GarageViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_edit_component, container, false);

//        componentNameEditText = root.findViewById(R.id.componentNameEditText);
//
//        // observe changes to selected component to load the component for editing
//        garageViewModel.getSelectedComponent().observe(this, component -> {
//            EditComponentFragment.this.selectedComponent = component;
//            loadComponent();
//        });
//
//        // observe changes to selected component fields
//        garageViewModel.getComponentFields().observe(this, fields -> {
//            EditComponentFragment.this.componentFields = fields;
//            for (ComponentField field : fields) {
//                System.out.println(field.getName() + " : " + field.getValue());
//            }
//        });
        return root;
    }

    /**
     * load the component data into the fragment UI for editing
      */
    private void loadComponent() {
        assert getActivity() != null;

        componentNameEditText.setText(selectedComponent.getName());
    }
}
