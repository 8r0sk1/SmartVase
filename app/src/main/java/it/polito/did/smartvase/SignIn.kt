package it.polito.did.smartvase

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import it.polito.did.smartvase.databinding.SignInBinding
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import it.polito.did.smartvase.ui.main.MainViewModel


class SignIn : Fragment(R.layout.sign_in) {

    private val viewModel: MainViewModel by activityViewModels<MainViewModel>()

    //private lateinit var binding: SignInBinding

    private lateinit var binding: SignInBinding
    //private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SignInBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
        //return inflater.inflate(R.layout.sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val signIn=view.findViewById<Button>(R.id.entra)
        viewModel.auth = FirebaseAuth.getInstance()

        if(viewModel.auth.currentUser == null){
            findNavController().navigate(R.id.action_signIn_to_registerActivity)
            /*startActivity(Intent(this, RegisterActivity::class.java))
            finish()*/
        }

        //binding = SignInBinding.inflate(layoutInflater)
        //setContentView(binding.root)

        binding.signIn.setOnClickListener{
            findNavController().navigate(R.id.action_signIn_to_registerActivity)
            /*startActivity(Intent(this, RegisterActivity::class.java))
            finish()*/
        }

        binding.signOut.setOnClickListener{
            viewModel.auth.signOut()
            binding.userDetails.text = updateData()
        }

//        //nascondo la Action Bar (barra con il titolo dell'app)
//        supportActionBar?.hide()
        signIn.setOnClickListener { println("aAAAAAAAAAAAAAAa")
            Log.e("TAG", "message")}
    }

    override fun onResume() {
        super.onResume()
        binding.userDetails.text = updateData()
    }

    private fun updateData(): String{
        return "Email: ${viewModel.auth.currentUser?.email}"
    }
}