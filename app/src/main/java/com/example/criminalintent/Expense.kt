package com.example.criminalintent
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity
data class Expense (
    @PrimaryKey val id: UUID,
    val title: String,
    val date: Date,
    val category: String
)