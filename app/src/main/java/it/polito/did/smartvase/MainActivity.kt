package it.polito.did.smartvase

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator
import androidx.fragment.app.Fragment
import it.polito.did.smartvase.ui.main.Homepage

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //nascondo la Action Bar (barra con il titolo dell'app)
        supportActionBar?.hide()
    }

    fun showFragment(f: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container_view_tag, f)
//altre operazioni: inserimento,
//rimozione, cambio di visibilità, …
            .commit()
    }

    private fun addFragment(id: Int, f: Fragment) {
        var fm = supportFragmentManager
        var ft = fm.beginTransaction()
        ft.add(id, f)
        ft.addToBackStack("ScreenName")
        ft.commit()
    }



}