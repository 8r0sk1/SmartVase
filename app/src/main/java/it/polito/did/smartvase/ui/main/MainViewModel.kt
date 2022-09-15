package it.polito.did.smartvase.ui.main

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import it.polito.did.smartvase.R

class MainViewModel : ViewModel() {
    var plantCreated : Boolean=true //TODO debug
    var auto : Boolean = false
    var notification : Boolean = true

    //in percentuale da 0 a 1 per come fatto su dashboard
    var waterLevel : Float = .30f
    var waterLevelMax : Float = 1.00f
    var waterLevelMin : Float = .25f
    var soilMoisture : Float = .90f
//    var soilMoistureMax : Float = .60f
//    var soilMoistureMin : Float = .25f
    var defaultMax: Float =.95f
    var defaultMin: Float =.25f

    var plantName : String = "Plant name"

//    lateinit var plantIcon : Drawable //nomi file immagini profilo
    var plantIconId : Int = R.drawable.ficusicon //nomi file immagini profilo

    var iconSelected : Boolean=true
    var setupSetted : Boolean = false
    var connected : Boolean = false

    lateinit var auth: FirebaseAuth
    var loggedIn : Boolean = false
    var idUtente : String? = null
    var plantMacAddress : String = "BC:FF:4D:5F:2E:51"

    val db = Firebase.database.reference
    val ref = db.child("chiave")

    var porcata=true
    var wifiPage=false

    init {
        //TODO RICEVERE DA DATABASE VALORI DEFAULT
        //reset()
    }

    fun reset(){
        plantCreated=false
        auto = false
        notification = true

        //in percentuale da 0 a 1 per come fatto su dashboard
        waterLevel = .30f
        waterLevelMax = 1.00f
        waterLevelMin = .25f
        soilMoisture = .60f
//        soilMoistureMax = .60f
//        soilMoistureMin = .25f
        defaultMax =.45f
        defaultMin =.25f

        plantName = "Plant name"

        plantIconId = android.R.drawable.list_selector_background //nomi file immagini profilo
        setupSetted = false
        iconSelected = false
        //TODO mancano ultime variabili
    }
    fun setMvm(v: MainViewModel){
        plantCreated=v.plantCreated
        auto = v.auto
        notification = v.notification

        //in percentuale da 0 a 1 per come fatto su dashboard
        waterLevel = v.waterLevel
        waterLevelMax = v.waterLevelMax
        waterLevelMin = v.waterLevelMin
        soilMoisture = v.soilMoisture
//        soilMoistureMax = v.soilMoistureMax
//        soilMoistureMin = v.soilMoistureMin
        defaultMax =v.defaultMax
        defaultMin = v.defaultMin

        plantName = v.plantName

        plantIconId = v.plantIconId
        setupSetted = v.setupSetted
        iconSelected = v.iconSelected
    }

}
