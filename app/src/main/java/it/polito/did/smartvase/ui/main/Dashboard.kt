package it.polito.did. smartvase.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import it.polito.did.smartvase.MainActivity
import it.polito.did.smartvase.R
import it.polito.did.smartvase.ui.main.MainViewModel

//import it.polito.did.smartvase.ui.main.Homepage

class Dashboard : Fragment() {

    private val viewModel: MainViewModel by activityViewModels<MainViewModel>()

    fun getDataFromDB(){
        viewModel.db.child("plants").child(viewModel.plantMacAddress).get().addOnSuccessListener {
            viewModel.plantMacAddress = it.value.toString()
        }.addOnFailureListener{
            Toast.makeText(context, "Error getting data from DB", Toast.LENGTH_SHORT).show()
        }

        viewModel.db.child("plants").child(viewModel.plantMacAddress).child("name").get().addOnSuccessListener {
            viewModel.plantName = it.value.toString()
        }.addOnFailureListener{
            Toast.makeText(context, "Error getting data from DB", Toast.LENGTH_SHORT).show()
        }

        viewModel.db.child("plants").child(viewModel.plantMacAddress).child("soilMoisture").get().addOnSuccessListener {
            viewModel.soilMoisture = it.value as Float
        }.addOnFailureListener{
            Toast.makeText(context, "Error getting data from DB", Toast.LENGTH_SHORT).show()
        }

        viewModel.db.child("plants").child(viewModel.plantMacAddress).child("soilMoistureMin").get().addOnSuccessListener {
            viewModel.defaultMin = it.value as Float
        }.addOnFailureListener{
            Toast.makeText(context, "Error getting data from DB", Toast.LENGTH_SHORT).show()
        }

        viewModel.db.child("plants").child(viewModel.plantMacAddress).child("soilMoistureMax").get().addOnSuccessListener {
            viewModel.defaultMax = it.value as Float
        }.addOnFailureListener{
            Toast.makeText(context, "Error getting data from DB", Toast.LENGTH_SHORT).show()
        }

        viewModel.db.child("plants").child(viewModel.plantMacAddress).child("waterLevel").get().addOnSuccessListener {
            viewModel.waterLevel = it.value as Float
        }.addOnFailureListener{
            Toast.makeText(context, "Error getting data from DB", Toast.LENGTH_SHORT).show()
        }

        viewModel.db.child("plants").child(viewModel.plantMacAddress).child("imagePlant").get().addOnSuccessListener {
            viewModel.plantIconId = it.value as Int
        }.addOnFailureListener{
            Toast.makeText(context, "Error getting data from DB", Toast.LENGTH_SHORT).show()
        }

        viewModel.db.child("plants").child(viewModel.plantMacAddress).child("notify_mode").get().addOnSuccessListener {
            viewModel.notification = it.value as Boolean
        }.addOnFailureListener{
            Toast.makeText(context, "Error getting data from DB", Toast.LENGTH_SHORT).show()
        }

        viewModel.db.child("plants").child(viewModel.plantMacAddress).child("auto_mode").get().addOnSuccessListener {
            viewModel.auto = it.value as Boolean
        }.addOnFailureListener{
            Toast.makeText(context, "Error getting data from DB", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide)
        exitTransition = inflater.inflateTransition(R.transition.fade)
        getDataFromDB()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.dashboard, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val waterBar = view.findViewById<CardView>(R.id.waterLevelBar2)
        val soilMostureBar = view.findViewById<CardView>(R.id.soilMoistureBar2)
        val plantName = view.findViewById<TextView>(R.id.plantName2)
        val plantIcon = view.findViewById<ImageView>(R.id.PlantIcon2)
        val offText = view.findViewById<TextView>(R.id.offText2)
        val homeButton = view.findViewById<ImageButton>(R.id.homeButton2)
        val editButton = view.findViewById<ImageButton>(R.id.editButton2)
        val autoWaterButton = view.findViewById<FloatingActionButton>(R.id.autoWaterButton2)
        val notificationButton = view.findViewById<ImageButton>(R.id.notificationButton2)
        val notificationState = view.findViewById<TextView>(R.id.notificationState2)
        val SoilMoisturePercentage = view.findViewById<TextView>(R.id.SoilMoisturePercentage)
        val WaterLevelPercentage = view.findViewById<TextView>(R.id.WaterLevelPercentage)
        val dividerMaxSoilMoisture = view.findViewById<ProgressBar>(R.id.dividerMaxSoilMoisture2)
        val dividerMinSoilMoisture = view.findViewById<ProgressBar>(R.id.dividerMinSoilMoisture2)
        val soilAlert = view.findViewById<ImageView>(R.id.soilAlert2)
        val waterAlert = view.findViewById<ImageView>(R.id.waterAlert2)

        val plants = FirebaseDatabase.getInstance().getReference("plants")

        val barHeight=waterBar.translationY
        if(viewModel.waterLevel<.10)
            waterAlert.visibility=View.VISIBLE
        if(viewModel.soilMoisture<viewModel.defaultMin)
            soilAlert.visibility=View.VISIBLE

        //popolamento viste
        plantName?.setText(viewModel.plantName)
        plantIcon?.setImageResource(viewModel.plantIconId)

        //testo percentuale riempimento
        WaterLevelPercentage?.text= (5*viewModel.waterLevel).toString()+"L"
        SoilMoisturePercentage?.text= (viewModel.soilMoisture*100).toInt().toString()+"%"

        //divisori barre
        dividerMaxSoilMoisture?.updateLayoutParams<ConstraintLayout.LayoutParams> { verticalBias = 1-viewModel.defaultMax  }
        dividerMinSoilMoisture?.updateLayoutParams<ConstraintLayout.LayoutParams> { verticalBias = 1-viewModel.defaultMin  }

        //riempire barre
        waterBar.translationY +=  (1-viewModel.waterLevel) *  barHeight
        soilMostureBar.translationY += (1-viewModel.soilMoisture) * barHeight

        if(viewModel.auto){
            offText?.text="ON"
            offText?.setTextColor(Color.RED)
        }
        if(!viewModel.notification){
            notification(notificationButton,notificationState,false)
        }

        homeButton.setOnClickListener { goBack() }
        editButton.setOnClickListener { findNavController().navigate(R.id.action_dashboard_to_settings) } //ancora da capire come fare, se con altro fragment

        autoWaterButton.setOnLongClickListener{
            viewModel.auto = !viewModel.auto
            (activity as MainActivity).vibration(true)
            if(viewModel.auto) {
                waterButton(autoWaterButton, offText, true)
                viewModel.db.child("plants").child("BC:FF:4D:5F:2E:51").child("auto_mode").setValue(1)
            }
            else {
                waterButton(autoWaterButton, offText, false)
                viewModel.db.child("plants").child("BC:FF:4D:5F:2E:51").child("auto_mode").setValue(0)
            }
            viewModel.ref.setValue(1) //TODO VLADDO da cambiare il path della reference in mainviewmodel
            return@setOnLongClickListener true
        }

        autoWaterButton.setOnClickListener{
            viewModel.db.child("plants").child("BC:FF:4D:5F:2E:51").child("to_water_control").setValue(1)
        }

        notificationButton.setOnClickListener {
            viewModel.notification = !viewModel.notification
            notification(notificationButton,notificationState,viewModel.notification)
            (activity as MainActivity).vibration(true)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    fun waterButton(waterButton:ImageButton, waterState:TextView, state:Boolean){
        if(state) {
            waterButton.foreground=resources.getDrawable(R.drawable.waterbuttonon)
            waterState.setText("ON")
        }
        else{
            waterButton.foreground=resources.getDrawable(R.drawable.waterbuttonoff)
            waterState.setText("OFF")
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    fun notification(notificationButton:ImageButton, notificationState:TextView, state:Boolean){
        if(state) {
            notificationButton.foreground=resources.getDrawable(R.drawable.notificationonbutton)
            notificationState.setText("ON")
        }
        else{
            notificationButton.foreground=resources.getDrawable(R.drawable.notificationoffbutton)
            notificationState.setText("OFF")
        }
    }
    fun goBack(){findNavController().navigate(R.id.action_dashboard_to_homepage)}
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