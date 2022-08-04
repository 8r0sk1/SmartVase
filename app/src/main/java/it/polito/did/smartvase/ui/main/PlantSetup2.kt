package it.polito.did.smartvase.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import it.polito.did.smartvase.R

class PlantSetup2 : Fragment() {

    /*companion object {
        fun newInstance() = Homepage()
    }*/

    private val viewModel: MainViewModel by activityViewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.fade)
        exitTransition = inflater.inflateTransition(R.transition.fade)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.plant_setup2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val plantName = view.findViewById<TextView>(R.id.plantName32)
        val plantIcon = view.findViewById<ImageView>(R.id.plantIcon32)
        val maxText = view.findViewById<TextView>(R.id.maxText32)
        val minText = view.findViewById<TextView>(R.id.minText32)
        val dividerMaxSoilMoisture = view.findViewById<ProgressBar>(R.id.dividerMaxSoilMoisture32)
        val dividerMinSoilMoisture = view.findViewById<ProgressBar>(R.id.dividerMinSoilMoisture32)
        val arrowMax = view.findViewById<ImageView>(R.id.arrowMax32)
        val arrowMin = view.findViewById<ImageView>(R.id.arrowMin32)

        //caricamento default
        dividerMaxSoilMoisture?.updateLayoutParams<ConstraintLayout.LayoutParams> { verticalBias = 1-viewModel.defaultMax }
        arrowMax?.updateLayoutParams<ConstraintLayout.LayoutParams> { verticalBias = 1-viewModel.defaultMax }
        maxText?.updateLayoutParams<ConstraintLayout.LayoutParams> { verticalBias = 1-viewModel.defaultMax }
        maxText.setText(viewModel.defaultMax.toString()+" %")

        dividerMinSoilMoisture?.updateLayoutParams<ConstraintLayout.LayoutParams> { verticalBias = 1-viewModel.defaultMin  }
        arrowMin?.updateLayoutParams<ConstraintLayout.LayoutParams> { verticalBias = 1-viewModel.defaultMin  }
        minText?.updateLayoutParams<ConstraintLayout.LayoutParams> { verticalBias = 1-viewModel.defaultMax }
        minText.setText(viewModel.defaultMin.toString()+" %")

        //TODO trascinando col dito si spostano i divisori e si aggiornano testo e arrow (on change qualcosa)


        val back = view.findViewById<Button>(R.id.back_button32)
        val next = view.findViewById<Button>(R.id.next_button32)

        plantIcon.setOnClickListener{
            //TODO situazione
        }

        back.setOnClickListener { findNavController().navigate(R.id.action_plantSetup2_to_plantSetup) }
        next.setOnClickListener {
            findNavController().navigate(R.id.action_plantSetup2_to_dashboard) }
    }
}