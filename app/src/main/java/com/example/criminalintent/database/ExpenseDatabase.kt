package com.example.criminalintent.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.criminalintent.Expense

@Database(entities = [Expense::class], version=2)
@TypeConverters(ExpenseTypeConverters::class)
abstract class ExpenseDatabase : RoomDatabase() {
    abstract fun expenseDao() : ExpenseDao
}