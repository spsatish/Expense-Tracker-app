package com.example.projecttry2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExpenseAdapter(private val itemList: MutableList<ExpenseItem>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_EXPENSE = 1
    private val VIEW_TYPE_TOTAL = 2

    interface OnExpenseItemClickListener {
        fun onExpenseItemClick(position: Int)
        fun onTotalItemClick(totalAmount: Double)
        fun onDeleteButtonClick(position: Int)
    }

    private var listener: OnExpenseItemClickListener? = null

    fun setOnExpenseItemClickListener(listener: OnExpenseItemClickListener) {
        this.listener = listener
    }

    fun addExpenseItem(expenseItem: ExpenseItem) {
        itemList.add(expenseItem)
        notifyItemInserted(itemList.size - 1)
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_EXPENSE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_expense, parent, false)
                ExpenseViewHolder(view)
            }
            VIEW_TYPE_TOTAL -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_main, parent, false)
                TotalViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    private fun deleteExpenseItem(position: Int) {
        if (position in itemList.indices) {
            itemList.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            VIEW_TYPE_EXPENSE -> {
                val expenseHolder = holder as ExpenseViewHolder
                val expenseItem = itemList[position]
                expenseHolder.textExpenseName.text = expenseItem.name
                expenseHolder.textExpenseAmount.text = expenseItem.amount.toString()
                expenseHolder.itemView.setOnClickListener {
                    listener?.onExpenseItemClick(position)
                }

                expenseHolder.deleteButton.setOnClickListener {
                    deleteExpenseItem(position)
                }
            }
            VIEW_TYPE_TOTAL -> {
            }
        }
    }

    inner class ExpenseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textExpenseName: TextView = itemView.findViewById(R.id.text_expense_name)
        val textExpenseAmount: TextView = itemView.findViewById(R.id.text_expense_amount)
        val deleteButton: ImageButton = itemView.findViewById(R.id.button_delete)
    }

    override fun getItemViewType(position: Int): Int {
        return VIEW_TYPE_EXPENSE
    }

    override fun getItemCount(): Int {
        return itemList.size
    }



    inner class TotalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
