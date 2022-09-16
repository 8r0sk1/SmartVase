package it.polito.did.smartvase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import it.polito.did.smartvase.ui.main.MainViewModel

class LoginActivity : Fragment(R.layout.activity_login) {

    private val viewModel: MainViewModel by activityViewModels<MainViewModel>()

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
        return inflater.inflate(R.layout.activity_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val registerTV=view.findViewById<TextView>(R.id.registerTV)
        val loginBtn=view.findViewById<MaterialButton>(R.id.loginBtn)
        val emailLogin=view.findViewById<TextInputEditText>(R.id.emailLogin)
        val passwordLogin=view.findViewById<TextInputEditText>(R.id.passwordLogin)
        val loading=view.findViewById<RelativeLayout>(R.id.loadingPanell)

        //supportActionBar?.title = "Login"

        registerTV.setOnClickListener{
            findNavController().navigate(R.id.action_loginActivity_to_registerActivity)
            /*startActivity(Intent(this, RegisterActivity::class.java))
            finish()*/
        }

        loginBtn.setOnClickListener{
            loginBtn.isClickable=false
            registerTV.isClickable=false
            //(activity as MainActivity).writeInternalStorage("0;0")
            loading.visibility=View.VISIBLE
            val email = emailLogin.text.toString()
            val password = passwordLogin.text.toString()
            if(email.isNotEmpty() && password.isNotEmpty())
                viewModel.auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                    if(it.isSuccessful){
                        viewModel.loggedIn = true
                        viewModel.idUtente = viewModel.auth.currentUser?.uid
                        (activity as MainActivity).writeInternalStorage(email+";"+password)
                        findNavController().navigate(R.id.action_loginActivity_to_homepage)
                        //findNavController().navigate(R.id.action_loginActivity_to_signIn)
                        /*startActivity(Intent(this, SignIn::class.java))
                        finish()*/
                    }
                    else loading.visibility=View.INVISIBLE
                }.addOnFailureListener{
                    loading.visibility=View.VISIBLE
                    Toast.makeText(this@LoginActivity.requireActivity(), it.localizedMessage, Toast.LENGTH_LONG).show()
                }
            else {
                loading.visibility=View.INVISIBLE
                loginBtn.isClickable=true
                registerTV.isClickable=true
            }
        }
    }

}