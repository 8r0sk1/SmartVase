package it.polito.did.smartvase.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import it.polito.did.smartvase.R
import android.util.Log
import android.widget.*

class Homepage : Fragment(R.layout.homepage) {

    private val viewModel: MainViewModel by activityViewModels<MainViewModel>()
    //private lateinit var auto : Switch


    companion object {
        fun newInstance() = Homepage()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("HOMEPAGE","creating")
       // auto = view?.findViewById<Switch>(R.id.autoSwitch1) ?:
       // auto.setChecked(viewModel.auto)
        return inflater.inflate(R.layout.homepage, container, false)
    }
//42
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("HOMEPAGE","created")
        super.onViewCreated(view, savedInstanceState)

    val addPlant = view.findViewById<Button>(R.id.addPlantButton2)
    val settings = view.findViewById<Button>(R.id.profileButton2)
    val dashboard = view.findViewById<Button>(R.id.cardWaterLevel) //TODO capire cosa premere per aprire dash
    val auto = view.findViewById<Switch>(R.id.autoSwitch2)
    var checked = false

    val txtV = view.findViewById<TextView>(R.id.yourPlantText2)
    val db = Firebase.database.reference
    val ref = db.child("chiave")

    ref.addValueEventListener(object: ValueEventListener{
        override fun onDataChange(snapshot: DataSnapshot) {
            txtV.text = snapshot.getValue<String>()
        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }
    })

    addPlant.setOnClickListener { findNavController().navigate(R.id.action_homepage_to_plantSetup) }
    settings.setOnClickListener {
        Log.d("MAMMAZZO", "TAMMAZZO")
        findNavController().navigate(R.id.action_homepage_to_settings)
    }
    //settings.setOnClickListener { findNavController().navigate(R.id.action_homepage_to_dashboard) } //TODO questo serve ad andare veloce a dashboard
    dashboard.setOnClickListener { findNavController().navigate(R.id.action_homepage_to_dashboard) }

    /*auto.setOnClickListener{ //BARARE LO SWITCH
        checked = !checked
        viewModel.auto = checked
    }*/
    auto.setOnCheckedChangeListener { _, isChecked ->
        viewModel.auto = isChecked
    }


        /*

        val addPlant = view.findViewById<FloatingActionButton>(R.id.addPlantButton2)
        val settings = view.findViewById<ImageButton>(R.id.profileButton2)
        val dashboard = view.findViewById<ImageView>(R.id.cardWaterLevel) //TODO capire cosa premere per aprire dash
        //val auto = view.findViewById<Switch>(R.id.autoSwitch2)

        val txtV = view.findViewById<TextView>(R.id.yourPlantText2)
        val db = Firebase.database.reference
        val ref = db.child("chiave")

        ref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                txtV.text = snapshot.getValue<String>()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        addPlant.setOnClickListener { findNavController().navigate(R.id.action_homepage_to_plantSetup) }
        //settings.setOnClickListener { findNavController().navigate(R.id.action_homepage_to_settings) }
//        settings.setOnClickListener { findNavController().navigate(R.id.action_homepage_to_dashboard) } //TODO questo serve ad andare veloce a dashboard
//        dashboard.setOnClickListener { findNavController().navigate(R.id.action_homepage_to_dashboard) }

//        auto.setOnCheckedChangeListener { _, isChecked ->
//            viewModel.auto = isChecked
//        }
//        auto.setOnClickListener {
//            viewModel.auto = true
//        }

         */
    }
}