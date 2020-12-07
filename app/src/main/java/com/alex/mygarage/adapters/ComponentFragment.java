package com.alex.mygarage.adapters;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alex.mygarage.R;
import com.alex.mygarage.models.Component;
import com.alex.mygarage.ui.garage.GarageViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComponentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComponentFragment extends Fragment {

    public static final String ARG_POSITION = "position";
    GarageViewModel garageViewModel;

    private int position;
    private Component component;

    public ComponentFragment() {
        // Required empty public constructor
    }


    public static ComponentFragment newInstance(int position) {
        ComponentFragment fragment = new ComponentFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            assert getActivity() != null;
            position = getArguments().getInt(ARG_POSITION);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        assert getActivity() != null;
        garageViewModel = ViewModelProviders.of(getActivity()).get(GarageViewModel.class);

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_component, container, false);

        TextView componentNameText = root.findViewById(R.id.componentNameTextView);

        garageViewModel.getVehicleComponents().observe(this, components -> {
            if (components.get(this.position) != null) {
                component = components.get(this.position);
            }
            if (component != null) {
                componentNameText.setText(component.getName());
            }
        });

        return root;
    }

}