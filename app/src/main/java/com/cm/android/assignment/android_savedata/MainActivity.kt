package com.cm.android.assignment.android_savedata

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    val MYPREFERENCES = "MyPrefs"
    val KEY_NAME = "KEY_NAME"
    var sharedpreferences : SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedpreferences = getSharedPreferences(MYPREFERENCES, Context.MODE_PRIVATE)

        findViewById<Button>(R.id.save1).setOnClickListener { view ->
            val textValue: String = findViewById<EditText>(R.id.editName).text.toString()
            val editor = sharedpreferences!!.edit()
            editor.putString(KEY_NAME, textValue.toString())
            editor.commit()

            Snackbar.make(view, "Data save", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        findViewById<Button>(R.id.view1).setOnClickListener { view->
            val getString = sharedpreferences?.getString(KEY_NAME,"DefaultVal")
            findViewById<TextView>(R.id.text1).text = getString

            Snackbar.make(view, getString.toString(), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }
}