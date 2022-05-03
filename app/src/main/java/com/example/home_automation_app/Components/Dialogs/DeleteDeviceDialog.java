package com.example.home_automation_app.Components.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.home_automation_app.R;

/**
 * This is a Delete device dialog class
 */
public class DeleteDeviceDialog extends DialogFragment {
    private DeleteDeviceListener mListener;
    private int deviceId;

    /**
     * Constructor for DeleteDeviceDialog
     * @param deviceId Device Id
     */
    public DeleteDeviceDialog(int deviceId) {
        this.deviceId = deviceId;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(String.format("Are you sure you want to delete this device?"))
                .setPositiveButton("Confirm", (dialog, id) -> mListener.onConfirmDeleteDevice(deviceId))
                .setNegativeButton("Cancel", (dialog, id) -> DeleteDeviceDialog.this.getDialog().cancel());
        // Create the AlertDialog object and return it
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mListener = (DeleteDeviceDialog.DeleteDeviceListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement DeleteDeviceListener");
        }
    }

    public interface DeleteDeviceListener{
        void onConfirmDeleteDevice(int deviceId);
    }
}
