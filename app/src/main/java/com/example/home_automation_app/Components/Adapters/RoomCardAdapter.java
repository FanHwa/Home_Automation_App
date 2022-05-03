package com.example.home_automation_app.Components.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.home_automation_app.Activities.HomeActivity;
import com.example.home_automation_app.DatabaseHelper.MyDBHelper;
import com.example.home_automation_app.R;

import java.util.ArrayList;

/**
 * This is an adapter class to set up the view of room item card
 */
public class RoomCardAdapter extends RecyclerView.Adapter<RoomCardAdapter.ViewHolder> {
    private ArrayList<String> roomsArrayList;
    private OnItemClickListener mListener;
    private MyDBHelper dbHelper;

    /**
     * Interface when the item card on click
     */
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    /**
     * Set on click listener for the item card
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mListener = onItemClickListener;
    }

    /**
     * Constructor for Room Card Adapter
     * @param roomsArrayList Room Array List
     * @param dbHelper Database helper
     */
    public RoomCardAdapter(ArrayList<String> roomsArrayList, MyDBHelper dbHelper) {
        this.roomsArrayList = roomsArrayList;
        this.dbHelper = dbHelper;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView roomName, deviceQty;

        public ViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            roomName = itemView.findViewById(R.id.card_room_name);
            deviceQty = itemView.findViewById(R.id.card_room_device_qty);

            // Set on click listener when item card click
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_room,
                parent, false);
        ViewHolder viewHolder = new ViewHolder(v, mListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RoomCardAdapter.ViewHolder holder, int position) {
        String tempRoomString = roomsArrayList.get(position);
        holder.roomName.setText(tempRoomString);

        String numDevicesString = dbHelper.getNumOfDevicesByLocation(tempRoomString) + " devices";
        holder.deviceQty.setText(numDevicesString);

    }

    @Override
    public int getItemCount() {
        return roomsArrayList.size();
    }
}
