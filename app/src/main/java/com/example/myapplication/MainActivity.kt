package com.example.myapplication

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        save.setOnClickListener {
            val name = name.text.toString()
            val number = number.text.toString()
            val address = address.text.toString()

            save(name, number, address)

        }
        read.setOnClickListener {
            read()
        }
        delete.setOnClickListener {
            delete()
        }
    }

    fun save(name: String, number: String, address: String) {
        val db = FirebaseFirestore.getInstance()
        val user: MutableMap<String, Any> = HashMap()
        user["name"] = name
        user["number"] = number
        user["address"] = address

        db.collection("users")
            .add(user)
            .addOnSuccessListener {
                Toast.makeText(this@MainActivity, "record added successfully ", Toast.LENGTH_SHORT)
                    .show()
            }
            .addOnFailureListener {
                Toast.makeText(this@MainActivity, "record Failed to add ", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    fun read() {
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .get()
            .addOnCompleteListener {

                val result: StringBuffer = StringBuffer()

                if (it.isSuccessful) {
                    for (document in it.result!!) {
                        result.append(document.data.getValue("name")).append(" \n")
                            .append(document.data.getValue("number")).append("\n")
                            .append(document.data.getValue("address")).append("\n\n")
                    }
                    textViewResult.setText(result)
                }
            }
    }
    fun delete(){
        val db = FirebaseFirestore.getInstance()

        db.collection("users").document("users")
            .delete()
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error deleting document", e) }
    }
}



