package it.polito.did.smartvase.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.google.android.material.floatingactionbutton.FloatingActionButton
import it.polito.did.smartvase.R

class Settings : Fragment() {

    private val viewModel: MainViewModel by activityViewModels<MainViewModel>()
    fun Long.toBoolean() = this>0
    fun getDataFromDB(){
        /*var goon=true
        viewModel.auth.currentUser?.let {
            viewModel.db.child("users").child(it.uid).child(viewModel.plantMacAddress).get().addOnSuccessListener {
                if(it.value==null)
                    goon=true
            }.addOnFailureListener{
                Toast.makeText(context, "Error getting data from DB", Toast.LENGTH_SHORT).show()
            }
        }
*/
        /*viewModel.db.child("plants").child(viewModel.plantMacAddress).get().addOnSuccessListener {
            viewModel.plantMacAddress = it.value.toString()
        }.addOnFailureListener{
            Toast.makeText(context, "Error getting data from DB", Toast.LENGTH_SHORT).show()
        }*/
//        if(goon) {
        viewModel.db.child("plants").child(viewModel.plantMacAddress).child("name").get()
            .addOnSuccessListener {
                viewModel.plantName = it.value.toString()
            }.addOnFailureListener {
                Toast.makeText(context, "Error getting data from DB", Toast.LENGTH_SHORT).show()
            }

        viewModel.db.child("plants").child(viewModel.plantMacAddress).child("soilMoisture")
            .get().addOnSuccessListener {
                viewModel.soilMoisture = (it.value as Double).toFloat()
            }.addOnFailureListener {
                Toast.makeText(context, "Error getting data from DB", Toast.LENGTH_SHORT).show()
            }

        viewModel.db.child("plants").child(viewModel.plantMacAddress).child("soilMoistureMin")
            .get().addOnSuccessListener {
                viewModel.defaultMin = (it.value as Double).toFloat()
            }.addOnFailureListener {
                Toast.makeText(context, "Error getting data from DB", Toast.LENGTH_SHORT).show()
            }

        viewModel.db.child("plants").child(viewModel.plantMacAddress).child("soilMoistureMax")
            .get().addOnSuccessListener {
                viewModel.defaultMax = (it.value as Double).toFloat()
            }.addOnFailureListener {
                Toast.makeText(context, "Error getting data from DB", Toast.LENGTH_SHORT).show()
            }

        viewModel.db.child("plants").child(viewModel.plantMacAddress).child("waterLevel").get()
            .addOnSuccessListener {
                viewModel.waterLevel = (it.value as Double).toFloat()
            }.addOnFailureListener {
                Toast.makeText(context, "Error getting data from DB", Toast.LENGTH_SHORT).show()
            }

        viewModel.db.child("plants").child(viewModel.plantMacAddress).child("imagePlant").get()
            .addOnSuccessListener {
                viewModel.plantIconId = (it.value as Long).toInt()
            }.addOnFailureListener {
                Toast.makeText(context, "Error getting data from DB", Toast.LENGTH_SHORT).show()
            }

        viewModel.db.child("plants").child(viewModel.plantMacAddress).child("notify_mode").get()
            .addOnSuccessListener {
                viewModel.notification = (it.value as Long).toBoolean()
            }.addOnFailureListener {
                Toast.makeText(context, "Error getting data from DB", Toast.LENGTH_SHORT).show()
            }

        viewModel.db.child("plants").child(viewModel.plantMacAddress).child("auto_mode").get()
            .addOnSuccessListener {
                viewModel.auto = (it.value as Long).toBoolean()
            }.addOnFailureListener {
                Toast.makeText(context, "Error getting data from DB", Toast.LENGTH_SHORT).show()
            }
    }

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

        val plantName = view.findViewById<TextView>(R.id.plantName4)
        val avatar = view.findViewById<ImageView>(R.id.avatar4)
        val plantIcon = view.findViewById<FloatingActionButton>(R.id.plantIcon4)

        val maxText = view.findViewById<TextView>(R.id.maxText4)
        val minText = view.findViewById<TextView>(R.id.minText4)
        val dividerMaxSoilMoisture = view.findViewById<ProgressBar>(R.id.dividerMaxSoilMoisture4)
        val dividerMinSoilMoisture = view.findViewById<ProgressBar>(R.id.dividerMinSoilMoisture4)
        val arrowMax = view.findViewById<ImageView>(R.id.arrowMax4)
        val arrowMin = view.findViewById<ImageView>(R.id.arrowMin4)
        val barMax = view.findViewById<SeekBar>(R.id.barMax4)
        val barMin = view.findViewById<SeekBar>(R.id.barMin4)

        val tmpMVM = viewModel
        val back = view.findViewById<Button>(R.id.back_button4)
        val next = view.findViewById<Button>(R.id.next_button4)

        avatar.setImageResource(tmpMVM.plantIconId)
        plantName.setText(tmpMVM.plantName)

        soilMoistureBarEdit(dividerMaxSoilMoisture,arrowMax,maxText,barMax,viewModel.defaultMax)
        soilMoistureBarEdit(dividerMinSoilMoisture,arrowMin,minText,barMin,viewModel.defaultMin)

        barMax.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
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
        barMin.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
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

        plantIcon.setOnClickListener{findNavController().navigate(R.id.action_settings_to_iconListFragment)}
        back.setOnClickListener {
            viewModel.setMvm(tmpMVM)
            goBack()
        }
        next.setOnClickListener {
            viewModel.defaultMax=barMax.progress*0.01f
            viewModel.defaultMin=barMin.progress*0.01f
            viewModel.plantName=plantName.text.toString()
            viewModel.db.child("plants" + viewModel.plantMacAddress).child("soilMoistureMin").setValue(viewModel.defaultMin)
            viewModel.db.child("plants" + viewModel.plantMacAddress).child("soilMoistureMax").setValue(viewModel.defaultMax)
            viewModel.db.child("plants" + viewModel.plantMacAddress).child("soilMoistureMin").setValue(viewModel.plantName)
            findNavController().navigate(R.id.action_settings_to_dashboard) }
    }

    fun soilMoistureBarEdit(divider:ProgressBar, arrow:ImageView, text:TextView, bar: SeekBar, progressValue:Float){
        divider.updateLayoutParams<ConstraintLayout.LayoutParams> { verticalBias = 1-progressValue}
        arrow.updateLayoutParams<ConstraintLayout.LayoutParams> { verticalBias = 1-progressValue }
        text.updateLayoutParams<ConstraintLayout.LayoutParams> { verticalBias = 1-progressValue }
        text.setText((progressValue*100f).toInt().toString()+" %")
        bar.progress= (progressValue*100f).toInt()
    }

    fun goBack(){findNavController().navigate(R.id.action_settings_to_dashboard)}
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