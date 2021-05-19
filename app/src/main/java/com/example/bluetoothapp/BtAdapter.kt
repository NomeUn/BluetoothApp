package com.example.bluetoothapp

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket

import android.graphics.Color
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.StringWriter
import java.lang.Exception
import java.util.*

class BtAdapter(
    var listeAppareils: MutableSet<BluetoothDevice>,
    var tvTexte : TextView
) : RecyclerView.Adapter<BtAdapter.BtViewHolder>() {


    lateinit var socket: BluetoothSocket
    var liste: MutableList<BluetoothDevice> = mutableListOf()

    inner class BtViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BtViewHolder {
        for (i in listeAppareils){
            liste.add(i)
        }
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bt, parent, false)
        return BtViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: BtViewHolder, position: Int) {
        holder.itemView.apply {
            if (position%2 == 0){
                this.setBackgroundColor(Color.LTGRAY)
            }
            else{
                this.setBackgroundColor(Color.WHITE)
            }

            findViewById<TextView>(R.id.tvBt).text = liste[position].name
            this.setOnClickListener {
                try {
                    socket = liste[position].createInsecureRfcommSocketToServiceRecord(liste[position].uuids[0].uuid)
                    socket.connect()
                    Toast.makeText(context, socket.isConnected.toString(), Toast.LENGTH_SHORT).show()
                    Toast.makeText(context, socket.connectionType.toString(), Toast.LENGTH_SHORT).show()

                    var inputS = socket.inputStream
                    var outputStream = socket.outputStream

                    outputStream.write("test".toByteArray())
                    Toast.makeText(context, "message émis", Toast.LENGTH_SHORT).show()

                    //Toast.makeText(context, inputS.read().toString(), Toast.LENGTH_SHORT).show()
                    GlobalScope.launch {
                        var buffer = ByteArray(256)
                        var bytes:Int
                        var msg = ""
                        while (true){
                            try {
                                bytes = inputS.read(buffer)
                                var temp = String(buffer,0, bytes)
                                msg += temp
                                if(msg.length > 10){
                                    Log.d("bt", msg)
                                    tvTexte.setText(msg)
                                    msg = ""
                                }


                                //Toast.makeText(context, msg,Toast.LENGTH_SHORT).show()
                            }
                            catch (e : Exception){
                                //Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
                catch (e:IOException){
                    Toast.makeText(context, "Impossible d'établir une connexion : ${e.message}", Toast.LENGTH_LONG).show()
                }

                /*var c = inputS.read().toChar()
                var chaine = ""
                Toast.makeText(context, c.toString(), Toast.LENGTH_SHORT).show()
                */
            }
        }
    }

    override fun getItemCount(): Int {
        return listeAppareils.size
    }
}