package com.example.projecttry2

interface ExpenseAddedListener {
    fun onExpenseAdded(expenseAmount: Double, expenseCategory: String)
}
