package it.polito.did.smartvase.ui.main

import android.widget.ImageView
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    public var auto : Boolean = false
    public var notification : Boolean = true
    public var waterLevel : Float = 0
    public var soilMoisture : Float = 0
    public lateinit var plantIcon : ImageView  //nomi file immagini profilo
    public var plantName : String = "Ficus"
}