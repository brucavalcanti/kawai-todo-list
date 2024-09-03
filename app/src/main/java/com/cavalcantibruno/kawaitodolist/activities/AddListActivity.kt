package com.cavalcantibruno.kawaitodolist.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.cavalcantibruno.kawaitodolist.R
import com.cavalcantibruno.kawaitodolist.data.KawaiList
import com.cavalcantibruno.kawaitodolist.data.ListDatabaseFunctions
import com.cavalcantibruno.kawaitodolist.databinding.ActivityAddListBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AddListActivity : AppCompatActivity() {

    private val binding by lazy{
        ActivityAddListBinding.inflate(layoutInflater)
    }

    private val listDatabaseFunctions = ListDatabaseFunctions()
    private lateinit var myColor:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initToolbar()
        generateSpinner()
        clickEvents()
        /*CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                colorSelected()
                delay(1000)
            }
        }*/
    }

    private fun initToolbar() {
        val toolBar = binding.includeToolBar.toolbarPrincipal
        setSupportActionBar(toolBar)
        supportActionBar?.apply {
            title = "Create a new List"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun generateSpinner(){
        val listColors = resources.getStringArray(R.array.ThemeColors)
        binding.listThemeSpinner.adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_dropdown_item,listColors)
        binding.listThemeSpinner.onItemSelectedListener = object:AdapterView.OnItemSelectedListener
        {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                colorSelected()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun colorSelected()
    {

        myColor = binding.listThemeSpinner.selectedItemPosition.toString()
        val spinnerPosition = binding.listThemeSpinner.selectedItemPosition

        myColor = when(spinnerPosition) {
            6 -> "#E1B7E8"
            5 -> "#FBF5BD"
            4 -> "#89D9FD"
            3 -> "#A38B83"
            2 -> "#C1EA92"
            1 -> "#F19696"
            else -> "#959595"
        }
            binding.colorPicker.setBackgroundColor(Color.parseColor(myColor))

    }

    private fun clickEvents()
    {

        binding.btnAddList.setOnClickListener {
            val listName = binding.editNameList.text.toString()
            var todoList = mutableListOf<String>()
            //colorSelected()
            if(listName.isEmpty())
            {
                binding.inputNameList.error ="Insert a name"
            }else {
                val kawaiList = KawaiList(listName, myColor)
                listDatabaseFunctions.createList(kawaiList, this)
                finish()
            }
        }

    }

}