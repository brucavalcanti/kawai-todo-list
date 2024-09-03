package com.cavalcantibruno.kawaitodolist.activities

import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.cavalcantibruno.kawaitodolist.adapters.TodoAdapter
import com.cavalcantibruno.kawaitodolist.data.KawaiList
import com.cavalcantibruno.kawaitodolist.data.ListDatabaseFunctions
import com.cavalcantibruno.kawaitodolist.databinding.ActivityListBinding
import com.cavalcantibruno.kawaitodolist.utilities.customMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ListActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityListBinding.inflate(layoutInflater)
    }

    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val firestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private var todoList:KawaiList?=null
    private val userId = firebaseAuth.currentUser?.uid
    private lateinit var todoListAdapter:TodoAdapter

    private val listFuncions = ListDatabaseFunctions()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        retrieveListData()
        initToolbar()
        initClickEvents()
        binding.rvTodoList.setBackgroundColor(Color.parseColor(todoList?.kawaiTheme))
        todoListAdapter = TodoAdapter()
        binding.rvTodoList.adapter = todoListAdapter
        binding.rvTodoList.layoutManager = LinearLayoutManager(this)
        binding.rvTodoList.addItemDecoration(
            DividerItemDecoration(this,LinearLayoutManager.VERTICAL)
        )
    }

    override fun onStart() {
        super.onStart()
        todoList?.name?.let { listFuncions.addTodoListener(todoListAdapter, it) }
    }

    private fun initClickEvents(){
        binding.btnAddTodoItem.setOnClickListener {
            val todoInput = EditText(this)

            val alertBuilder = AlertDialog.Builder(this)
            alertBuilder.setTitle("Create new Item")
            alertBuilder.setView(todoInput)
            alertBuilder.setPositiveButton("Add") { _,_->
                val itemText = todoInput.text.toString()
                if (itemText.isEmpty()) {
                    customMessage("Please insert an item")
                } else {
                    val todoItem = mapOf("item" to itemText)
                    todoList?.let { it1 -> listFuncions.addTodoItem(it1.name,todoItem) }
                }
            }
            alertBuilder.setNegativeButton("Cancel"){_,_ -> }
            alertBuilder.create().show()
        }
    }

    private fun initToolbar()
    {
        val customActionBar = binding.tbTodoList.toolbarPrincipal
        setSupportActionBar(customActionBar)
        supportActionBar?.apply {
            title = todoList?.name
            setDisplayHomeAsUpEnabled(true)

        }
    }


    private fun retrieveListData(){
        val extras = intent.extras
        if(extras!=null) {
            todoList = if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.TIRAMISU)
            {
                extras.getParcelable("todoList",KawaiList::class.java)
            }else {
                extras.getParcelable("todoList")
            }
        }
    }
}