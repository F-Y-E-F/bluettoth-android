package com.example.tictactoearduino

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var bluetoothAdapter: BluetoothAdapter? = null
    private lateinit var pairedDevices:Set<BluetoothDevice>


    companion object{
        const val REQUEST_ENABLE_BLUETOOTH = 1
        const val EXTRA_ADDRESS = "device_address"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if(bluetoothAdapter == null){
            return showSnack("Device not support bluetooth",refresh)
        }
        if(!bluetoothAdapter!!.isEnabled){
            requestBluetooth()
        }
    }

    private fun showSnack(text:String,view: View){
        Snackbar.make(view,text,Snackbar.LENGTH_SHORT).show()
    }
    private fun requestBluetooth(){
        val bluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        startActivityForResult(bluetoothIntent, REQUEST_ENABLE_BLUETOOTH)
    }


}