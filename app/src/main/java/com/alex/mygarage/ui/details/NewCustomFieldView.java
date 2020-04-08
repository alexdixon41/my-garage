package com.alex.mygarage.ui.details;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.alex.mygarage.R;
import com.alex.mygarage.models.VehicleField;

public class NewCustomFieldView extends ConstraintLayout {

    private EditText fieldNameEditText;
    private EditText fieldValueEditText;
    private Button addFieldButton;

    public NewCustomFieldView(Context context) {
        super(context);

        inflate(context, R.layout.new_custom_field_view, this);
        initializeViews();
    }

    public NewCustomFieldView(Context context, AttributeSet attrs) {
        super(context, attrs);

        inflate(context, R.layout.new_custom_field_view, this);
        initializeViews();
    }

    public NewCustomFieldView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        inflate(context, R.layout.new_custom_field_view, this);
        initializeViews();
    }

    private void initializeViews() {
        fieldNameEditText = findViewById(R.id.fieldNameEditText);
        fieldValueEditText = findViewById(R.id.fieldValueEditText);
        addFieldButton = findViewById(R.id.addFieldButton);
    }

    public String getFieldName() {
        return fieldNameEditText.getText().toString();
    }

    public String getFieldValue() {
        return fieldValueEditText.getText().toString();
    }

    public EditText getFieldNameEditText() {
        return fieldNameEditText;
    }

    public EditText getFieldValueEditText() {
        return fieldValueEditText;
    }

    public Button getAddFieldButton() {
        return addFieldButton;
    }

}
