package com.example.criminalintent

import android.content.Context
import androidx.room.Room
import com.example.criminalintent.database.ExpenseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.IllegalStateException
import java.util.Date
import java.util.UUID


private const val DATABASE_NAME = "expense-database"


class ExpenseRepository private constructor(
    context: Context,
    private val coroutineScope: CoroutineScope = GlobalScope
){
    private val database: ExpenseDatabase = Room.databaseBuilder(
        context.applicationContext,
        ExpenseDatabase::class.java,
        DATABASE_NAME
    )
//    .createFromAsset(DATABASE_NAME)
    .fallbackToDestructiveMigration()
    .build()

    private val _expenses: MutableStateFlow<List<Expense>> = MutableStateFlow(emptyList())
    val expenses : StateFlow<List<Expense>>
        get() = _expenses.asStateFlow()

    fun getExpenses() : Flow<List<Expense>> = database.expenseDao().getExpenses()

    suspend fun getExpense(id:UUID) : Expense = database.expenseDao().getExpense(id)


    fun updateExpense(expense: Expense) {
        coroutineScope.launch {
            database.expenseDao().updateExpense(expense)
        }
    }
    suspend fun addExpense(expense: Expense) {
        database.expenseDao().addExpense(expense)
    }

    companion object {
        private var INSTANCE: ExpenseRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = ExpenseRepository(context)
            }
        }

        fun get():ExpenseRepository {
            return INSTANCE ?:
            throw IllegalStateException("ExpenseRepository must be initialized")
        }
    }

}