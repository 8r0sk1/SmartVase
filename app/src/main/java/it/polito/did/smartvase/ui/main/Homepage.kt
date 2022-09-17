package it.polito.did.smartvase.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.firebase.auth.FirebaseAuth
import it.polito.did.smartvase.MainActivity
import it.polito.did.smartvase.R

class Homepage : Fragment(R.layout.homepage) {

    private val viewModel: MainViewModel by activityViewModels<MainViewModel>()

    fun Long.toBoolean() = this>0
    fun getDataFromDB(){
        viewModel.db.child("plants").child(viewModel.plantMacAddress).child("name").get()
                .addOnSuccessListener {
                    viewModel.plantName = it.value.toString()
                    Log.d("caccaaaaa",viewModel.plantName)
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
                    Log.d("panettone", "ue buono")
                    if(viewModel.porcata) {
                        viewModel.porcata=false
                        refreshFrag()
                    }
                }.addOnFailureListener {
                Toast.makeText(context, "Error getting data from DB", Toast.LENGTH_SHORT).show()
            }

    }
    fun checkPlantList(){
        viewModel.auth.currentUser?.let {
            viewModel.db.child("users").child(it.uid).child("plant1").get().addOnSuccessListener {
                viewModel.plantCreated = it.value != null
            }.addOnFailureListener{
                Toast.makeText(context, "Error getting data from DB", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun refreshFrag(){findNavController().navigate(R.id.action_homepage_to_homepage)}

    fun fastAccessNoLogin(user: String, psw:String){
        viewModel.loggedIn=true
        viewModel.auth.signInWithEmailAndPassword(user, psw).addOnCompleteListener{
            if(it.isSuccessful){
                viewModel.loggedIn = true
                viewModel.idUtente = viewModel.auth.currentUser?.uid

            }
        }.addOnFailureListener{
//            Toast.makeText(this@Homepage.requireActivity(), it.localizedMessage, Toast.LENGTH_LONG).show()
        }
    }

    //var elapsed : Long =0
    fun checkLog(){
        if(viewModel.auth.getCurrentUser()==null)
            findNavController().navigate(R.id.action_homepage_to_registerActivity)
        else {
            checkPlantList()
            getDataFromDB()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.auth = FirebaseAuth.getInstance()
        checkLog()

        viewModel.plantMacAddress="BC:FF:4D:5F:2E:51"
        //(activity as MainActivity).writeInternalStorage("vittorio@gmail.com;vittorio1234") //TODO se siamo in pericolo
        val account = (activity as MainActivity).readUser()
        if(account[0]!="0")
            fastAccessNoLogin(account[0],account[1])

        // DA DE-COMMENTARE PER FARE IL LOGIN
        // E COMMENTARE LA LINEA CHE LANCIA "fastAccessNoLogin"
        /*if(!viewModel.loggedIn)
            findNavController().navigate(R.id.action_homepage_to_registerActivity)*/
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.fade)
        exitTransition = inflater.inflateTransition(R.transition.fade)

        /*if(viewModel.porcata) {
            viewModel.porcata=false
            findNavController().navigate(R.id.action_homepage_to_homepage)
        }*/
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.homepage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        val refresh = view.findViewById<ImageButton>(R.id.refresh1)
        val loading = view.findViewById<ConstraintLayout>(R.id.loading1)

        if(viewModel.porcata) {
            loading.visibility = View.VISIBLE
            Handler().postDelayed({loading.visibility=View.GONE},1000)
        }
        else
            loading.visibility=View.GONE
        Log.d("visibilititit",viewModel.plantCreated.toString())
        if(!viewModel.plantCreated)
            hider.visibility=View.VISIBLE
        else{
            hider.visibility=View.GONE
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

        refresh.setOnClickListener { refreshFrag() }

        plantCard.setOnLongClickListener{
            removePlant.visibility= View.VISIBLE
            removePlant.setOnClickListener{
                if(!removing) {
                    auto.isClickable=false
                    refresh.isClickable=false
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
                        //todo vladdo sei sicuro?????????????????????????????????????????

                        val snack = Snackbar.make(it, "Plant deleted", Snackbar.LENGTH_LONG)
                        snack.show()
                    }
                    deleteNo.setOnClickListener {
                        deleteConfirm.visibility = View.INVISIBLE
                        removing = false
                        refresh.isClickable=true
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
            viewModel.porcata=true
            if(!removing) findNavController().navigate(R.id.action_homepage_to_wifisetup)
            else (activity as MainActivity).vibration(true)
        }

        plantCard.setOnClickListener {
            viewModel.porcata=true
            if(!removing) findNavController().navigate(R.id.action_homepage_to_dashboard)
            else (activity as MainActivity).vibration(true)
        }

        auto.setOnCheckedChangeListener { _, isChecked ->
            if(!removing) {
                viewModel.auto = isChecked //TODO vlad aggiornare database
                if(isChecked)
                    viewModel.db.child("plants").child(viewModel.plantMacAddress).child("auto_mode").setValue(1)
                else
                    viewModel.db.child("plants").child(viewModel.plantMacAddress).child("auto_mode").setValue(0)
            }
            else (activity as MainActivity).vibration(true)
        }

        profile.setOnClickListener {
            viewModel.porcata=true
            if(!removing) findNavController().navigate(R.id.action_homepage_to_profile)
            else (activity as MainActivity).vibration(true)
        }

    }

    override fun onResume() {
        super.onResume()
        checkLog()
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