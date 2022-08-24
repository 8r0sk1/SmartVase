package it.polito.did.smartvase.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
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
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

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

    @SuppressLint("ResourceType")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val plantName = view.findViewById<TextView>(R.id.plantName3)
        val plantIcon = view.findViewById<FloatingActionButton>(R.id.plantIcon3)
        val avatar = view.findViewById<ImageView>(R.id.avatar3)

        val back = view.findViewById<Button>(R.id.back_button3)
        val next = view.findViewById<Button>(R.id.next_button3)

        val tmpMVM = viewModel

        avatar.setImageResource(tmpMVM.plantIconId)
        plantName.setText(tmpMVM.plantName)

        plantIcon.setOnClickListener{
            findNavController().navigate(R.id.action_plantSetup_to_iconListFragment)
        }
        back.setOnClickListener {
            viewModel.setMvm(tmpMVM)
            goBack()
        }
        next.setOnClickListener {
            if (!tmpMVM.setupSetted){
                val snack = Snackbar.make(it,"Choose a plant icon",Snackbar.LENGTH_SHORT)
                snack.show()
            }
            else { //avanza
                viewModel.plantName=plantName.text.toString()
                findNavController().navigate(R.id.action_plantSetup_to_plantSetup2)
            }
        }
    }

    fun goBack(){
        viewModel.reset()
        findNavController().navigate(R.id.action_plantSetup_to_wifisetup)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true)
            {
                override fun handleOnBackPressed() {
                    // Leave empty do disable back press or
                    // write your code which you want
                    goBack()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }
}