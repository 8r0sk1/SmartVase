package it.polito.did.smartvase.ui.main

import android.graphics.drawable.Drawable
import androidx.lifecycle.ViewModel
import it.polito.did.smartvase.R

class MainViewModel : ViewModel() {
    public var auto : Boolean = false
    public var notification : Boolean = true

    //in percentuale da 0 a 1 per come fatto su dashboard
    public var waterLevel : Float = .30f
    public var waterLevelMax : Float = 1.00f
    public var waterLevelMin : Float = .25f
    public var soilMoisture : Float = .60f
    public var soilMoistureMax : Float = .60f
    public var soilMoistureMin : Float = .25f
    public var defaultMax: Float =.45f
    public var defaultMin: Float =.25f

    public var plantName : String = "Ficus69xxx"

    public lateinit var plantIcon : Drawable //nomi file immagini profilo
    public var plantIconId : Int = R.mipmap.ic_launcher //nomi file immagini profilo

    public var setupSetted : Boolean = false

    init {
        //TODO RICEVERE DA DATABASE VALORI DEFAULT
        //reset()
    }

    fun reset(){
        auto = false
        notification = true

        //in percentuale da 0 a 1 per come fatto su dashboard
        waterLevel = .30f
        waterLevelMax = 1.00f
        waterLevelMin = .25f
        soilMoisture = .60f
        soilMoistureMax = .60f
        soilMoistureMin = .25f
        defaultMax =.45f
        defaultMin =.25f

        plantName = "Ficus69xxx"

        plantIconId = R.mipmap.ic_launcher //nomi file immagini profilo
        setupSetted = false
    }
    fun setMvm(v: MainViewModel){
        auto = v.auto
        notification = v.notification

        //in percentuale da 0 a 1 per come fatto su dashboard
        waterLevel = v.waterLevel
        waterLevelMax = v.waterLevelMax
        waterLevelMin = v.waterLevelMin
        soilMoisture = v.soilMoisture
        soilMoistureMax = v.soilMoistureMax
        soilMoistureMin = v.soilMoistureMin
        defaultMax =v.defaultMax
        defaultMin = v.defaultMin

        plantName = v.plantName

        plantIconId = v.plantIconId
        setupSetted = v.setupSetted
    }

}
