package it.polito.did.smartvase

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import it.polito.did.smartvase.ui.main.Homepage

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //nascondo la Action Bar (barra con il titolo dell'app)
        supportActionBar?.hide()
    }
}