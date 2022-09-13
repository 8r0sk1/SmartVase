package it.polito.did.smartvase.ui.main

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.google.android.material.snackbar.Snackbar
import it.polito.did.smartvase.MainActivity
import it.polito.did.smartvase.R

@Suppress("DEPRECATION")
class WifiSetup : Fragment() {

    private val viewModel: MainViewModel by activityViewModels<MainViewModel>()
    lateinit var wifiManager: WifiManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide)
        exitTransition = inflater.inflateTransition(R.transition.fade)
        requestPerm()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.wifi_setup, container, false)
    }
    private lateinit var browserButton: Button

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        browserButton = view.findViewById<Button>(R.id.browserButton6)
        val back = view.findViewById<Button>(R.id.back_button6)
        val next = view.findViewById<Button>(R.id.next_button6)
        val connect = view.findViewById<Button>(R.id.connect6)


        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.msftconnecttest.com/redirect"))

                //viewModel.plantIcon=resources.getDrawable(R.drawable.nficusicon)

        browserButton.setOnClickListener {
            startActivity(browserIntent)
        }
        if(!wifiConnected())
            browserButton.visibility=View.INVISIBLE

        connect.setOnClickListener {
            startActivity( Intent(Settings.ACTION_WIFI_SETTINGS))

            if(wifiConnected())
                browserButton.visibility=View.VISIBLE
        }

        back.setOnClickListener {goBack() }
        next.setOnClickListener {

            // TODO vitto mac address
            viewModel.db.child("plants").child("02:02:02:02:02:02").get().addOnSuccessListener {
                if(it.value==null)
                    Log.d("cosucce", "Connettiti scemo")
                if(viewModel.plantCreated) {
                    //val snack = Snackbar.make(it, "Already connected", Snackbar.LENGTH_LONG)
                    //snack.show()
                    //Log.i("cosucce", viewModel.plantMacAddress)
                    //Log.i("cosucce", "Got value ${it.value}")
                    Log.d("cosucce", "Ok")
                }
                else
                    findNavController().navigate(R.id.action_wifisetup_to_plantsetup)
            }.addOnFailureListener{
                //val snack = Snackbar.make(it, "DB Connection Lost.", Snackbar.LENGTH_SHORT)
                //snack.show()
                Log.d("cosucce", "Connettiti scemo")
            }

            Log.d("chiaveval", viewModel.db.child("chiave").get().toString())
            /*if (viewModel.db.child("plants").child(viewModel.plantMacAddress.toString()).get().equals(null)) {
                val snack = Snackbar.make(it, "Connect to Wi-Fi and complete the setup", Snackbar.LENGTH_SHORT)
                snack.show()
            } else {
                if(viewModel.plantCreated) {
                    val snack = Snackbar.make(it, "Already connected", Snackbar.LENGTH_LONG)
                    snack.show()
                }
                else
                    findNavController().navigate(R.id.action_wifisetup_to_plantsetup)
            }*/
        }
        view.findViewById<TextView>(R.id.tutorial6).setText(getSSid()) //TODO togliere

    }

    override fun onResume() {
        super.onResume()
        if(wifiConnected())
            browserButton.visibility=View.VISIBLE
    }

    fun goBack(){findNavController().navigate(R.id.action_wifisetup_to_homepage)}
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true)
            {
                override fun handleOnBackPressed() {
                    // Leave empty do disable back press or
                    // write your code which you want
                    goBack()
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }

    private fun wifiConnected() : Boolean {
        //if(getMac()=="SmartVase")
        if (getSSid().substring(1, 9) == "AndroidW")//TODO debug
        {
            //viewModel.plantMacAddress=getSSid().replace(":","").substring(10)
            return true
        }
        return false
    }
    private fun getSSid() : String{
        wifiManager = context?.getSystemService(Context.WIFI_SERVICE) as WifiManager
        Log.d("mamma", wifiManager.connectionInfo.toString())
        return wifiManager.connectionInfo.ssid
    }

    private fun requestPerm() {
        val requestPermissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                }
            }
        when {
            ContextCompat.checkSelfPermission(
                (activity as MainActivity),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_WIFI_STATE) -> {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected. In this UI,
                // include a "cancel" or "no thanks" button that allows the user to
                // continue using your app without granting the permission.
                //TODO
            }
            else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                requestPermissionLauncher.launch(
                    Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
        val requestPermissionLauncher2 =
            registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                } else {
                    // Explain to the user that the feature is unavailable because the
                    // features requires a permission that the user has denied. At the
                    // same time, respect the user's decision. Don't link to system
                    // settings in an effort to convince the user to change their
                    // decision.
                }
            }
        when {
            ContextCompat.checkSelfPermission(
                (activity as MainActivity),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                // You can use the API that requires the permission.
            }
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) -> {
                // In an educational UI, explain to the user why your app requires this
                // permission for a specific feature to behave as expected. In this UI,
                // include a "cancel" or "no thanks" button that allows the user to
                // continue using your app without granting the permission.
                //TODO
            }
            else -> {
                // You can directly ask for the permission.
                // The registered ActivityResultCallback gets the result of this request.
                requestPermissionLauncher2.launch(
                    Manifest.permission.ACCESS_COARSE_LOCATION)
            }
        }
    }
}
