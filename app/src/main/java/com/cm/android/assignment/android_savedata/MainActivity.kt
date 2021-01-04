package com.cm.android.assignment.android_savedata

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import java.io.*

class MainActivity : AppCompatActivity() {

    val MYPREFERENCES = "MyPrefs"
    val KEY_NAME = "KEY_NAME"
    var sharedpreferences : SharedPreferences? = null

    private val filepath = "MyFileStorage"
    internal var myExternalFile: File?=null
    private val isExternalStorageReadOnly: Boolean get() {
        val extStorageState = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED_READ_ONLY == extStorageState
    }
    private val isExternalStorageAvailable: Boolean get() {
        val extStorageState = Environment.getExternalStorageState()
        return Environment.MEDIA_MOUNTED == extStorageState
    }

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

        findViewById<Button>(R.id.save2).setOnClickListener { view ->
            myExternalFile = File(getExternalFilesDir(filepath), findViewById<EditText>(R.id.editfileName).text.toString())
            try {
                val fileOutPutStream = FileOutputStream(myExternalFile)
                fileOutPutStream.write(findViewById<EditText>(R.id.editfilePath).text.toString().toByteArray())
                fileOutPutStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            Snackbar.make(view, "Data save", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }


        findViewById<Button>(R.id.view2).setOnClickListener { view ->
            myExternalFile = File(getExternalFilesDir(filepath), findViewById<EditText>(R.id.editfileName).text.toString())

            val filename = findViewById<EditText>(R.id.editfileName).text.toString()

            if(filename.toString().trim()!=""){
                val fileInputStream = FileInputStream(myExternalFile)
                val inputStreamReader: InputStreamReader = InputStreamReader(fileInputStream)
                val bufferedReader: BufferedReader = BufferedReader(inputStreamReader)
                val stringBuilder: StringBuilder = StringBuilder()
                var text: String? = null
                while ({ text = bufferedReader.readLine(); text }() != null) {
                    stringBuilder.append(text)
                }
                fileInputStream.close()

                findViewById<TextView>(R.id.text2).text = stringBuilder

                Snackbar.make(view, stringBuilder.toString(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show()
            }
        }

        if (!isExternalStorageAvailable || isExternalStorageReadOnly) {
            findViewById<Button>(R.id.save2).isEnabled = false
        }
    }
}