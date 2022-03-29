package com.example.home_automation_app.Components.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.home_automation_app.Models.Device;
import com.example.home_automation_app.R;

import java.util.ArrayList;

public class DeviceCardAdapter extends RecyclerView.Adapter<DeviceCardAdapter.ViewHolder> {
    private ArrayList<Device> deviceArrayList;
    private OnItemClickListener mItemListener;
    private OnBtnClickListener mOnListener;
    private OffBtnClickListener mOffListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        mItemListener = itemClickListener;
    }

    public interface OnBtnClickListener{
        void onBtnClick(int position);
    }

    public void setOnBtnClick(OnBtnClickListener onListener) {
        mOnListener = onListener;
    }

    public interface OffBtnClickListener{
        void offBtnClick(int position);
    }

    public void setOffBtnClick(OffBtnClickListener offListener){
        mOffListener = offListener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public Button onBtn, offBtn;
        public TextView nameView, locationView;

        public ViewHolder(View itemView, final OnItemClickListener itemListener,
                          final OnBtnClickListener onListener,
                          final OffBtnClickListener offListener) {
            super(itemView);

            nameView = itemView.findViewById(R.id.card_device_name);
            locationView = itemView.findViewById(R.id.card_device_location);
            onBtn = itemView.findViewById(R.id.card_device_on_btn);
            offBtn = itemView.findViewById(R.id.card_device_off_btn);

            itemView.setOnClickListener(v -> {
                if (itemListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        itemListener.onItemClick(position);
                    }
                }
            });

            onBtn.setOnClickListener(v -> {
                if (onListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        onListener.onBtnClick(position);
                    }
                }
            });

            offBtn.setOnClickListener(v -> {
                if (offListener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        offListener.offBtnClick(position);
                    }
                }
            });
        }
    }

    public DeviceCardAdapter(ArrayList<Device> deviceArrayList) {
        this.deviceArrayList = deviceArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_device, parent, false);
        ViewHolder viewHolder = new ViewHolder(v, mItemListener, mOnListener, mOffListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceCardAdapter.ViewHolder holder, int position) {
        Device device = deviceArrayList.get(position);

        holder.nameView.setText(device.getDeviceName());
        holder.locationView.setText(device.getDeviceLocation());
    }

    @Override
    public int getItemCount() {
        return deviceArrayList.size();
    }


}
