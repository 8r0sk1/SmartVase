package it.polito.did.smartvase.ui.main

import android.widget.ImageView
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    public var auto : Boolean = false
    public var notification : Boolean = true
    public var waterLevel : Float = 0f //in percentuale per come fatto su dashboard
    public var soilMoisture : Float = 0f
    public lateinit var plantIcon : ImageView //nomi file immagini profilo
    public var plantName : String = "Ficus"
}