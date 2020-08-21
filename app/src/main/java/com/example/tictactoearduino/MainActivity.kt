package com.example.tictactoearduino

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
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

        refresh.setOnRefreshListener {
            getPairedDevicesList()
            refresh.isRefreshing = false
        }
    }

    private fun showSnack(text:String,view: View){
        Snackbar.make(view,text,Snackbar.LENGTH_SHORT).show()
    }
    private fun requestBluetooth(){
        val bluetoothIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        startActivityForResult(bluetoothIntent, REQUEST_ENABLE_BLUETOOTH)
        selectDeviceList.adapter = null
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
            if(requestCode == REQUEST_ENABLE_BLUETOOTH){
                if(resultCode == Activity.RESULT_OK){
                    if(bluetoothAdapter!!.isEnabled){
                        getPairedDevicesList()
                    }else showSnack("Bluetooth has been disabled", refresh)
                }else if(resultCode == Activity.RESULT_CANCELED){
                    showSnack("Bluetooth enabling has been canceled",refresh)
                }
            }
        }




    private fun getPairedDevicesList(){
        if(!bluetoothAdapter!!.isEnabled) requestBluetooth()
        else{
            pairedDevices = bluetoothAdapter!!.bondedDevices
            val listOfDevices = ArrayList<BluetoothDevice>()
            val listOfDevicesNames = ArrayList<String>()

            if(pairedDevices.isNotEmpty())
                for(device  in pairedDevices){
                    listOfDevices.add(device)
                    listOfDevicesNames.add(device.name)
                }
            else showSnack("Cannot find eny paired devices",refresh)

            val adapter = ArrayAdapter(applicationContext,android.R.layout.simple_list_item_1,listOfDevicesNames)

            selectDeviceList.adapter = adapter

            selectDeviceList.onItemClickListener = AdapterView.OnItemClickListener{ _, _, position, _->
                val intent = Intent(this,MainActivity::class.java)
                intent.putExtra(EXTRA_ADDRESS,listOfDevices[position].address)
                startActivity(intent)
            }


        }
    }
}