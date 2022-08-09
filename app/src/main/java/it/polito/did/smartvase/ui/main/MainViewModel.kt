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
        reset()
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

}
