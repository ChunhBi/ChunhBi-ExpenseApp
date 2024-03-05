package com.example.criminalintent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.criminalintent.databinding.FragmentExpenseListBinding
import kotlinx.coroutines.launch
import java.util.Date
import java.util.UUID

class ExpenseListFragment : Fragment() {
    private var _binding: FragmentExpenseListBinding?=null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val expenseListViewModel: ExpenseListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExpenseListBinding.inflate(inflater, container, false)
        binding.expenseRecyclerView.layoutManager = LinearLayoutManager(context)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                expenseListViewModel.expenses.collect {expenses ->
                    binding.expenseRecyclerView.adapter = ExpenseListAdapter(expenses) {expenseId ->
                        findNavController().navigate(
                            ExpenseListFragmentDirections.showExpenseDetail(expenseId)
                        )
                    }
                }
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.fragment_expense_list, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.new_crime -> {
                showNewCrime()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showNewCrime() {
        viewLifecycleOwner.lifecycleScope.launch {
            val newExpense = Expense(
                id = UUID.randomUUID(),
                title = "",
                amount = 0.00f,
                date = Date(),
                category = 7
            )
            expenseListViewModel.addExpense(newExpense)
            findNavController().navigate(
                ExpenseListFragmentDirections.showExpenseDetail(newExpense.id)
            )
        }
    }
}