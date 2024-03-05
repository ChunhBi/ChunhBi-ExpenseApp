package com.example.criminalintent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val TAG = "ExpenseListViewModel"
class ExpenseListViewModel : ViewModel() {
    private val expenseRepository = ExpenseRepository.get()

    private val _expenses: MutableStateFlow<List<Expense>> = MutableStateFlow(emptyList())
    val expenses : StateFlow<List<Expense>>
        get() = _expenses.asStateFlow()
    private val _foodExpenses: MutableStateFlow<List<Expense>> = MutableStateFlow(emptyList())
    val foodExpenses : StateFlow<List<Expense>>
        get() = _foodExpenses.asStateFlow()

    private val _entertainmentExpenses: MutableStateFlow<List<Expense>> = MutableStateFlow(emptyList())
    val entertainmentExpenses : StateFlow<List<Expense>>
        get() = _entertainmentExpenses.asStateFlow()

    private val _housingExpenses: MutableStateFlow<List<Expense>> = MutableStateFlow(emptyList())
    val housingExpenses : StateFlow<List<Expense>>
        get() = _housingExpenses.asStateFlow()

    private val _utilitiesExpenses: MutableStateFlow<List<Expense>> = MutableStateFlow(emptyList())
    val utilitiesExpenses : StateFlow<List<Expense>>
        get() = _utilitiesExpenses.asStateFlow()

    private val _fuelExpenses: MutableStateFlow<List<Expense>> = MutableStateFlow(emptyList())
    val fuelExpenses : StateFlow<List<Expense>>
        get() = _fuelExpenses.asStateFlow()
    private val _automotiveExpenses: MutableStateFlow<List<Expense>> = MutableStateFlow(emptyList())
    val automotiveExpenses : StateFlow<List<Expense>>
        get() = _automotiveExpenses.asStateFlow()

    private val _miscExpenses: MutableStateFlow<List<Expense>> = MutableStateFlow(emptyList())
    val miscExpenses : StateFlow<List<Expense>>
        get() = _miscExpenses.asStateFlow()


    init {
        viewModelScope.launch {
            expenseRepository.getExpenses().collect{
                _expenses.value = it
            }
        }
        viewModelScope.launch {
            expenseRepository.getCategorizedExpenses(0).collect{
                _foodExpenses.value = it
            }
        }
        viewModelScope.launch {
            expenseRepository.getCategorizedExpenses(1).collect{
                _entertainmentExpenses.value = it
            }
        }
        viewModelScope.launch {
            expenseRepository.getCategorizedExpenses(2).collect{
                _housingExpenses.value = it
            }
        }
        viewModelScope.launch {
            expenseRepository.getCategorizedExpenses(3).collect{
                _utilitiesExpenses.value = it
            }
        }
        viewModelScope.launch {
            expenseRepository.getCategorizedExpenses(4).collect{
                _fuelExpenses.value = it
            }
        }
        viewModelScope.launch {
            expenseRepository.getCategorizedExpenses(5).collect{
                _automotiveExpenses.value = it
            }
        }
        viewModelScope.launch {
            expenseRepository.getCategorizedExpenses(6).collect{
                _miscExpenses.value = it
            }
        }
    }
    suspend fun addExpense(expense: Expense) {
        expenseRepository.addExpense(expense)
    }
}