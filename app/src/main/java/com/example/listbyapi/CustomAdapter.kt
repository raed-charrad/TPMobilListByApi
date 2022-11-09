package com.example.listbyapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Response

internal class CustomAdapter(private var data: MutableList<Offre>): RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {
    internal inner class MyViewHolder (view : View) : RecyclerView.ViewHolder(view) {
        var intitule :TextView = view.findViewById(R.id.intitulé)
        var specialite : TextView = view.findViewById(R.id.specialité)
        var sosiete : TextView = view.findViewById(R.id.société)
        var nbpostes : TextView = view.findViewById(R.id.nbpostes)
        var pays : TextView = view.findViewById(R.id.pays)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.line,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val ite= data[position]
        holder.intitule.text= ite.intitulé
        holder.pays.text=ite.pays
        holder.specialite.text=ite.specialité
        holder.nbpostes.text= ite.nbpostes.toString()
        holder.sosiete.text=ite.société
    }

    override fun getItemCount(): Int {
        return data.size
    }
}