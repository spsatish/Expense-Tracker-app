package com.example.projecttry2

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast


class ExpenseDialog(context: Context, private val adapter: ExpenseAdapter) : Dialog(context), View.OnClickListener {
    private lateinit var editTextExpenseName: EditText
    private lateinit var editTextExpenseAmount: EditText
    private lateinit var editTextExpenseCategory: EditText
    private lateinit var buttonSave: Button
    private var expenseAddedListener: ExpenseAddedListener? = null

    fun setOnExpenseAddedListener(listener: MainActivity) {
        this.expenseAddedListener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_expense)

        editTextExpenseName = findViewById(R.id.editText_expense_name)
        editTextExpenseAmount = findViewById(R.id.editText_expense_amount)
        editTextExpenseCategory = findViewById(R.id.editText_expense_category)
        buttonSave = findViewById(R.id.button_save)
        buttonSave.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_save -> {
                val expenseName = editTextExpenseName.text.toString()
                val expenseAmount = editTextExpenseAmount.text.toString()
                val expenseCategory = editTextExpenseCategory.text.toString()

                if (expenseName.isNotEmpty() && expenseAmount.isNotEmpty()) {
                    val amount = expenseAmount.toDoubleOrNull()
                    if (amount != null) {
                        adapter.addExpenseItem(ExpenseItem(expenseName, amount))
                        dismiss()
                        notifyExpenseAdded(amount, expenseCategory)
                    } else {
                        Toast.makeText(context, "Invalid amount entered", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(context, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun notifyExpenseAdded(expenseAmount: Double, expenseCategory: String) {
        expenseAddedListener?.onExpenseAdded(expenseAmount, expenseCategory)
    }
}








