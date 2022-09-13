package it.polito.did.smartvase.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.google.android.material.button.MaterialButton
import it.polito.did.smartvase.R

class Profile : Fragment() {

    private val viewModel: MainViewModel by activityViewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.slide)
        exitTransition = inflater.inflateTransition(R.transition.slide)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val back = view.findViewById<Button>(R.id.back_button7)
        val logout = view.findViewById<MaterialButton>(R.id.logout7)
        val email = view.findViewById<TextView>(R.id.email7)

        email.setText(viewModel.auth.currentUser?.email)
        logout.setOnClickListener {
            viewModel.loggedIn=false
            findNavController().navigate(R.id.action_profile_to_homepage)
        }
        back.setOnClickListener {
            //viewModel.setMvm(tmpMVM)
            goBack()
        }
    }

    fun goBack(){
        viewModel.reset()
        findNavController().navigate(R.id.action_plantSetup_to_wifisetup)
    }
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
}