package com.cavalcantibruno.kawaitodolist.data


import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.cavalcantibruno.kawaitodolist.activities.BoardActivity
import com.cavalcantibruno.kawaitodolist.activities.LoginActivity
import com.cavalcantibruno.kawaitodolist.utilities.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException

class UserCredentialFunctions {

    private val firebaseAuth = FirebaseAuth.getInstance()

    fun userRegistration(email:String,password:String,context: Context){
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
            result->
            Log.i(Constants.USER_CREDENTIALS, "userRegistration: User successfully registered")
            Log.d(Constants.USER_CREDENTIALS, "userRegistration: $result")
            customMessage("User successfully registered",context)

        }.addOnFailureListener {exception ->
            try {
                throw exception
            } catch (errorWeakPassword:FirebaseAuthWeakPasswordException) {
                Log.i(Constants.USER_CREDENTIALS,"userRegistration: ${errorWeakPassword.message}")
                customMessage(errorWeakPassword.message.toString(),context)
            } catch (errorCredentials:FirebaseAuthInvalidCredentialsException) {
                Log.i(Constants.USER_CREDENTIALS,"userRegistration: ${errorCredentials.message}")
                customMessage(errorCredentials.message.toString(),context)
            } catch (errorAlreadyExists:FirebaseAuthUserCollisionException) {
                Log.i(Constants.USER_CREDENTIALS,"userRegistration: ${errorAlreadyExists.message}")
                customMessage(errorAlreadyExists.message.toString(),context)
            }
        }
    }

    fun verifyLoggedUser(context: Context)
    {
        val currentUser = firebaseAuth.currentUser

        if(currentUser!=null)
        {
            startActivity(context,Intent(context,BoardActivity::class.java),null)
        }

    }

    fun startUserLogin(email:String, password: String, context: Context){
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
            val intent = Intent(context,BoardActivity::class.java)
            startActivity(context,intent,null)
        }.addOnFailureListener { loginError ->
            try {
                throw loginError
            }catch (errorInvalidUser: FirebaseAuthInvalidUserException){
                customMessage("User not found",context)
                Log.i(Constants.USER_CREDENTIALS, "startUserLogin: ${errorInvalidUser.message} ")
            }catch (errorCredentials:FirebaseAuthInvalidCredentialsException){
                customMessage("Wrong email or password",context)
                Log.i(Constants.USER_CREDENTIALS, "startUserLogin: ${errorCredentials.message}")
            }
        }
    }

    fun userLogout(context:Context){
        AlertDialog.Builder(context).setTitle("Logout")
            .setMessage("Do you really want to Logout?")
            .setNegativeButton("Cancel"){ _, _ ->}
            .setPositiveButton("Yes"){ _, _ ->
                firebaseAuth.signOut()
                startActivity(context,Intent(context,LoginActivity::class.java),null)
            }
            .create().show()
    }

    //Since this class cannot call the function inside Extensions.kt, had to create another one
    private fun customMessage(message: String, context: Context){
        Toast.makeText(context, "${message}", Toast.LENGTH_LONG).show()
    }
}