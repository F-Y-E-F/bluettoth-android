package com.example.tictactoearduino

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class BluetoothDevicesRecyclerViewAdapter:RecyclerView.Adapter<BluetoothDevicesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BluetoothDevicesViewHolder {
        return LayoutInflater.from(parent.context).inflate(R.layout.,parent,false)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: BluetoothDevicesViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}

