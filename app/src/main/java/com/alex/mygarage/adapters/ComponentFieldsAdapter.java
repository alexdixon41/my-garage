package com.alex.mygarage.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.mygarage.R;
import com.alex.mygarage.models.Component;
import com.alex.mygarage.models.ComponentField;

import java.util.List;

public class ComponentFieldsAdapter extends RecyclerView.Adapter<ComponentFieldsAdapter.ViewHolder> {

    private List<ComponentField> componentFields;

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView fieldNameText, fieldValueText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fieldNameText = itemView.findViewById(R.id.fieldNameText);
            fieldValueText = itemView.findViewById(R.id.fieldValueText);
        }

        public void bind(String fieldName, String fieldValue) {
            if (fieldName != null) {
                if (fieldName.length() > 1)
                    this.fieldNameText.setText(fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));
                else {
                    this.fieldNameText.setText(fieldName.toUpperCase());
                }
            }
            this.fieldValueText.setText(fieldValue != null ? fieldValue : "");
        }
    }

    public void setComponentFields(List<ComponentField> fields) {
        this.componentFields = fields;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View fieldView = inflater.inflate(R.layout.field_view, parent, false);

        return new ViewHolder(fieldView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (componentFields != null) {
            ComponentField comp = componentFields.get(position);

            holder.bind(comp.getName(), comp.getValue());
        }
        else {
            holder.bind("No fields", "No fields");
        }
    }

    @Override
    public int getItemCount() {
        if (componentFields != null) {
            return componentFields.size();
        }
        return 0;
    }
}
