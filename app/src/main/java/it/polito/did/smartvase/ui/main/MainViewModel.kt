package it.polito.did.smartvase.ui.main

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    public var auto : Boolean = false
    public var notification : Boolean = true
    public var waterLevel : Float = 0
    public var soilMoisture : Float = 0
}