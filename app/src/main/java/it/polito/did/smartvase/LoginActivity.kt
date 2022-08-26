package it.polito.did.smartvase

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import it.polito.did.smartvase.databinding.ActivityLoginBinding
import it.polito.did.smartvase.ui.main.MainViewModel

class LoginActivity : Fragment(R.layout.activity_login) {

    private val viewModel: MainViewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = ActivityLoginBinding.inflate(layoutInflater)

        //supportActionBar?.title = "Login"

        binding.registerTV.setOnClickListener{
            findNavController().navigate(R.id.action_loginActivity_to_registerActivity)
            /*startActivity(Intent(this, RegisterActivity::class.java))
            finish()*/
        }

        binding.loginBtn.setOnClickListener{
            val email = binding.emailLogin.text.toString()
            val password = binding.passwordLogin.text.toString()
            if(email.isNotEmpty() && password.isNotEmpty())
                viewModel.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                    if(it.isSuccessful){
                        findNavController().navigate(R.id.action_loginActivity_to_signIn)
                        /*startActivity(Intent(this, SignIn::class.java))
                        finish()*/
                    }
                }.addOnFailureListener{
                    Toast.makeText(this@LoginActivity.requireActivity(), it.localizedMessage, Toast.LENGTH_LONG).show()
                }
        }
    }
}