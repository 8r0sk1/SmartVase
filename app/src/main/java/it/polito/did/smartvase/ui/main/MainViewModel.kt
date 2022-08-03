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

    //in percentuale da 0 a 1 per come fatto su dashboard
    public var waterLevel : Float = .30f
    public var waterLevelMax : Float = 1.00f
    public var waterLevelMin : Float = .25f
    public var soilMoisture : Float = .60f
    public var soilMoistureMax : Float = .60f
    public var soilMoistureMin : Float = .25f
    public var defaultMax: Float =.0f
    public var defaultMin: Float =.0f

    public var plantName : String = "Ficus69xxx"

    public lateinit var plantIcon : Drawable //nomi file immagini profilo
    public var plantIconId : Int = R.mipmap.ic_launcher //nomi file immagini profilo

}