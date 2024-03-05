package com.example.criminalintent

import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity

class SpinnerDetailActivity(
    val expenseDetailViewModel: ExpenseDetailViewModel,
) : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        // Code to execute when an item is selected
//        val selectedItem = parent?.getItemAtPosition(position) as String  // Example
        expenseDetailViewModel.updateExpense { oldExpense->
            oldExpense.copy(category = position)
        }
    }
    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Code to execute when no item is selected (optional)
    }
}