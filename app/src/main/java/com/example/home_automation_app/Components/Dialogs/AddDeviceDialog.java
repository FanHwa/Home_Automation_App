package com.example.home_automation_app.Components.Dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.home_automation_app.R;
import com.google.android.material.textfield.TextInputLayout;

public class AddDeviceDialog extends DialogFragment {
    // Widgets
    private TextInputLayout nameLayout, typeLayout, locationLayout, onCmdLayout, offCmdLayout;
    private EditText deviceName, deviceLocation, deviceOnCmd, deviceOffCmd;
    private AutoCompleteTextView deviceType;


    public AddDeviceDialog.AddDeviceListener mListener;

    public AddDeviceDialog(){};

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.CurvedDialogBox);

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_add_edit_device, null);

        // Call widgets
        nameLayout = view.findViewById(R.id.input_layout_device_name);
//        typeLayout = view.findViewById(R.id.input_layout_device_type);
        locationLayout = view.findViewById(R.id.input_layout_device_location);
        onCmdLayout = view.findViewById(R.id.input_layout_device_onCmd);
        offCmdLayout = view.findViewById(R.id.input_layout_device_offCmd);

        deviceName = view.findViewById(R.id.device_name_field);
//        deviceType = view.findViewById(R.id.device_type_field);
        deviceLocation = view.findViewById(R.id.device_location_field);
        deviceOnCmd = view.findViewById(R.id.device_onCmd_field);
        deviceOffCmd = view.findViewById(R.id.device_offCmd_field);

        builder.setCancelable(false);
        builder.setView(view)
                .setPositiveButton("Confirm", (dialog, which) -> {
                    String name = deviceName.getText().toString();
                    String type = deviceType.getText().toString();
                    String location = deviceLocation.getText().toString();
                    String onCmd = deviceOnCmd.getText().toString();
                    String offCmd = deviceOffCmd.getText().toString();

                    if(checkInputValid(name, location, onCmd, offCmd)){
                        mListener.onAddDeviceConfirm(name, type, location, onCmd, offCmd);
                    } else {

                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    AddDeviceDialog.this.getDialog().dismiss();
                });



        //Create AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try{
            mListener = (AddDeviceDialog.AddDeviceListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement AddDeviceListener");
        }
    }

    public interface AddDeviceListener {
        void onAddDeviceConfirm(String name, String type, String location, String onCmd, String offCmd);
    }

    private boolean checkInputValid(String name, String location, String onCmd, String offCmd){
        boolean valid = true;
        if(name.isEmpty()){
            valid = false;
            nameLayout.setError("This field cannot empty");
        }

        return valid;
    }
}
