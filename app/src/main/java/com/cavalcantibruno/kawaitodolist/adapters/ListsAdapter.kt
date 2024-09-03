package com.cavalcantibruno.kawaitodolist.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.cavalcantibruno.kawaitodolist.data.KawaiList
import com.cavalcantibruno.kawaitodolist.data.ListDatabaseFunctions
import com.cavalcantibruno.kawaitodolist.databinding.ItemListBinding
import kotlinx.coroutines.NonDisposableHandle.parent

class ListsAdapter(private val onClick:(KawaiList)->Unit) : RecyclerView.Adapter<ListsAdapter.ListsViewHolder>() {

    private var listKawaiLists = emptyList<KawaiList>()
    private val listDatabaseFunction = ListDatabaseFunctions()
    fun addList(list:List<KawaiList>){
        listKawaiLists = list
        notifyDataSetChanged()
    }

    inner class ListsViewHolder(private val binding:ItemListBinding):ViewHolder(binding.root) {

        fun bind(myList:KawaiList)
        {
            binding.listName.text = myList.name
            binding.layoutList.setBackgroundColor(Color.parseColor(myList.kawaiTheme)
            )
            binding.btnDeleteList.setOnClickListener {
                listDatabaseFunction.deleteList(myList.name)
            }
            binding.layoutList.setOnClickListener {
                onClick(myList)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListsAdapter.ListsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = ItemListBinding.inflate(inflater,parent,false)
        return ListsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListsAdapter.ListsViewHolder, position: Int) {
        val myKawaiList = listKawaiLists[position]
        holder.bind(myKawaiList)
    }

    override fun getItemCount(): Int {
        return listKawaiLists.size
    }


}