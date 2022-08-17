package it.polito.did.smartvase.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import it.polito.did.smartvase.R
import com.google.android.material.snackbar.Snackbar

class WifiSetup : Fragment() {

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

        val browserButton = view.findViewById<Button>(R.id.browserButton)
        val back = view.findViewById<Button>(R.id.back_button6)
        val next = view.findViewById<Button>(R.id.next_button6)

        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"))

        browserButton.setOnClickListener { startActivity(browserIntent) }

        back.setOnClickListener { findNavController().navigate(R.id.action_wifisetup_to_homepage) }

        //TODO AUTOMATICAMENTE A PAGINA SUCCESSIVA
        while(true) {
            next.setOnClickListener {
                if (false /*TODO CONTROLLO DATABASE VLADDD*/) {
                    val snack =
                        Snackbar.make(it, "Open Browser and connect to WiFi", Snackbar.LENGTH_SHORT)
                    snack.show()
                } else {
                    findNavController().navigate(R.id.action_wifisetup_to_plantsetup)
                }
            }
        }
    }
}
