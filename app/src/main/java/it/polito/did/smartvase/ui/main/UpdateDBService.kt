package it.polito.did.smartvase.ui.main

import android.os.Bundle
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.transition.TransitionInflater
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import it.polito.did.smartvase.R

class UpdateDBService : Fragment() {

    private val viewModel: MainViewModel by activityViewModels<MainViewModel>()
    val db = Firebase.database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.plantMacAddress = db.child("plants/" + viewModel.idUtente).toString()
        viewModel.plantName = db.child("plants/" + viewModel.idUtente).child(viewModel.plantMacAddress!!).child("name").toString()
        viewModel.soilMoisture = db.child("plants/" + viewModel.idUtente).child(viewModel.plantMacAddress!!).child("soilMosture") as Float
        viewModel.defaultMax = db.child("plants/" + viewModel.idUtente).child(viewModel.plantMacAddress!!).child("soilMostureMax") as Float
        viewModel.defaultMin = db.child("plants/" + viewModel.idUtente).child(viewModel.plantMacAddress!!).child("soilMostureMin") as Float
        viewModel.waterLevel = db.child("plants/" + viewModel.idUtente).child(viewModel.plantMacAddress!!).child("waterLevel") as Float
        viewModel.plantIconId = db.child("plants/" + viewModel.idUtente).child(viewModel.plantMacAddress!!).child("imagePlant") as Int

    }
}