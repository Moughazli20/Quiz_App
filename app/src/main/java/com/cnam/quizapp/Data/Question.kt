package com.cnam.quizapp.Data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.cnam.quizapp.Converters

@Entity(
    foreignKeys = [ForeignKey(
        entity = Category::class,
        parentColumns = arrayOf("name"),
        childColumns = arrayOf("category"),
        onDelete = ForeignKey.CASCADE
    )]
)
data class Question(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val category: String,
    val text: String,
    val correctAnswer: String,
    @TypeConverters(Converters::class) val wrongAnswers: List<String>
)
