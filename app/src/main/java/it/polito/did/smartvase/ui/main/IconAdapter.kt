package it.polito.did.smartvase.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import it.polito.did.smartvase.R

class IconAdapter(val data:List<PlantIcon>):
    RecyclerView.Adapter< IconAdapter.IconViewHolder >()
{
    override fun getItemCount() = data.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconViewHolder {
        val v=LayoutInflater.from(parent.context)
            .inflate(R.layout.plant_icon, parent, false)
        return IconViewHolder(v)
    }

    override fun onBindViewHolder(holder:IconViewHolder, position: Int) {
        val u = data[position] //access data item
// l'holder si occuperà di usare i dati
        holder.bind(u);
    }

    class IconViewHolder(v:View):
        RecyclerView.ViewHolder(v) {
        val icon: ImageView = v.findViewById(R.id.plantIcon5)
        fun bind(p: PlantIcon) {
            icon?.setImageResource(p.iconId)
        }}


}