package com.example.criminalintent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.criminalintent.databinding.FragmentExpenseDetailBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import java.util.Date

class ExpenseDetailFragment: Fragment() {
    private var _binding: FragmentExpenseDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val args: ExpenseDetailFragmentArgs by navArgs()

    private val expenseDetailViewModel: ExpenseDetailViewModel by viewModels {
        ExpenseDetailViewModelFactory(args.expenseId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            FragmentExpenseDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            expenseTitle.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    // EditText has lost focus
                    val text = expenseTitle.text.toString()
                    expenseDetailViewModel.updateExpense { oldExpense->
                        oldExpense.copy(title = text.toString())
                    }
                }
            }
            expenseAmount.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    // EditText has lost focus
                    val text = expenseAmount.text.toString()
                    try {
                        text.toFloat()
                        expenseDetailViewModel.updateExpense { oldExpense->
                            oldExpense.copy(amount = text.toFloat())
                        }
                    }
                    catch (e: NumberFormatException) {
                        Snackbar.make(expenseAmount, "Amount illegal!", Snackbar.LENGTH_SHORT).show()
                    }
                }
            }
            ArrayAdapter.createFromResource(
                this.root.context,
                R.array.expenses_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                // Specify the layout to use when the list of choices appears.
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Apply the adapter to the spinner.
                categorySpinner.adapter = adapter
            }
            categorySpinner.onItemSelectedListener = SpinnerDetailActivity(expenseDetailViewModel)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                expenseDetailViewModel.expense.collect {expense ->
                    expense?.let { updateUi(it) }
                }
            }
        }
        setFragmentResultListener(
            DatePickerFragment.REQUEST_KEY_DATE
        ) { _, bundle ->
            val newDate =
                bundle.getSerializable(DatePickerFragment.BUNDLE_KEY_DATE) as Date
            expenseDetailViewModel.updateExpense { it.copy(date = newDate) }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUi (expense: Expense) {
        binding.apply {
            if (expenseTitle.text.toString() != expense.title) {
                expenseTitle.setText(expense.title)
            }
//            if (expenseAmount.text.toString().toFloat() != expense.amount) {
                expenseAmount.setText(expense.amount.toString())
//            }
            expenseDate.setOnClickListener {
                findNavController().navigate(
                    ExpenseDetailFragmentDirections.selectDate(expense.date)
                )
            }
            expenseDate.text = expense.date.toString()
            categorySpinner.setSelection(expense.category)
        }
    }
}