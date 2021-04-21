package com.example.bluetoothapp

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {

    /*private inner class ConnectThread(device: BluetoothDevice) : Thread() {

        private val mmSocket: BluetoothSocket? by lazy(LazyThreadSafetyMode.NONE) {
            device.createRfcommSocketToServiceRecord(UUID.fromString("dbbf3428-a69c-458b-85a8-5ca2de5b673c"))
        }

        public override fun run() {
            // Cancel discovery because it otherwise slows down the connection.
            bt?.cancelDiscovery()

            mmSocket?.use { socket ->
                // Connect to the remote device through the socket. This call blocks
                // until it succeeds or throws an exception.
                socket.connect()

                // The connection attempt succeeded. Perform work associated with
                // the connection in a separate thread.
                
            }
        }

        // Closes the client socket and causes the thread to finish.
        fun cancel() {
            try {
                mmSocket?.close()
            } catch (e: IOException) {
                Log.e("TAG", "Could not close the client socket", e)
            }
        }
    }*/

    lateinit var bt : BluetoothAdapter
    lateinit var btSock : BluetoothSocket
    /*private val receiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val action: String? = intent.action
            when (action) {
                BluetoothDevice.ACTION_FOUND -> {
                    // Discovery has found a device. Get the BluetoothDevice
                    // object and its info from the Intent.
                    val device: BluetoothDevice? =
                        intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    val deviceName = device?.name
                    val deviceHardwareAddress = device?.address // MAC address
                }
            }
        }
    }*/


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rvListeBluetooth = findViewById<RecyclerView>(R.id.rvListeBluetooth)
        val btn1 = findViewById<Button>(R.id.btn1)
        val tvTexte = findViewById<TextView>(R.id.tvTexte)
        bt = BluetoothAdapter.getDefaultAdapter()
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        //registerReceiver(receiver, filter)

        if (bt == null) {
            Toast.makeText(
                this,
                "Votre appareil n'est pas compatible avec le bluetooth",
                Toast.LENGTH_LONG
            ).show()
        }

        if (!bt.isEnabled) {
            if (bt.enable()) {
                Toast.makeText(this, "Bluetooth activé", Toast.LENGTH_LONG).show()
            }
        }
        var devices = bt.bondedDevices
        if (devices.size < 1) {
            Toast.makeText(this, "Aucun appareil Bluetooth détecté", Toast.LENGTH_SHORT).show()
        }


        rvListeBluetooth.layoutManager = LinearLayoutManager(this)
        rvListeBluetooth.adapter = BtAdapter(devices, tvTexte)

        btn1.setOnClickListener {
            if (bt.startDiscovery()) {
                Toast.makeText(this, "recherche de périphérique en cours", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Toast.makeText(this, "ça pique", Toast.LENGTH_SHORT).show()
            }
        }

        /*var compteur = true
        for(i in devices){
            if(compteur){
                btSock = i.createRfcommSocketToServiceRecord(UUID.fromString("dbbf3428-a69c-458b-85a8-5ca2de5b673c"))
                btSock.connect()
                compteur = false
            }
        }*/

        /*if (btSock.isConnected){
            var inputS = btSock.inputStream
            Toast.makeText(this, inputS.read(), Toast.LENGTH_SHORT).show()
        }*/




    }

    /*override fun onDestroy() {
        super.onDestroy()

        // Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(receiver)

    }*/
}