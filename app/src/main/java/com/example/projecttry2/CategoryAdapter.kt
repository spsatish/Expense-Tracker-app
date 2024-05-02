package com.example.projecttry2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class CategoryAdapter(private val categories: List<String>, private val percentages: List<Int>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val categoryTextView: TextView = itemView.findViewById(R.id.categoryTextView)
        val percentageTextView: TextView = itemView.findViewById(R.id.averageTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        val percentage = percentages[position]
        holder.categoryTextView.text = category
        holder.percentageTextView.text = holder.itemView.context.getString(R.string.category_percentage, percentage)
    }

    override fun getItemCount(): Int {
        return categories.size
    }
}
