package it.polito.did.smartvase.ui.main

data class PlantIcon(
    var iconId : Int,
    var type : String ="",
    var defaultMax : Int = 50,
    var defaultMin : Int = 25,
    var selected: Boolean = false
)