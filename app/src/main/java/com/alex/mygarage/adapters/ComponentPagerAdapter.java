package com.alex.mygarage.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.alex.mygarage.models.Component;
import com.alex.mygarage.ui.details.ComponentFragment;
import com.alex.mygarage.ui.garage.GarageViewModel;

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
