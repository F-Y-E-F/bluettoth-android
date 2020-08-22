package com.example.tictactoearduino

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.tictactoearduino.MainActivity.Companion.EXTRA_ADDRESS

class BluetoothDevicesRecyclerViewAdapter(private val listOfDevices:ArrayList<BluetoothDevice>,private val context: Context):RecyclerView.Adapter<BluetoothDevicesViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BluetoothDevicesViewHolder {
        return BluetoothDevicesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.bluetooth_device_card,parent,false))
    }

    override fun getItemCount(): Int {
        return listOfDevices.size
    }

    override fun onBindViewHolder(holder: BluetoothDevicesViewHolder, position: Int) {
        holder.deviceName.text = listOfDevices[holder.adapterPosition].name
        holder.deviceAddress.text = listOfDevices[holder.adapterPosition].address

        //g oto play game screen
        holder.parseButton.setOnClickListener {
            val intent = Intent(context,PlayGame::class.java)
            intent.putExtra(EXTRA_ADDRESS,listOfDevices[position].address)
            context.startActivity(intent)
        }

    }
}

