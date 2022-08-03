package it.polito.did.smartvase.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.transition.TransitionInflater
import it.polito.did.smartvase.R

class Settings : Fragment() {

    companion object {
        fun newInstance() = Homepage()
    }

    private val viewModel: MainViewModel by activityViewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /*val back = view.findViewById<Button>(R.id.back_button)

        back.setOnClickListener { findNavController().navigate(R.id.action_editPlant_to_homepage) }*/
    }
}