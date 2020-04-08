package com.alex.mygarage.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.selection.SelectionTracker;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.mygarage.R;
import com.alex.mygarage.models.Vehicle;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class GarageViewAdapter extends RecyclerView.Adapter<GarageViewAdapter.ViewHolder> {

    private List<Vehicle> garageVehicles;
    private SelectionTracker selectionTracker;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private View rootView;
        public RoundedImageView vehicleImage;
        public TextView vehicleTextView;
        private TextView yearMakeModelTextView;

        public ViewHolder(View v) {
            super(v);

            rootView = v;
//            vehicleImage = (RoundedImageView) v.findViewById(R.id.vehicleImage);
            vehicleTextView = (TextView) v.findViewById(R.id.vehicleText);
            yearMakeModelTextView = v.findViewById(R.id.vehicleListItemYearMakeModelText);
        }

        public void bind(String vehicleText, String yearMakeModel, String imagePath, boolean isActivated) {
            vehicleTextView.setText(vehicleText);
            if (yearMakeModel == null) {
                yearMakeModelTextView.setVisibility(View.GONE);
            }
            else {
                yearMakeModelTextView.setText(yearMakeModel);
            }

            // TODO - set image dynamically using imagePath
//            vehicleImage.setImageResource(R.drawable.m);
            itemView.setActivated(isActivated);
        }

        public ItemDetailsLookup.ItemDetails<Long> getItemDetails() {
            System.out.println("Position: " + getAdapterPosition() + " key: " + getItemId());
            return new ItemDetailsLookup.ItemDetails<Long>() {
                @Override
                public int getPosition() {
                    return getAdapterPosition();
                }

                @Override
                public Long getSelectionKey() {
                    return getItemId();
                }
            };
        }
    }

    public void setSelectionTracker(SelectionTracker selectionTracker) {
        this.selectionTracker = selectionTracker;
    }

    public void setGarageVehicles(List<Vehicle> vehicles) {
        garageVehicles = vehicles;
        notifyDataSetChanged();
    }

    public Vehicle getVehicle(int position) {
        if (garageVehicles != null) {
            return garageVehicles.get(position);
        }
        return null;
    }

    public GarageViewAdapter() {
        setHasStableIds(true);
    }

    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // get the layout inflater from the parent context to inflate the list item custom view
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // inflate the custom view for list items
        View vehicleView = inflater.inflate(R.layout.vehicle_list_item, parent, false);

        return new ViewHolder(vehicleView);
    }

    // populate data through the ViewHolder
    @Override
    public void onBindViewHolder(@NonNull GarageViewAdapter.ViewHolder holder, int position) {
        long key = (long) position;
        if (garageVehicles != null) {
            Vehicle vehicle = garageVehicles.get(position);
            String yearMakeModel = vehicle.getYear() + " " + vehicle.getMake() + " " + vehicle.getModel();
            if (yearMakeModel.equals(vehicle.getName()))
                holder.bind(vehicle.getName(), null, vehicle.getImageName(), selectionTracker.isSelected(key));
            else
                holder.bind(vehicle.getName(), yearMakeModel, vehicle.getImageName(), selectionTracker.isSelected(key));
        }
        else {
            holder.bind("No vehicle", null, null, selectionTracker.isSelected(key));
        }
    }

    @Override
    public long getItemId(int position) {
        return (long) position;
    }

    @Override
    public int getItemCount() {
        if (garageVehicles != null) {
            return garageVehicles.size();
        }
        return 0;
    }

}
