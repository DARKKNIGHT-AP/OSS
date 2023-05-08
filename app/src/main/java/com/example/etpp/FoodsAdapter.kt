package com.example.etpp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.etpp.Helper.convertDateFormatOlder


class FoodsAdapter(context: Context?, foods: ArrayList<Food?>?)
    :
    ArrayAdapter<Food?>(context!!, 0, foods!!)
{
    override fun getView(position: Int, listItemView: View?, parent: ViewGroup)
    : View {

        var listItemView = listItemView

        if (listItemView == null)
        {
            listItemView = LayoutInflater.from(context).inflate(R.layout.food_item, parent, false)
        }

        val currentFood = getItem(position)

        val dateListItem = listItemView!!.findViewById<TextView>(R.id.food_item_date)
        dateListItem.text = convertDateFormatOlder(currentFood!!.date)

        val foodListItem = listItemView.findViewById<TextView>(R.id.food_item_food)
        foodListItem.text = currentFood.food

        val mealListItem = listItemView.findViewById<TextView>(R.id.food_item_meal)
        mealListItem.text = currentFood.meal

        listItemView.tag = currentFood.id

        return listItemView
    }
}
