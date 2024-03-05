package com.example.criminalintent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.criminalintent.databinding.ListItemExpenseBinding
import java.util.UUID

class ExpenseHolder (
    val binding: ListItemExpenseBinding
) : RecyclerView.ViewHolder(binding.root){
    fun bind(expense: Expense, onExpenseClicked: (expenseId: UUID) -> Unit) {
        binding.expenseTitle.text = expense.title
        binding.expenseDate.text = expense.date.toString()
        binding.root.setOnClickListener {
            onExpenseClicked(expense.id)
        }
    }
}

class ExpenseListAdapter (
    private val expenses: List<Expense>,
    private val onExpenseClicked: (expenseId: UUID) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        when(viewType) {
            1 ->  {
                val binding = ListItemExpenseBinding.inflate(inflater, parent, false)
                return ExpenseHolder(binding)
            }
            else -> {
                val binding = ListItemExpenseBinding.inflate(inflater, parent, false)
                return ExpenseHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val expense = expenses[position]
        when (holder) {
            is ExpenseHolder -> {
                holder.bind(expense, onExpenseClicked)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
//        val expense = expenses[position]
//        return if (expense.requiresPolice) 1 else 0
        return 0
    }

    override fun getItemCount() = expenses.size
}