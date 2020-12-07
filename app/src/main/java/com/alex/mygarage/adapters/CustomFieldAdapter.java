package com.alex.mygarage.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alex.mygarage.GarageRepository;
import com.alex.mygarage.R;
import com.alex.mygarage.models.CustomField;
import com.alex.mygarage.models.VehicleField;

import java.util.List;

public class CustomFieldAdapter extends RecyclerView.Adapter<CustomFieldAdapter.CustomFieldViewHolder> {

    private List<? extends CustomField> customFields;
    private GarageRepository garageRepository;

    public class CustomFieldViewHolder extends RecyclerView.ViewHolder {

        private View root;
        private EditText fieldNameEditText;
        private EditText fieldValueEditText;
        private ImageButton removeFieldButton;

        public CustomFieldViewHolder(@NonNull View itemView) {
            super(itemView);

            root = itemView;

            // get sub views of the custom field viewholder
            fieldNameEditText = root.findViewById(R.id.fieldNameEditText);
            fieldValueEditText = root.findViewById(R.id.fieldValueEditText);
            removeFieldButton = root.findViewById(R.id.removeFieldButton);
        }

        public void bind(CustomField field) {
            fieldNameEditText.setText(field.getName());
            fieldValueEditText.setText(field.getValue());

            if (field instanceof VehicleField) {
                removeFieldButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        removeAt(getAdapterPosition());
                    }
                });
            }
        }

        public String[] getFieldInfo() {
            return new String[] { fieldNameEditText.getText().toString(), fieldValueEditText.getText().toString() };
        }
    }

    private void removeAt(int position) {
        garageRepository.deleteVehicleField((VehicleField) customFields.get(position));
    }

    public CustomField getItemAt(int position) {
        return customFields.get(position);
    }

    public CustomFieldAdapter(GarageRepository repository) {
        super();

        garageRepository = repository;
    }

    public void setCustomFields(List<? extends CustomField> fields) {
        customFields = fields;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CustomFieldViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View fieldView = inflater.inflate(R.layout.edit_field_view, parent, false);

        return new CustomFieldViewHolder(fieldView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomFieldViewHolder holder, int position) {
        if (customFields != null) {
            CustomField field = customFields.get(position);
            holder.bind(field);
        }
    }

    @Override
    public long getItemId(int position) {
        return customFields.get(position).getId();
    }

    @Override
    public int getItemCount() {
        if (customFields != null)
            return customFields.size();
        return 0;
    }
}
