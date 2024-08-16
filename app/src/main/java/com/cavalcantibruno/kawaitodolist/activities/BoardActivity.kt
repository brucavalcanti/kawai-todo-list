package com.cavalcantibruno.kawaitodolist.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuProvider
import com.cavalcantibruno.kawaitodolist.R
import com.cavalcantibruno.kawaitodolist.data.UserCredentialFunctions
import com.cavalcantibruno.kawaitodolist.databinding.ActivityBoardBinding

class BoardActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityBoardBinding.inflate(layoutInflater)
    }

    val userFunctions = UserCredentialFunctions()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initToolbar()
    }


    private fun initToolbar(){
        val customToolbar = binding.include.toolbarPrincipal
        setSupportActionBar(customToolbar)

        supportActionBar.apply {
            title = "Lists DashBoard"
        }

        addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(R.menu.menu_principal,menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    when(menuItem.itemId){
                        R.id.menuLogout -> {
                            userFunctions.userLogout(this@BoardActivity)
                        }
                    }
                    return true
                }
            }
        )
    }
}