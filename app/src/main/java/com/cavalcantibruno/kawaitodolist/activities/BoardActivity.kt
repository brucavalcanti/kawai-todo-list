package com.cavalcantibruno.kawaitodolist.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.view.MenuProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cavalcantibruno.kawaitodolist.R
import com.cavalcantibruno.kawaitodolist.adapters.ListsAdapter
import com.cavalcantibruno.kawaitodolist.data.KawaiList
import com.cavalcantibruno.kawaitodolist.data.ListDatabaseFunctions
import com.cavalcantibruno.kawaitodolist.data.UserCredentialFunctions
import com.cavalcantibruno.kawaitodolist.databinding.ActivityBoardBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class BoardActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityBoardBinding.inflate(layoutInflater)
    }
    private val firebaseAuth = FirebaseAuth.getInstance()

    private lateinit var listsAdapter: ListsAdapter
    val userFunctions = UserCredentialFunctions()
    val listDatabaseFunctions = ListDatabaseFunctions()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initToolbar()
        clickEvents()
        listsAdapter = ListsAdapter{kawaiList ->
            val intent = Intent(this,ListActivity::class.java)
            intent.putExtra("todoList",kawaiList)
            startActivity(intent)
        }
        binding.rvLists.adapter = listsAdapter
        binding.rvLists.layoutManager = LinearLayoutManager(this)
        binding.rvLists.addItemDecoration(
            DividerItemDecoration(this,LinearLayoutManager.VERTICAL)
        )

    }

    override fun onStart() {
        super.onStart()
        listDatabaseFunctions.addListListener(listsAdapter)
    }

    private fun clickEvents() {
        binding.btnAddNote.setOnClickListener {
            val intent = Intent(this,AddListActivity::class.java)
            startActivity(intent)
        }
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