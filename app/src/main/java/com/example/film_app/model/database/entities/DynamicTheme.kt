package com.example.film_app.model.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DynamicTheme(

    @PrimaryKey
    val id : Int,
    val dynamicThemeState : Boolean

)
