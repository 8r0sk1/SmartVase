package it.polito.did.smartvase

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Vibrator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //nascondo la Action Bar (barra con il titolo dell'app)
        supportActionBar?.hide()
    }

    /*fun showFragment(f: Fragment) {
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
    }*/

    public fun vibration(ms:Long) {
        val vibratorService = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibratorService.vibrate(ms)
    }
}