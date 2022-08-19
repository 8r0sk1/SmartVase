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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase
import it.polito.did.smartvase.R
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.transition.TransitionInflater
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.switchmaterial.SwitchMaterial
import android.content.Intent




class Homepage : Fragment(R.layout.homepage) {

    private val viewModel: MainViewModel by activityViewModels<MainViewModel>()

    companion object {
        fun newInstance() = Homepage()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        exitTransition = inflater.inflateTransition(R.transition.fade)
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
        val profile = view.findViewById<ImageButton>(R.id.profileButton1)
        val removePlant = view.findViewById<FloatingActionButton>(R.id.removePlantButton1)
        val deleteConfirm = view.findViewById<ConstraintLayout>(R.id.deleteConfirm1)
        val deleteNo = view.findViewById<Button>(R.id.deleteNo1)
        val deleteYes = view.findViewById<Button>(R.id.deleteYes1)
        var removing :Boolean=false
        val hider = view.findViewById<ImageView>(R.id.hider1)
        val bg = view.findViewById<ImageView>(R.id.backgroundImage1)

        val plantCard = view.findViewById<CardView>(R.id.cardPlant1) //TODO capire cosa premere per aprire dash
        val waterLevel = view.findViewById<ImageView>(R.id.cardWaterLevel1)
        val waterLevelHeight=waterLevel.translationY
        val auto = view.findViewById<SwitchMaterial>(R.id.autoSwitch)
        val txtV = view.findViewById<TextView>(R.id.yourPlantText)

        val db = Firebase.database.reference
        val ref = db.child("chiave")

        if(!viewModel.plantCreated)
            hider.visibility=View.VISIBLE

        ref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                txtV.text = snapshot.getValue<String>()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        waterLevel.translationY +=  viewModel.waterLevel *  waterLevelHeight

        plantCard.setOnLongClickListener{
            removePlant.visibility= View.VISIBLE
            removePlant.setOnClickListener{
                if(!removing) {
                    removing = true
                    deleteConfirm.visibility = View.VISIBLE
                    deleteYes.setOnClickListener {
                        // activity?.vibrate(25);
                        removePlant.visibility = View.INVISIBLE
                        hider.visibility = View.VISIBLE
                        viewModel.plantCreated = false
                        removing=false
                        deleteConfirm.visibility=View.INVISIBLE

                        val snack = Snackbar.make(it, "Plant deleted", Snackbar.LENGTH_LONG)
                        snack.show()
                    }
                    deleteNo.setOnClickListener {
                        deleteConfirm.visibility = View.INVISIBLE
                        removing = false
                    }
                }
                //else  activity?.vibrate(25);
            }
            bg.setOnClickListener{
                removePlant.visibility=View.INVISIBLE
            }
            // activity?.vibrate(25);
            return@setOnLongClickListener true
        }

        addPlant.setOnClickListener {
            if(!removing) findNavController().navigate(R.id.action_homepage_to_wifisetup)
            //else  activity?.vibrate(25);
        }

        plantCard.setOnClickListener {
            if(!removing) findNavController().navigate(R.id.action_homepage_to_dashboard)
            //else  activity?.vibrate(25);
        }

        auto.setOnCheckedChangeListener { _, isChecked ->
            if(!removing) viewModel.auto = isChecked //TODO aggiornare database
            //else  activity?.vibrate(25);
        }

        //ToDO profile.setOnClickListener { findNavController().navigate(R.id.action_homepage_to_profile) }

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
