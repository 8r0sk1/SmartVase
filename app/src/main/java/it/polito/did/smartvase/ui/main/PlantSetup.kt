package it.polito.did.smartvase.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import it.polito.did.smartvase.R
import android.util.Log

class PlantSetup : Fragment() {

    /*companion object {
        fun newInstance() = Homepage()
    }*/

    private val viewModel: MainViewModel by activityViewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide)
        exitTransition = inflater.inflateTransition(R.transition.fade)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.plant_setup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val plantName = view.findViewById<TextView>(R.id.plantName3)
        val plantIcon = view.findViewById<ImageView>(R.id.plantIcon32)

        val back = view.findViewById<Button>(R.id.back_button32)
        val next = view.findViewById<Button>(R.id.next_button32)

        //debugssssssssssssss
        var setted:Boolean=true
        var iconId:Int=R.mipmap.ic_launcher
        plantIcon.setOnClickListener{
            //TODO situazione
            plantIcon.setImageResource(iconId)
        }

        plantIcon.setOnClickListener{ findNavController().navigate(R.id.action_plantSetup_to_iconListFragment) }
        back.setOnClickListener { findNavController().navigate(R.id.action_plantSetup_to_homepage) }
        next.setOnClickListener {
            if (!setted)
                //TODO MESSAGGIO completare cosetta
            else {
                viewModel.defaultMax = .55f //TODO PRENDERE I VALORI DEFAULT dal tipo pianta
                viewModel.defaultMin = .25f
                viewModel.plantIconId=iconId
                viewModel.plantName = plantName.text.toString()
                findNavController().navigate(R.id.action_plantSetup_to_plantSetup2)
            }
        }
    }
}
