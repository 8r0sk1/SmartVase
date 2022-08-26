package it.polito.did.smartvase

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionInflater
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.SignInButton
import com.google.android.gms.common.api.ApiException
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.GoogleAuthProvider
import it.polito.did.smartvase.ui.main.MainViewModel

class RegisterActivity : Fragment(R.layout.activity_register) {

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
        return inflater.inflate(R.layout.activity_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val loginTV=view.findViewById<TextView>(R.id.loginTV)
        val createAccountBtn=view.findViewById<MaterialButton>(R.id.createAccountBtn)
        val googleBtn=view.findViewById<SignInButton>(R.id.googleBtn)
        val emailRegister=view.findViewById<TextInputEditText>(R.id.emailRegister)
        val passwordRegister=view.findViewById<TextInputEditText>(R.id.passwordRegister)

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.client_id))
            .requestEmail()
            .build()

        val googleSignInClient = GoogleSignIn.getClient(this@RegisterActivity.requireActivity(), gso)

        loginTV.setOnClickListener{
            findNavController().navigate(R.id.action_registerActivity_to_loginActivity)
            /*startActivity(Intent(this, LoginActivity::class.java))
            finish()*/
        }

        createAccountBtn.setOnClickListener{
            val email = emailRegister.text.toString()
            val password = passwordRegister.text.toString()
            if(email.isNotEmpty() && password.isNotEmpty())
                viewModel.auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
                    if(it.isSuccessful){
                        findNavController().navigate(R.id.action_registerActivity_to_loginActivity)
                        /*startActivity(Intent(this, LoginActivity::class.java))
                        finish()*/
                    }
                }.addOnFailureListener{
                    Toast.makeText(this@RegisterActivity.requireActivity(), it.localizedMessage, Toast.LENGTH_LONG).show()
                }
        }

        googleBtn.setOnClickListener{
            googleSignInClient.signOut()
            startActivityForResult(googleSignInClient.signInIntent, 13)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 13 && resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val account = task.getResult(ApiException::class.java)!!
            firebaseAuthWithGoogle(account.idToken!!)
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        viewModel.auth.signInWithCredential(credential)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    findNavController().navigate(R.id.action_registerActivity_to_signIn)
                    /*startActivity(Intent(this, SignIn::class.java))
                    finish()*/
                }
            }.addOnFailureListener {
                Toast.makeText(this@RegisterActivity.requireActivity(), it.localizedMessage, Toast.LENGTH_LONG).show()
            }
    }
}