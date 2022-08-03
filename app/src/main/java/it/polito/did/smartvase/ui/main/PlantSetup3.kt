package it.polito.did.smartvase.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import it.polito.did.smartvase.R

class PlantSetup3 : Fragment() {

    companion object {
        fun newInstance() = Homepage()
    }

    private val viewModel: MainViewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.plant_setup3, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val back = view.findViewById<Button>(R.id.back_button32)
        val next = view.findViewById<Button>(R.id.next_button32)

        back.setOnClickListener { findNavController().navigate(R.id.action_plantSetup3_to_plantSetup2) }
        next.setOnClickListener { findNavController().navigate(R.id.action_plantSetup3_to_dashboard) }
    }
}