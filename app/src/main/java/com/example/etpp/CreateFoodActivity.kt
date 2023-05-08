package com.example.etpp

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.etpp.Helper.convertDateToString
import com.example.etpp.Helper.convertDatetoDBString
import java.util.*


class CreateFoodActivity : AppCompatActivity() {

    lateinit var foodText: EditText
   lateinit var commentText: EditText
   lateinit var dateButton: Button
   lateinit var saveButton: Button
   lateinit var mealSpinner: Spinner
   lateinit var datePickerDialog: DatePickerDialog
   lateinit var db: AppDatabase
   lateinit var dateForDB: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_food)

        foodText= findViewById(R.id.food_text)
        commentText = findViewById(R.id.comment_text)
        dateButton = findViewById(R.id.date_button)
        saveButton = findViewById(R.id.save_button)
        mealSpinner = findViewById(R.id.meal_spinner)

        val newCalendar = Calendar.getInstance()

        Log.i(toString(),"before db createfood")

        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "foods")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()


        Log.i(toString(),"after db createfood")

        val todayDate: Date = Helper.getCurrentDate()
        val button_date: String = Helper.convertDateToString(todayDate)

        dateForDB = convertDatetoDBString(todayDate)

        dateButton.text = button_date

        val mealAdapter: ArrayAdapter<Any?> =
            ArrayAdapter<Any?>(this, android.R.layout.simple_spinner_item, Meals.values())

        mealAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        mealSpinner.adapter = mealAdapter

        datePickerDialog = DatePickerDialog(
            this,
            {
                    view, year, monthOfYear, dayOfMonth ->
                val display_date = "Date: " + dayOfMonth + "/" + (monthOfYear + 1) + "/" + year

                dateButton.text = display_date

                dateForDB =
                    Integer.toString(year) + "-" + Helper.addLeadingZero(
                        Integer.toString(monthOfYear + 1)
                    ) + "-" + Helper.addLeadingZero(
                        Integer.toString(dayOfMonth)
                    )
            },
            newCalendar[Calendar.YEAR],
            newCalendar[Calendar.MONTH],
            newCalendar[Calendar.DAY_OF_MONTH]
        )
        saveButton.setOnClickListener(View.OnClickListener {

            foodText = findViewById(R.id.food_text)

            if (foodText.text.toString().isEmpty())
            {
                val toast = Toast.makeText(
                    this@CreateFoodActivity,
                    R.string.no_food_toast,
                    Toast.LENGTH_SHORT
                )
                toast.show()

                foodText.requestFocus()

            }
            else
            {
                val food = Food(
                    dateForDB!!,
                    mealSpinner.selectedItem.toString(),
                    foodText.text.toString(),
                    commentText.text.toString()
                )
                db!!.foodDao()!!.insertAll(food)
                startActivity(Intent(this@CreateFoodActivity, ListAddActivity::class.java))
            }
        })

    }

    fun showDatePickerDialog(v: View?) {
        datePickerDialog!!.show()
    }
}
