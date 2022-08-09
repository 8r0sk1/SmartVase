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
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import it.polito.did.smartvase.R

class Settings : Fragment() {

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
        return inflater.inflate(R.layout.settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val plantName = view.findViewById<TextView>(R.id.plantName4)
        val plantIcon = view.findViewById<ImageView>(R.id.plantIcon4)
        val maxText = view.findViewById<TextView>(R.id.maxText4)
        val minText = view.findViewById<TextView>(R.id.minText4)
        val dividerMaxSoilMoisture = view.findViewById<ProgressBar>(R.id.dividerMaxSoilMoisture4)
        val dividerMinSoilMoisture = view.findViewById<ProgressBar>(R.id.dividerMinSoilMoisture4)
        val arrowMax = view.findViewById<ImageView>(R.id.arrowMax4)
        val arrowMin = view.findViewById<ImageView>(R.id.arrowMin4)

        val tmpMVM = viewModel


        /*
        //caricamento default
        dividerMaxSoilMoisture?.updateLayoutParams<ConstraintLayout.LayoutParams> { verticalBias = 1-viewModel.defaultMax }
        arrowMax?.updateLayoutParams<ConstraintLayout.LayoutParams> { verticalBias = 1-viewModel.defaultMax }
        maxText?.updateLayoutParams<ConstraintLayout.LayoutParams> { verticalBias = 1-viewModel.defaultMax }
        maxText.setText(viewModel.defaultMax.toString()+" %")

        dividerMinSoilMoisture?.updateLayoutParams<ConstraintLayout.LayoutParams> { verticalBias = 1-viewModel.defaultMin  }
        arrowMin?.updateLayoutParams<ConstraintLayout.LayoutParams> { verticalBias = 1-viewModel.defaultMin  }
        minText?.updateLayoutParams<ConstraintLayout.LayoutParams> { verticalBias = 1-viewModel.defaultMax }
        minText.setText(viewModel.defaultMin.toString()+" %")
        */

        //TODO trascinando col dito si spostano i divisori e si aggiornano testo e arrow (on change qualcosa)


        val back = view.findViewById<Button>(R.id.back_button4)
        val next = view.findViewById<Button>(R.id.next_button4)

        plantIcon.setOnClickListener{
            //TODO situazione
        }

        back.setOnClickListener {
            findNavController().navigate(R.id.action_settings_to_dashboard) }
        next.setOnClickListener {
            viewModel.reset()
            findNavController().navigate(R.id.action_settings_to_dashboard) }
    }
}