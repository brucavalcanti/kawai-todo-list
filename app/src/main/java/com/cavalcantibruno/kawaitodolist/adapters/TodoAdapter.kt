package com.cavalcantibruno.kawaitodolist.adapters

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.cavalcantibruno.kawaitodolist.data.TodoItem
import com.cavalcantibruno.kawaitodolist.databinding.ItemTodoBinding

class TodoAdapter():RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    private var todoItemList = emptyList<TodoItem>()

    fun addList(list: MutableList<TodoItem>){
        todoItemList = list
        notifyDataSetChanged()
    }

    inner class TodoViewHolder(private val binding: ItemTodoBinding):ViewHolder(binding.root){
        private var check=false
      fun bind(todoList:TodoItem){
          binding.todoItem.text = todoList.item
          binding.todoCard.setOnClickListener {
              if(!check) {
                  binding.todoItem.apply {
                      paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                  }
                  check=true
              }else
              {
                  binding.todoItem.apply {
                      paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
                  }
                  check=false
              }
          }
      }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = ItemTodoBinding.inflate(inflater,parent,false)
        return TodoViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return todoItemList.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val myTodoItemList = todoItemList[position]
        holder.bind(myTodoItemList)
    }
}