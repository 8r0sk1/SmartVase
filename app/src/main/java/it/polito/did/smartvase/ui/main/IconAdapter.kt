package it.polito.did.smartvase.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import it.polito.did.smartvase.R

class IconAdapter(val data:List<PlantIcon>,val parentFragment: IconListFragment): RecyclerView.Adapter< IconAdapter.IconViewHolder >() {

   /* var defaultMax: Float = 1f
    var defaultMin: Float = 1f
    var type: String = ""
    var icon: Int = 2
    var setupSetted: Boolean = true
    var selected: Boolean = false*/

    override fun getItemCount() = data.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconViewHolder {
        val v=LayoutInflater.from(parent.context)
            .inflate(R.layout.plant_icon, parent, false)
        return IconViewHolder(v)
    }

    override fun onBindViewHolder(holder:IconViewHolder, position: Int) {
        val u = data[position] //access data item
        val p: PlantIcon=data[0]
        // l'holder si occuperà di usare i dati
        holder.bind(u)
        holder.plantSelection.setOnClickListener{
            p.defaultMax=holder.defaultMax.text.toString().toInt()
            p.defaultMin=holder.defaultMin.text.toString().toInt()
            p.type=holder.type.text.toString()
            p.iconId= holder.iconId
            parentFragment.setMvm(p) //graziesssignore
        }
    }

    class IconViewHolder(v:View):
        RecyclerView.ViewHolder(v) {
        val plantSelection: RelativeLayout = v.findViewById(R.id.plantSelection5)
        val icon: ImageView = v.findViewById(R.id.plantIcon5)
        val type: TextView = v.findViewById(R.id.Type5)
        val defaultMax : TextView = v.findViewById(R.id.defaultMax5)
        val defaultMin : TextView = v.findViewById(R.id.defaultMin5)
        var iconId: Int =0

        fun bind(p: PlantIcon) {
            icon.setImageResource(p.iconId)
            type.setText(p.type)
            defaultMax.setText(p.defaultMax.toString())
            defaultMin.setText(p.defaultMin.toString())
            iconId=p.iconId
        }
    }
}