package it.polito.did.smartvase

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import it.polito.did.smartvase.databinding.SignInBinding


class SignIn : AppCompatActivity() {

    private lateinit var binding: SignInBinding

    companion object{
        lateinit var auth: FirebaseAuth
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        if(auth.currentUser == null){
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        binding = SignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signIn.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        binding.signOut.setOnClickListener{
            auth.signOut()
            binding.userDetails.text = updateData()
        }

//        //nascondo la Action Bar (barra con il titolo dell'app)
//        supportActionBar?.hide()

    }

    override fun onResume() {
        super.onResume()
        binding.userDetails.text = updateData()
    }

    private fun updateData(): String{
        return "Email: ${auth.currentUser?.email}"
    }
}