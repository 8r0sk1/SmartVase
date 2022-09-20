package it.polito.did.smartvase.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import it.polito.did.smartvase.R
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.activity.OnBackPressedCallback
import com.google.android.material.floatingactionbutton.FloatingActionButton


class PlantSetup2 : Fragment() {

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
        return inflater.inflate(R.layout.plant_setup2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val plantName = view.findViewById<TextView>(R.id.plantName32)
        val avatar = view.findViewById<ImageView>(R.id.avatar32)
        val plantIcon = view.findViewById<FloatingActionButton>(R.id.plantIcon32)

        val maxText = view.findViewById<TextView>(R.id.maxText32)
        val minText = view.findViewById<TextView>(R.id.minText32)
        val dividerMaxSoilMoisture = view.findViewById<ProgressBar>(R.id.dividerMaxSoilMoisture32)
        val dividerMinSoilMoisture = view.findViewById<ProgressBar>(R.id.dividerMinSoilMoisture32)
        val arrowMax = view.findViewById<ImageView>(R.id.arrowMax32)
        val arrowMin = view.findViewById<ImageView>(R.id.arrowMin32)
        val barMax = view.findViewById<SeekBar>(R.id.barMax32)
        val barMin = view.findViewById<SeekBar>(R.id.barMin32)

        val back = view.findViewById<Button>(R.id.back_button32)
        val next = view.findViewById<Button>(R.id.next_button32)

        plantName.setText(viewModel.plantName)
        avatar.setImageResource(viewModel.plantIconId)

        //caricamento default
        soilMoistureBarEdit(dividerMaxSoilMoisture,arrowMax,maxText,barMax,viewModel.defaultMax)
        soilMoistureBarEdit(dividerMinSoilMoisture,arrowMin,minText,barMin,viewModel.defaultMin)

        //set divider
        barMax.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            var progressChangedValue = barMax.progress*0.01f
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                progressChangedValue = progress*0.01f
                if(progressChangedValue*100f>barMin.progress+1)
                    soilMoistureBarEdit(dividerMaxSoilMoisture,arrowMax,maxText,barMax,progressChangedValue)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {
                return
            }
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                return
            }
        })
        barMin.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            var progressChangedValue = barMax.progress*0.01f
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                progressChangedValue = progress*0.01f
                if(progressChangedValue*100f<barMax.progress-1)
                    soilMoistureBarEdit(dividerMinSoilMoisture,arrowMin,minText,barMin,progressChangedValue)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {
                return
            }
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                return
            }
        })

        plantIcon.setOnClickListener{ findNavController().navigate(R.id.action_plantSetup2_to_iconListFragment)}
        back.setOnClickListener { goBack() }
        next.setOnClickListener {
            viewModel.defaultMax=barMax.progress*0.01f
            viewModel.defaultMin=barMin.progress*0.01f
            viewModel.plantCreated=true
            viewModel.db.child("users/" + viewModel.auth.currentUser?.uid + "/plant1").setValue(viewModel.plantMacAddress)
            viewModel.db.child("plants/" + viewModel.plantMacAddress).child("imagePlant").setValue(viewModel.plantIconId)
            viewModel.db.child("plants/" + viewModel.plantMacAddress).child("name").setValue(viewModel.plantName)
            viewModel.db.child("plants/" + viewModel.plantMacAddress).child("soilMoistureMax").setValue(viewModel.defaultMax)
            viewModel.db.child("plants/" + viewModel.plantMacAddress).child("soilMoistureMin").setValue(viewModel.defaultMin)
            //TODO VLAD aggiungere viewmodel.mac come valore di User->viewmodel.idutente->plant1
            findNavController().navigate(R.id.action_plantSetup2_to_dashboard) }
    }

    fun soilMoistureBarEdit(divider:ProgressBar, arrow:ImageView, text:TextView, bar:SeekBar, progressValue:Float){
        divider.updateLayoutParams<ConstraintLayout.LayoutParams> { verticalBias = 1-progressValue}
        arrow.updateLayoutParams<ConstraintLayout.LayoutParams> { verticalBias = 1-progressValue }
        text.updateLayoutParams<ConstraintLayout.LayoutParams> { verticalBias = 1-progressValue }
        text.setText((progressValue*100f).toInt().toString()+" %")
        bar.progress= (progressValue*100f).toInt()
    }

    fun goBack(){findNavController().navigate(R.id.action_plantSetup2_to_plantSetup)}
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