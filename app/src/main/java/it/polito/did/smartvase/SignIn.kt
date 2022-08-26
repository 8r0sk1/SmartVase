package it.polito.did.smartvase

import android.os.Bundle
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import it.polito.did.smartvase.ui.main.MainViewModel


class SignIn : Fragment(R.layout.sign_in) {

    private val viewModel: MainViewModel by activityViewModels<MainViewModel>()

    //private lateinit var binding: SignInBinding

//    private lateinit var binding: SignInBinding
    //private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val inflater = TransitionInflater.from(requireContext())
        enterTransition = inflater.inflateTransition(R.transition.fade)
        exitTransition = inflater.inflateTransition(R.transition.fade)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val signIn=view.findViewById<MaterialButton>(R.id.signIn)
        val signOut=view.findViewById<MaterialButton>(R.id.signOut)
        val userDetails=view.findViewById<TextView>(R.id.userDetails)

        viewModel.auth = FirebaseAuth.getInstance()

        if(viewModel.auth.currentUser == null){
            findNavController().navigate(R.id.action_signIn_to_registerActivity)
            /*startActivity(Intent(this, RegisterActivity::class.java))
            finish()*/
        }

        //binding = SignInBinding.inflate(layoutInflater)
        //setContentView(binding.root)

        signIn.setOnClickListener{
            findNavController().navigate(R.id.action_signIn_to_registerActivity)
            /*startActivity(Intent(this, RegisterActivity::class.java))
            finish()*/
        }

        signOut.setOnClickListener{
            viewModel.auth.signOut()
            userDetails.text = updateData()
        }

//        //nascondo la Action Bar (barra con il titolo dell'app)
//        supportActionBar?.hide()
        signIn.setOnClickListener { println("aAAAAAAAAAAAAAAa")
            Log.e("TAG", "message")}
    }

    override fun onResume() {
        super.onResume()
        view?.findViewById<TextView>(R.id.userDetails)?.text = updateData() //TODO da controllare
    }

    private fun updateData(): String{
        return "Email: ${viewModel.auth.currentUser?.email}"
    }
}