package com.example.etpp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.etpp.Helper.convertDateFormatOlder



class FoodsMealAdapter(context: Context?, foods: ArrayList<Food?>?)
    : ArrayAdapter<Food?>(context!!, 0, foods!!)
{
    override fun getView(position: Int, listItemView: View?, parent: ViewGroup)
    : View {

        var listItemView = listItemView
        if (listItemView == null)
        {
            listItemView =
                LayoutInflater.from(context).inflate(R.layout.food_by_meal_item, parent, false)
        }
        val currentFood = getItem(position)

        val foodListItem = listItemView!!.findViewById<TextView>(R.id.food_by_meal_text)
        foodListItem.text = currentFood!!.food

        val dateListItem = listItemView.findViewById<TextView>(R.id.date_by_meal_text)
        dateListItem.text = convertDateFormatOlder(currentFood.date)
        return listItemView
    }
}
