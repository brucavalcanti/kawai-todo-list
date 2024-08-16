package com.cavalcantibruno.kawaitodolist.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cavalcantibruno.kawaitodolist.data.UserCredentialFunctions
import com.cavalcantibruno.kawaitodolist.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private val binding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private val userCredentials = UserCredentialFunctions()
    private lateinit var editEmail:String
    private lateinit var editPassword:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        clickEvents()
    }

    private fun clickEvents(){
        binding.textSignUp.setOnClickListener {
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            if(validadeLoginFields())
            {
                userCredentials.startUserLogin(editEmail,editPassword,this)
            }
        }

    }

    override fun onStart() {
        super.onStart()
        userCredentials.verifyLoggedUser(this)
    }

    private fun validadeLoginFields():Boolean{
         editEmail = binding.editLoginEmail.text.toString()
         editPassword = binding.editLoginPassword.text.toString()

        binding.inputLoginEmail.error = null
        binding.editLoginPassword.error =null

        return if(editEmail.isNotEmpty() && editPassword.isNotEmpty()) {
            true
        } else {
            if(editEmail.isEmpty()) {
                binding.inputLoginEmail.error = "Please insert an email"
            }
            if(editPassword.isEmpty()) {
                binding.inputLoginPassword.error = "Please insert a password"
            }
            false
        }
    }

}