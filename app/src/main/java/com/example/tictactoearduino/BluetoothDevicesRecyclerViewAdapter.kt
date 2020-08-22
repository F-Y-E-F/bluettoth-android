package com.example.tictactoearduino

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class BluetoothDevicesRecyclerViewAdapter:RecyclerView.Adapter<BluetoothDevicesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BluetoothDevicesViewHolder {
        return BluetoothDevicesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.bluetooth_device_card,parent,false))
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: BluetoothDevicesViewHolder, position: Int) {

    }
}

