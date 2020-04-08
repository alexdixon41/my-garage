package com.alex.mygarage;

import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemDetailsLookup;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.mygarage.adapters.GarageViewAdapter;

public final class VehicleLookup extends ItemDetailsLookup<Long> {
    private RecyclerView recyclerView;

    public VehicleLookup(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    @Nullable
    @Override
    public ItemDetailsLookup.ItemDetails<Long> getItemDetails(@NonNull MotionEvent e) {
        View view = recyclerView.findChildViewUnder(e.getX(), e.getY());
        if (view != null) {
            RecyclerView.ViewHolder viewHolder = recyclerView.getChildViewHolder(view);
            if (viewHolder instanceof GarageViewAdapter.ViewHolder) {
                return ((GarageViewAdapter.ViewHolder) viewHolder).getItemDetails();
            }
        }
        return null;
    }
}
