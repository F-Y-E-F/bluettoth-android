package com.example.tictactoearduino

import android.bluetooth.BluetoothAdapter
import android.content.Context
import android.os.AsyncTask
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import dmax.dialog.SpotsDialog
import java.io.IOException

class ConnectToDevice(private val context: Context):AsyncTask<Void,Void,String>() {
    private val dialog = SpotsDialog.Builder().setContext(context).setTheme(R.style.dialogTheme).setMessage("Connecting..").build()!!

    override fun onPreExecute() {
        super.onPreExecute()
        dialog.apply {
            show()//show loading dialog
        }
    }

    //create bluetooth socket and connect to device
    override fun doInBackground(vararg p0: Void?): String {
        try{
            if(PlayGame.bluetoothSocket == null){
                PlayGame.bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                val device  = PlayGame.bluetoothAdapter.getRemoteDevice(PlayGame.address)
                PlayGame.bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(
                    PlayGame.uid
                )
                BluetoothAdapter.getDefaultAdapter().cancelDiscovery()
                PlayGame.bluetoothSocket!!.connect()
            }
        }catch (e: IOException){
            this.cancel(true)
            toast("${e.message}")
            e.printStackTrace()
        }
        return ""
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)
        //dismiss loading dialog
        dialog.dismiss()
    }
    private fun toast(s: String) = Toast.makeText(context,s, Toast.LENGTH_SHORT).show()
}