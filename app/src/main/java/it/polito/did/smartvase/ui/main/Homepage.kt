package it.polito.did.smartvase.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Switch
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import it.polito.did.smartvase.R

class Homepage : Fragment() {

    companion object {
        fun newInstance() = Homepage()
    }

    private val viewModel: MainViewModel by activityViewModels<MainViewModel>()
    private lateinit var auto : Switch

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       // auto = view?.findViewById<Switch>(R.id.autoSwitch1) ?:
       // auto.setChecked(viewModel.auto)

        return inflater.inflate(R.layout.homepage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val addPlant = view.findViewById<FloatingActionButton>(R.id.addPlantButton)
        val settings = view.findViewById<ImageButton>(R.id.profileButton)
        val dashboard = view.findViewById<ImageView>(R.id.cardPlantIcon1) //per ora il tasto per aprire la dashboard è l'immagine pianta



        addPlant.setOnClickListener { findNavController().navigate(R.id.action_homepage_to_plantSetup) }
        settings.setOnClickListener { findNavController().navigate(R.id.action_homepage_to_settings) }
        dashboard.setOnClickListener { findNavController().navigate(R.id.action_homepage_to_dashboard) }

        auto.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.auto = isChecked

        }

    }
}