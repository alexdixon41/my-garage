package com.alex.mygarage.ui.details;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alex.mygarage.GarageRepository;
import com.alex.mygarage.R;
import com.alex.mygarage.adapters.ComponentFieldsAdapter;
import com.alex.mygarage.models.Component;
import com.alex.mygarage.models.ComponentField;
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
//    private ComponentFieldsAdapter fieldsAdapter;

    public ComponentFragment() {
        // Required empty public constructor
    }

    public static ComponentFragment newInstance(int position) {
        ComponentFragment newFragment = new ComponentFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        newFragment.setArguments(args);
        return newFragment;
    }

    public void setComponent(Component component) {
        this.component = component;
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
        garageViewModel = ViewModelProviders.of(requireActivity()).get(GarageViewModel.class);

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_component, container, false);

//        TextView componentNameText = root.findViewById(R.id.componentNameTextView);
//        ImageView iconView = root.findViewById(R.id.componentIcon);

        // set up fields recyclerview
        final RecyclerView componentRecyclerView = root.findViewById(R.id.componentRecyclerView);
        componentRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        ComponentFieldsAdapter fieldsAdapter = new ComponentFieldsAdapter();
        componentRecyclerView.setAdapter(fieldsAdapter);

        garageViewModel.getVehicleComponents().observe(getViewLifecycleOwner(), components -> {
            System.out.println("Position: " + ComponentFragment.this.position + " - Component: " + components.get(ComponentFragment.this.position).getName());
            ComponentFragment.this.component = components.get(ComponentFragment.this.position);
            System.out.println("Selected Component Name: " + ComponentFragment.this.component.getName());

//            componentNameText.setText(ComponentFragment.this.component.getName());
//            iconView.setImageResource(getResources().getIdentifier("com.alex.mygarage:drawable/" + ComponentFragment.this.component.getIconName(), "drawable", "com.alex.mygarage"));

            // set component fields on recyclerview
            // (I hate that this works - setting an observer inside the observer - oh well)
            garageViewModel.getComponentFields(this.component).observe(getViewLifecycleOwner(), fieldsAdapter::setComponentFields);
        });

        return root;
    }

}