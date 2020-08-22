package com.example.tictactoearduino

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.bluetooth_device_card.view.*

class BluetoothDevicesViewHolder(v: View): RecyclerView.ViewHolder(v){
    val deviceName = v.deviceNameTV!!
    val deviceAddress = v.deviceAddressTV!!
    val parseButton = v.parseButton!!
}