package com.alex.mygarage.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.mygarage.R;
import com.alex.mygarage.models.Component;

import java.util.List;

public class VehicleComponentsAdapter extends RecyclerView.Adapter<VehicleComponentsAdapter.ViewHolder> {

    private List<Component> vehicleComponents;
    private Resources res;
    private String packageName;

    class ViewHolder extends RecyclerView.ViewHolder {

        private View root;
        private ImageView iconView;
        private TextView nameTextView;

        ViewHolder(View v) {
            super(v);

            root = v;
            iconView = root.findViewById(R.id.componentIconImageView);
            nameTextView = root.findViewById(R.id.componentNameTextView);
        }

        public void bind(String componentName, int iconId) {
            nameTextView.setText(componentName);
            iconView.setImageResource(iconId);
        }
    }

    public void setVehicleComponents(List<Component> components) {
        vehicleComponents = components;
        notifyDataSetChanged();
    }

    public Component getComponent(int position) {
        if (vehicleComponents != null)
            return vehicleComponents.get(position);
        return null;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View componentView = inflater.inflate(R.layout.component_list_item, parent, false);

        res = parent.getResources();
        packageName = context.getPackageName();

        return new ViewHolder(componentView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (vehicleComponents != null) {
            Component comp = vehicleComponents.get(position);
            int iconId = res.getIdentifier(comp.getIconName(), "drawable", packageName);
            System.out.println(iconId);
            holder.bind(comp.getName(), iconId);
        }
        else {
            holder.bind("No components", 0);
        }
    }

    @Override
    public int getItemCount() {
        if (vehicleComponents != null)
            return vehicleComponents.size();
        return 0;
    }


}
