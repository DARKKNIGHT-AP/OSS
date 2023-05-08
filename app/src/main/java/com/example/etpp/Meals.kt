package com.example.etpp


enum class Meals(private val mealName: String) {
    BREAKFAST("Breakfast"), LUNCH("Lunch"), DINNER("Dinner"), SNACK("Snack"), OTHER("Other");

    override fun toString(): String {
        return mealName
    }
}
