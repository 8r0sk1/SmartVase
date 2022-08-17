package it.polito.did.smartvase.ui.main

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
import android.widget.Toast

import it.polito.did.smartvase.MainActivity

import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener


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

        //caricamento default
        /*dividerMaxSoilMoisture?.updateLayoutParams<ConstraintLayout.LayoutParams> { verticalBias = 1-viewModel.defaultMax }
        arrowMax?.updateLayoutParams<ConstraintLayout.LayoutParams> { verticalBias = 1-viewModel.defaultMax }
        maxText?.updateLayoutParams<ConstraintLayout.LayoutParams> { verticalBias = 1-viewModel.defaultMax }
        maxText.setText((viewModel.defaultMax*100f).toInt().toString()+" %")
        barMax.progress= (viewModel.defaultMax*100f).toInt()*/
        soilMostureBarEdit(dividerMaxSoilMoisture,arrowMax,maxText,barMax,viewModel.defaultMax)

        /*dividerMinSoilMoisture?.updateLayoutParams<ConstraintLayout.LayoutParams> { verticalBias = 1-viewModel.defaultMin  }
        arrowMin?.updateLayoutParams<ConstraintLayout.LayoutParams> { verticalBias = 1-viewModel.defaultMin  }
        minText?.updateLayoutParams<ConstraintLayout.LayoutParams> { verticalBias = 1-viewModel.defaultMin }
        minText.setText((viewModel.defaultMin*100f).toInt().toString()+" %")*/
        soilMostureBarEdit(dividerMinSoilMoisture,arrowMin,minText,barMin,viewModel.defaultMin)

        barMax.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            var progressChangedValue = barMax.progress*0.01f
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                progressChangedValue = progress*0.01f
                soilMostureBarEdit(dividerMaxSoilMoisture,arrowMax,maxText,barMax,progressChangedValue)
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
                soilMostureBarEdit(dividerMinSoilMoisture,arrowMin,minText,barMin,progressChangedValue)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {
                return
            }
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                return
            }
        })

        back.setOnClickListener { findNavController().navigate(R.id.action_plantSetup2_to_plantSetup) }
        next.setOnClickListener {
            viewModel.defaultMax=barMax.progress*0.01f
            viewModel.defaultMin=barMin.progress*0.01f
            //TODO VLAD db
            findNavController().navigate(R.id.action_plantSetup2_to_dashboard) }
    }

    fun soilMostureBarEdit(divider:ProgressBar,arrow:ImageView,text:TextView,bar:SeekBar,progressValue:Float){
        divider?.updateLayoutParams<ConstraintLayout.LayoutParams> { verticalBias = 1-progressValue}
        arrow?.updateLayoutParams<ConstraintLayout.LayoutParams> { verticalBias = 1-progressValue }
        text?.updateLayoutParams<ConstraintLayout.LayoutParams> { verticalBias = 1-progressValue }
        text.setText((progressValue*100f).toInt().toString()+" %")
        bar.progress= (progressValue*100f).toInt()
    }
}