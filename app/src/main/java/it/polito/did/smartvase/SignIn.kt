package it.polito.did.smartvase

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import it.polito.did.smartvase.databinding.SignInBinding
import android.util.Log


class SignIn : AppCompatActivity() {

    private lateinit var binding: SignInBinding
    lateinit var signIn: MaterialButton

    companion object{
        lateinit var auth: FirebaseAuth
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in)
        signIn=findViewById(R.id.entra)
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
        signIn.setOnClickListener { println("aAAAAAAAAAAAAAAa")
            Log.e("TAG", "message")}
    }

    override fun onResume() {
        super.onResume()
        binding.userDetails.text = updateData()
    }

    private fun updateData(): String{
        return "Email: ${auth.currentUser?.email}"
    }
}