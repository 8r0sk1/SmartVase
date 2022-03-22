package it.polito.did.smartvase.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import it.polito.did.smartvase.MainActivity
import it.polito.did.smartvase.R
import org.w3c.dom.Text

class Dashboard : Fragment() {

    companion object {
        fun newInstance() = Homepage()
    }

    private val viewModel: MainViewModel by activityViewModels<MainViewModel>()

    private var auto : Switch
    private var waterButton : Button
    private var notificationButton : Button
    private var offText : TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val waterBar = view.findViewById<ImageView>(R.id.waterBarFiller)
        val soilMostureBar = view.findViewById<ImageView>(R.id.soilMoistureBarFiller)
        val plantName = view.findViewById<TextView>(R.id.cardPlantName2)
        val plantIcon = view.findViewById<TextView>(R.id.cardPlantIcon2)
        offText = view.findViewById<TextView>(R.id.offText)
        waterButton = view.findViewById<Button>(R.id.waterButton)
        notificationButton = view.findViewById<Button>(R.id.notificationButton)

        plantName.text=viewModel.plantName
        //TODO METTERE IMMAGINE PIANTA plantIcon=viewModel.plantIcon
        waterBar.height=viewModel.waterLevel
        soilMostureBar.height=viewModel.soilMoisture

        if(viewModel.auto) {
            offText.text = "OFF"
        }
        else {
            //TODO TASTO WATER IN DENTRO
            offText.text = "ON"
        }
        if(viewModel.notification)
            //TODO STATE ICONA NOTIFICHE
        //todo peppinodicapri
        auto.setChecked(viewModel.auto)

        return inflater.inflate(R.layout.plant_setup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeButton = view.findViewById<Button>(R.id.homeButton)
        val editButton = view.findViewById<Button>(R.id.editButton)

        homeButton.setOnClickListener { findNavController().navigate(R.id.action_dashboard_to_homepage) }
        editButton.setOnClickListener { findNavController().navigate(R.id.action_dashboard_to_editPlant) } //ancora da capire come fare, se con altro fragment

        waterButton.setOnLongClickListener{
            viewModel.auto=true
            activity?.vibrate(25);
            return@setOnLongClickListener true
        }

        notificationButton.setOnClickListener{
            viewModel.notification=false
        }
    }

}