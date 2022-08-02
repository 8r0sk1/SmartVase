package it.polito.did.smartvase.ui.main

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.ViewModel
import it.polito.did.smartvase.R

class MainViewModel : ViewModel() {
    public var auto : Boolean = false
    public var notification : Boolean = true

    //in percentuale per come fatto su dashboard
    public var waterLevel : Float = 30f
    public var waterLevelMax : Float = 90f
    public var waterLevelMin : Float = 25f
    public var soilMoisture : Float = 60f
    public var soilMoistureMax : Float = 80f
    public var soilMoistureMin : Float = 25f

    public var plantName : String = "Ficus69xxx"
    public lateinit var plantIcon : Drawable //nomi file immagini profilo
    public var plantIconId : Int = 0 //nomi file immagini profilo
}