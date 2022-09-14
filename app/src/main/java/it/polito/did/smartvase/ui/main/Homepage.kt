package it.polito.did.smartvase.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import it.polito.did.smartvase.R
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.transition.TransitionInflater
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.switchmaterial.SwitchMaterial
import android.content.Intent
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import it.polito.did.smartvase.MainActivity

class Homepage : Fragment(R.layout.homepage) {

    private val viewModel: MainViewModel by activityViewModels<MainViewModel>()

    fun Long.toBoolean() = this>0
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
            viewModel.soilMoisture = (it.value as Double).toFloat()
        }.addOnFailureListener{
            Toast.makeText(context, "Error getting data from DB", Toast.LENGTH_SHORT).show()
        }

        viewModel.db.child("plants").child(viewModel.plantMacAddress).child("soilMoistureMin").get().addOnSuccessListener {
            viewModel.defaultMin = (it.value as Double).toFloat()
        }.addOnFailureListener{
            Toast.makeText(context, "Error getting data from DB", Toast.LENGTH_SHORT).show()
        }

        viewModel.db.child("plants").child(viewModel.plantMacAddress).child("soilMoistureMax").get().addOnSuccessListener {
            viewModel.defaultMax = (it.value as Double).toFloat()
        }.addOnFailureListener{
            Toast.makeText(context, "Error getting data from DB", Toast.LENGTH_SHORT).show()
        }

        viewModel.db.child("plants").child(viewModel.plantMacAddress).child("waterLevel").get().addOnSuccessListener {
            viewModel.waterLevel = (it.value as Double).toFloat()
        }.addOnFailureListener{
            Toast.makeText(context, "Error getting data from DB", Toast.LENGTH_SHORT).show()
        }

        viewModel.db.child("plants").child(viewModel.plantMacAddress).child("imagePlant").get().addOnSuccessListener {
            viewModel.plantIconId = (it.value as Long).toInt()
        }.addOnFailureListener{
            Toast.makeText(context, "Error getting data from DB", Toast.LENGTH_SHORT).show()
        }

        viewModel.db.child("plants").child(viewModel.plantMacAddress).child("notify_mode").get().addOnSuccessListener {
            viewModel.notification = (it.value as Long).toBoolean()
        }.addOnFailureListener{
            Toast.makeText(context, "Error getting data from DB", Toast.LENGTH_SHORT).show()
        }

        viewModel.db.child("plants").child(viewModel.plantMacAddress).child("auto_mode").get().addOnSuccessListener {
            viewModel.auto = (it.value as Long).toBoolean()
        }.addOnFailureListener{
            Toast.makeText(context, "Error getting data from DB", Toast.LENGTH_SHORT).show()
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.auth = FirebaseAuth.getInstance()
        Log.d("userrr", viewModel.auth.currentUser.toString())

        fastAccessNoLogin()

        // DA DE-COMMENTARE PER FARE IL LOGIN
        // E COMMENTARE LA LINEA CHE LANCIA "fastAccessNoLogin"
        /*if(!viewModel.loggedIn)
            findNavController().navigate(R.id.action_homepage_to_registerActivity)*/

        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide)
        exitTransition = inflater.inflateTransition(R.transition.fade)

        getDataFromDB()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.homepage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getDataFromDB()

        val addPlant = view.findViewById<FloatingActionButton>(R.id.addPlantButton1)
        val profile = view.findViewById<FloatingActionButton>(R.id.profileButton1)
        val removePlant = view.findViewById<FloatingActionButton>(R.id.removePlantButton1)
        val deleteConfirm = view.findViewById<ConstraintLayout>(R.id.deleteConfirm1)
        val deleteNo = view.findViewById<Button>(R.id.deleteNo1)
        val deleteYes = view.findViewById<Button>(R.id.deleteYes1)
        var removing = false
        val hider = view.findViewById<ImageView>(R.id.hider1)
        val bg = view.findViewById<ConstraintLayout>(R.id.constraintLayout1)

        val plantCard = view.findViewById<CardView>(R.id.cardPlant1)
        val plantName = view.findViewById<TextView>(R.id.plantName1)
        val plantIcon = view.findViewById<ImageView>(R.id.plantIcon1)
        val waterLevel = view.findViewById<ImageView>(R.id.cardWaterLevel1)
        val waterLevelHeight=waterLevel.translationY
        val auto = view.findViewById<SwitchMaterial>(R.id.autoSwitch1)
        val soilAlert = view.findViewById<ImageView>(R.id.soilAlert1)

        if(!viewModel.plantCreated)
            hider.visibility=View.VISIBLE
        else{
            if(viewModel.waterLevel<.10){
                plantCard.setBackgroundColor(0xCCDF0C49.toInt())
                (activity as MainActivity).notification(viewModel.plantIconId,"Please load some water",(5*viewModel.waterLevel).toString()+" L left")
                if(viewModel.soilMoisture<viewModel.defaultMin) {
                    (activity as MainActivity).notification(viewModel.plantIconId,"Please water this plant",(viewModel.soilMoisture*100).toInt().toString()+"% Soil moisture")
                    soilAlert.visibility = View.VISIBLE
                }
            }
        }

        auto.isChecked=viewModel.auto
        plantName.setText(viewModel.plantName)
        plantIcon.setImageResource(viewModel.plantIconId)

        waterLevel.translationY -=  viewModel.waterLevel *  waterLevelHeight

        plantCard.setOnLongClickListener{
            removePlant.visibility= View.VISIBLE
            removePlant.setOnClickListener{
                if(!removing) {
                    auto.isClickable=true
                    removing = true
                    deleteConfirm.visibility = View.VISIBLE
                    deleteYes.setOnClickListener {
                        (activity as MainActivity).vibration(false)

                        removePlant.visibility = View.INVISIBLE
                        hider.visibility = View.VISIBLE
                        viewModel.reset()
                        removing=false
                        deleteConfirm.visibility=View.INVISIBLE

                        viewModel.db.child("users/" + viewModel.auth.currentUser?.uid).removeValue()

                        val snack = Snackbar.make(it, "Plant deleted", Snackbar.LENGTH_LONG)
                        snack.show()
                    }
                    deleteNo.setOnClickListener {
                        deleteConfirm.visibility = View.INVISIBLE
                        removing = false
                        auto.isClickable=true
                    }
                }
                else (activity as MainActivity).vibration(true)
            }
            bg.setOnClickListener{
                removePlant.visibility=View.INVISIBLE
            }
            (activity as MainActivity).vibration(true)
            return@setOnLongClickListener true
        }

        addPlant.setOnClickListener {
            if(!removing) findNavController().navigate(R.id.action_homepage_to_wifisetup)
            else (activity as MainActivity).vibration(true)
        }

        plantCard.setOnClickListener {
            if(!removing) findNavController().navigate(R.id.action_homepage_to_dashboard)
            else (activity as MainActivity).vibration(true)
        }

        auto.setOnCheckedChangeListener { _, isChecked ->
            if(!removing) viewModel.auto = isChecked //TODO aggiornare database
            else (activity as MainActivity).vibration(true)
        }

        profile.setOnClickListener {
            if(!removing) findNavController().navigate(R.id.action_homepage_to_profile)
            else (activity as MainActivity).vibration(true)
        }
    }

    fun fastAccessNoLogin(){
        val email = "vlad@gmail.com"
        val password = "vlad1234"
        viewModel.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
            if(it.isSuccessful){
                viewModel.loggedIn = true
                viewModel.idUtente = viewModel.auth.currentUser?.uid
            }
        }.addOnFailureListener{
            Toast.makeText(this@Homepage.requireActivity(), it.localizedMessage, Toast.LENGTH_LONG).show()
        }
    }

    override fun onResume() {
        super.onResume()
        if(!viewModel.loggedIn)
            findNavController().navigate(R.id.action_homepage_to_registerActivity)
    }

    fun goBack(){
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)
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