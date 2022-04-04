package it.polito.did.smartvase.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import it.polito.did.smartvase.MainActivity
import it.polito.did.smartvase.R

class Dashboard : Fragment() {

    companion object {
        fun newInstance() = Homepage()
    }

    private val viewModel: MainViewModel by activityViewModels<MainViewModel>()

//    private lateinit var auto : Switch
//    private lateinit var waterButton : Button
//    private lateinit var notificationButton : Button
//    private lateinit var offText : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val waterBar = view?.findViewById<ImageView>(R.id.waterBarFull)
        val soilMostureBar = view?.findViewById<ImageView>(R.id.soilMoisrtureBarFull)
        val plantName = view?.findViewById<TextView>(R.id.PlantName2)
        val plantIcon = view?.findViewById<TextView>(R.id.PlantIcon1)
        val offText = view?.findViewById<TextView>(R.id.offText)
        val waterButton = view?.findViewById<Button>(R.id.waterButton)
        val notificationButton = view?.findViewById<Button>(R.id.notificationButton)


        if (plantName != null) {
            plantName.text=viewModel.plantName
        }
            //TODO METTERE IMMAGINE PIANTA plantIcon=viewModel.plantIcon
        //waterBar.height=viewModel.waterLevel
        //soilMostureBar.height=viewModel.soilMoisture

        if(viewModel.auto) {
            if (offText != null) {
                offText.setText("OFF")
            }
        }
        else {
            //TODO TASTO WATER IN DENTRO
            if (offText != null) {
                offText.setText("ON")
            }
        }
        //if(viewModel.notification)
            //TODO STATE ICONA NOTIFICHE
        //todo peppinodicapri
        viewModel.auto=!viewModel.auto

        return inflater.inflate(R.layout.dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeButton = view.findViewById<ImageButton>(R.id.homeButton)
        val editButton = view.findViewById<ImageButton>(R.id.editButton)
        val waterButton = view.findViewById<FloatingActionButton>(R.id.autoWaterButton)
        val notificationButton = view.findViewById<ImageButton>(R.id.notificationButton)

//        homeButton.setOnClickListener { findNavController().navigate(R.id.action_dashboard_to_homepage) }
//        editButton.setOnClickListener { findNavController().navigate(R.id.action_dashboard_to_editPlant) } //ancora da capire come fare, se con altro fragment
//
//        waterButton.setOnLongClickListener{
//            viewModel.auto=true
//           // activity?.vibrate(25);
//            return@setOnLongClickListener true
//        }
//
//        notificationButton.setOnClickListener{
//            viewModel.notification=false
//        }
    }

}