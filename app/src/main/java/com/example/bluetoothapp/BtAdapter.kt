package com.example.bluetoothapp

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class BtAdapter(
    var listeAppareils: MutableSet<BluetoothDevice>
) : RecyclerView.Adapter<BtAdapter.BtViewHolder>(){



    var liste: MutableList<BluetoothDevice> = mutableListOf()

    inner class BtViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BtViewHolder {
        for (i in listeAppareils){
            liste.add(i)
        }
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_bt, parent, false)
        return BtViewHolder(view)
    }

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

            }
        }
    }

    override fun getItemCount(): Int {
        return listeAppareils.size
    }
}