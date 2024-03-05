package com.example.criminalintent
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.temporal.TemporalAmount
import java.util.Date
import java.util.UUID

@Entity
data class Expense (
    @PrimaryKey val id: UUID,
    val title: String,
    val amount: Float,
    val date: Date,
    val category: Int
)