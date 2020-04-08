package com.alex.mygarage;

import android.content.ClipData;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.selection.ItemKeyProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Objects;

public class VehicleKeyProvider extends ItemKeyProvider<Long> {

    private RecyclerView garageRecyclerView;

    public VehicleKeyProvider(RecyclerView garageRecyclerView) {
        super(ItemKeyProvider.SCOPE_MAPPED);

        this.garageRecyclerView = garageRecyclerView;
    }

    @Nullable
    @Override
    public Long getKey(int position) {
        System.out.println("TRYING TO GET THE KEY!!!!!!");
        if (garageRecyclerView.getAdapter() != null) {
            return garageRecyclerView.getAdapter().getItemId(position);
        }
        return null;
    }

    @Override
    public int getPosition(@NonNull Long key) {
        System.out.println("TRYING TO GET THE POSITION!!!!!!");
        RecyclerView.ViewHolder viewHolder = garageRecyclerView.findViewHolderForItemId(key);
        if (viewHolder != null) {
            return viewHolder.getLayoutPosition();
        }
        return RecyclerView.NO_POSITION;
    }
}
