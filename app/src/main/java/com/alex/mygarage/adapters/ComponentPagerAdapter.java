package com.alex.mygarage.adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.alex.mygarage.models.Component;

import java.util.List;

public class ComponentPagerAdapter extends FragmentStateAdapter {
    List<Component> components;

    public ComponentPagerAdapter(Fragment fragment, List<Component> components) {
        super(fragment);
        this.components = components;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return ComponentFragment.newInstance(position);
    }

    @Override
    public int getItemCount() {
        return this.components.size();
    }
}
