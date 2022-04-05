package com.gonzalezcs.covidnewmodule.data.model
//import androidx.room.ColumnInfo
//import androidx.room.Entity
//import androidx.room.PrimaryKey

//@Entity
data class DataCovidModel (
    /*@PrimaryKey @ColumnInfo(name = "date")*/ val date: String,
    /*@ColumnInfo(name = "confirmed")*/ val confirmed: Int?,
    /*@ColumnInfo(name = "deaths")*/ val deaths: Int?
)