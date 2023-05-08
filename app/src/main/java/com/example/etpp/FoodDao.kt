package com.example.etpp


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query



//Use data access objects to access data
@Dao
interface FoodDao {
    @Insert
    fun insertAll(vararg foods: Food?)

    @get:Query("SELECT * FROM foods ORDER BY date(date) DESC")
    val allFoods: List<Food?>?

    @Query("SELECT * FROM foods WHERE date =:dateString")
    fun findByDay(dateString: String?): List<Food?>?

    @Query("SELECT * FROM foods WHERE meal =:mealString ORDER BY date DESC")
    fun findByMeal(mealString: String?): List<Food?>?

    @Query("SELECT * FROM foods WHERE id =:foodID")
    fun findByID(foodID: Int): Food?

    @Query("DELETE FROM foods WHERE id =:foodID")
    fun deleteByID(foodID: Int)

    @Query("SELECT * FROM foods WHERE food LIKE :word ORDER BY date DESC")
    fun findBySearch(word: String?): List<Food?>?

    @Query("DELETE FROM foods")
    fun deleteAll()

    @Query("SELECT count(*) FROM foods WHERE meal = :meal AND (date BETWEEN :date1 AND :date2)")
    fun getCountByMealAndDate(meal: String?, date1: String?, date2: String?): Int
}
