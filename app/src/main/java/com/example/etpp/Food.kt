package com.example.etpp


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey



@Entity(tableName = "foods")
class Food(



    @field:ColumnInfo(name = "date")
    var date: String,

    @field:ColumnInfo(name = "meal")
    var meal: String,

    @field:ColumnInfo(name = "food")
    var food: String,

    @field:ColumnInfo(name = "comment")
    var comment: String

){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
