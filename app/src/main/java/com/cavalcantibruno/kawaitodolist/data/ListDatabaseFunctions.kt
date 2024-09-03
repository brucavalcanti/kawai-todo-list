package com.cavalcantibruno.kawaitodolist.data

import android.content.Context
import android.widget.Toast
import com.cavalcantibruno.kawaitodolist.adapters.ListsAdapter
import com.cavalcantibruno.kawaitodolist.adapters.TodoAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.toObject

class ListDatabaseFunctions {

    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val currentUserId = firebaseAuth.currentUser?.uid
    private lateinit var myUserSnapshotEvent: ListenerRegistration

    fun createList(kawaiList: KawaiList,context: Context)
    {
        firestore.collection("users").document(currentUserId!!)
            .collection("lists").document(kawaiList.name)
            .set(kawaiList).addOnSuccessListener {
                customMessage("List created with success",context)
            }
    }

    fun deleteList(listName: String)
    {
            firestore.collection("users").document(currentUserId!!)
                .collection("lists").document(listName).delete()
    }

    /*Função que servirá para recuperar as Listas que estão cadastradas na database*/
     fun addListListener(listAdapter: ListsAdapter) {
        /*A variável serve para depois ser utilizada no onDestroy, evitando que continue sendo
        * recuperado os dados da database mesmo após a activity ser encerrada*/
        myUserSnapshotEvent = firestore.collection("users")
            .document(currentUserId!!)
            .collection("lists")
            .addSnapshotListener { querySnapshot, error ->
                val kawaiTodoList = mutableListOf<KawaiList>()
                val documents = querySnapshot?.documents

                documents?.forEach {documentSnapshot ->
                    val list = documentSnapshot.toObject(KawaiList::class.java)
                    if (list != null) {
                        kawaiTodoList.add(list)
                    }
                }
                //Atualizar a lista de contatos do recyclerview
                if(kawaiTodoList.isNotEmpty()) {
                    listAdapter.addList(kawaiTodoList)
                }
            }
    }


    fun addTodoListener(todoAdapter: TodoAdapter,listName: String){
        myUserSnapshotEvent = firestore.collection("users")
            .document(currentUserId!!).collection("lists")
            .document(listName).collection("todos")
            .addSnapshotListener { querySnapshot, error ->
                val todoItems = mutableListOf<TodoItem>()
                val documents = querySnapshot?.documents
                documents?.forEach { documentSnapshot ->
                    val itemTodo = documentSnapshot.toObject<TodoItem>()
                    if (itemTodo != null) {
                       todoItems.add(itemTodo)
                    }
                }
                if(todoItems.isNotEmpty())
                {
                    todoAdapter.addList(todoItems)
                }
            }
    }

     fun addTodoItem(listName: String, item: Map<String, String>){
        if(listName!=null) {
            firestore.collection("users").document(currentUserId!!)
                .collection("lists").document(listName)
                .collection("todos")
                .add(item)
        }
    }


    private fun customMessage(message:String,context: Context){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}