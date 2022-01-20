package it.polito.did.smartvase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import it.polito.did.smartvase.ui.main.Homepage

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        //nascondo la Action Bar (barra con il titolo dell'app)
        supportActionBar?.hide()

    }
}