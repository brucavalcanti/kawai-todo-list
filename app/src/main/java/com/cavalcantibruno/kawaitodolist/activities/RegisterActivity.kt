package com.cavalcantibruno.kawaitodolist.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cavalcantibruno.kawaitodolist.data.UserCredentialFunctions
import com.cavalcantibruno.kawaitodolist.databinding.ActivityRegisterBinding
import com.cavalcantibruno.kawaitodolist.utilities.customMessage

class RegisterActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    private lateinit var registerEmail:String
    private lateinit var registerPassword:String
    private val userRegister = UserCredentialFunctions()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initToolBar()
        clickEvents()
        setContentView(binding.root)
    }


    private fun initToolBar(){
        val toolBar = binding.includeToolBar.toolbarPrincipal
        setSupportActionBar(toolBar)
        supportActionBar?.apply {
            title = "Make your register"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun clickEvents(){
        binding.btnSignUp.setOnClickListener {
            if(validateFields()) {
                userRegister.userRegistration(registerEmail, registerPassword,this)
                finish()
            }
        }
    }

    private fun validateFields():Boolean
    {
        registerEmail = binding.editInputEmail.text.toString()
        registerPassword = binding.editInputPassword.text.toString()

        binding.inputEmail.error = null
        binding.inputPassword.error = null

        return if(registerEmail.isNotEmpty() && registerPassword.isNotEmpty()) {
            true
        }else {
            if(registerEmail.isEmpty()) {
                binding.inputEmail.error = "Please insert an email"
            }
            if(registerPassword.isEmpty()){
                binding.inputPassword.error = "Please insert a password"
            }
            false
        }
    }
}