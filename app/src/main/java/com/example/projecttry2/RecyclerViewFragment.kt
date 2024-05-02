package com.example.projecttry2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class RecyclerViewFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_recycler_view, container, false)


        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())


        val categories = listOf("Grocery", "Food", "Entertainment", "Travelling")
        val expenses = listOf(200.0, 300.0, 500.0, 150.0)


        val totalExpenses = expenses.sum()


        val percentages = expenses.map { (it / totalExpenses * 100).toInt() }


        adapter = CategoryAdapter(categories, percentages)
        recyclerView.adapter = adapter

        return view
    }
}
