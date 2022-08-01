package it.polito.did. smartvase.ui.main

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Vibrator
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.view.marginTop
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import it.polito.did.smartvase.R

class Dashboard : Fragment() {

    companion object {
        fun newInstance() = Homepage()
    }

    private val viewModel: MainViewModel by activityViewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide)
    }

    /*override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val plantIcon = view?.findViewById<TextView>(R.id.PlantIcon)

        val waterBar = view?.findViewById<ImageView>(R.id.waterBarFull)
        val soilMostureBar = view?.findViewById<ImageView>(R.id.soilMoisrtureBarFull)
        val plantName = view?.findViewById<TextView>(R.id.PlantName2)
        val plantIcon = view?.findViewById<TextView>(R.id.PlantIcon)
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
    }*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var waterBar = view?.findViewById<CardView>(R.id.waterLevelBar)
        var soilMostureBar = view?.findViewById<CardView>(R.id.soilMoistureBar)
        val plantName = view?.findViewById<TextView>(R.id.PlantName)
        val plantIcon = view?.findViewById<ImageView>(R.id.PlantIcon)
        val offText = view?.findViewById<TextView>(R.id.offText)
        val homeButton = view.findViewById<ImageButton>(R.id.homeButton)
        val editButton = view.findViewById<ImageButton>(R.id.editButton)
        val waterButton = view.findViewById<FloatingActionButton>(R.id.autoWaterButton)
        val notificationButton = view.findViewById<ImageButton>(R.id.notificationButton)

        val db = Firebase.database.reference
        val ref = db.child("A7/toWaterControl")

        //popolamento viste
        var fillWater :Float=385-(3.85f * viewModel.waterLevel)
        var fillSoil :Float =385-(3.85f * viewModel.soilMoisture)
        waterBar?.marginTop ?: fillWater
        soilMostureBar?.marginTop ?: fillSoil
        if(viewModel.auto){
            offText?.text="ON"
            offText?.setTextColor(Color.RED)
        }


        waterButton.setOnClickListener {
            ref.setValue(1)
        }


        homeButton.setOnClickListener { findNavController().navigate(R.id.action_dashboard_to_homepage) }
        editButton.setOnClickListener { findNavController().navigate(R.id.action_dashboard_to_editPlant) } //ancora da capire come fare, se con altro fragment

        waterButton.setOnLongClickListener{
            viewModel.auto=true
            // activity?.vibrate(25);
            return@setOnLongClickListener true
        }

        notificationButton.setOnClickListener{
            viewModel.notification=false
        }
    }

}