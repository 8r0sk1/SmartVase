package it.polito.did.smartvase.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import it.polito.did.smartvase.MainActivity
import it.polito.did.smartvase.R

class Dashboard : Fragment() {

    companion object {
        fun newInstance() = Homepage()
    }

    private val viewModel: MainViewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.plant_setup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val homeButton = view.findViewById<Button>(R.id.homeButton)
        val editButton = view.findViewById<Button>(R.id.editButton)
        val waterButton = view.findViewById<Button>(R.id.waterButton)
        val notificationButton = view.findViewById<Button>(R.id.notificationButton)

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