package com.example.projecttry2

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.eazegraph.lib.charts.PieChart
import org.eazegraph.lib.models.PieModel

class PieChartFragment : Fragment() {

    private val categories = listOf("Grocery", "Travelling", "Entertainment", "Food")
    private val expenses = listOf(200.0, 300.0, 500.0, 150.0)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_pie_chart, container, false)


        val mPieChart = view.findViewById(R.id.piechart) as PieChart


        val totalExpenses = expenses.sum()


        for (i in categories.indices) {
            val percentage = if (totalExpenses != 0.0) {
                ((expenses[i] / totalExpenses) * 100).toFloat()
            } else {
                0f
            }
            mPieChart.addPieSlice(PieModel(categories[i], percentage, randomColor()))
        }

        mPieChart.startAnimation()

        return view
    }


    private fun randomColor(): Int {
        val r = (50..200).random()
        val g = (50..200).random()
        val b = (50..200).random()
        return Color.rgb(r, g, b)
    }
}
