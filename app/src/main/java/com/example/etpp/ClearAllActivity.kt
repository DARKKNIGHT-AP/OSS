package com.example.etpp


import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.math.log


class ClearAllActivity : AppCompatActivity() {

    lateinit var clearAllButton: Button
    lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clear_all)


        Log.i(toString()," befor db code clearall")

        clearAllButton = findViewById(R.id.clear_all_button)
        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "foods")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()

        Log.i(toString(),"after db code clearall")



    }

    fun clearAllData(v: View?) {

        val dialogClickListener =
            DialogInterface.OnClickListener {
                    dialog, which ->
                when (which)
                {
                    DialogInterface.BUTTON_POSITIVE ->
                    {
                        db!!.foodDao().deleteAll()
                        val intent = Intent(this@ClearAllActivity, ListAddActivity::class.java)
                        startActivity(intent)
                    }
                    DialogInterface.BUTTON_NEGATIVE ->
                    {

                    }
                }

            }
        val builder = AlertDialog.Builder(this, R.style.MyDialogTheme)
        builder.setMessage("Once deleted data can not be recovered. Are you sure you want to continue?")
            .setNegativeButton("No", dialogClickListener)
            .setPositiveButton("Yes", dialogClickListener).show()
    }
}
