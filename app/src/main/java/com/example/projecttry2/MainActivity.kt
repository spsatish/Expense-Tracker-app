package com.example.projecttry2

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity(), ExpenseAdapter.OnExpenseItemClickListener,
    ExpenseAddedListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExpenseAdapter
    private val expenseList = mutableListOf<ExpenseItem>()
    private var pressedTime: Long = 0
    private lateinit var totalAmountTextView: TextView
    private val budget = 3000.0


    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        sharedPreferences = getSharedPreferences("expenses", Context.MODE_PRIVATE)

        recyclerView = findViewById(R.id.recyclerView)
        adapter = ExpenseAdapter(expenseList)
        adapter.setOnExpenseItemClickListener(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        totalAmountTextView = findViewById(R.id.text_total_amount)

        val pieImageButton = findViewById<ImageButton>(R.id.button_pie)
        pieImageButton.setOnClickListener {
            val intent = Intent(this, PieChartActivity::class.java)
            startActivity(intent)
        }

        val buttonAdd: ImageButton = findViewById(R.id.button_add)
        buttonAdd.setOnClickListener {
            showExpenseDialog()
        }

        loadExpensesFromSharedPreferences()
        calculateAndAddTotalItem()

        createNotificationChannel()
    }

    override fun onExpenseItemClick(position: Int) {

    }

    override fun onTotalItemClick(totalAmount: Double) {

    }

    @SuppressLint("SetTextI18n")
    private fun calculateAndAddTotalItem() {
        val totalAmount = expenseList.toList().sumOf { it.amount }

        totalAmountTextView.text = ": RS ${String.format("%.2f", totalAmount)}"


        if (totalAmount > budget) {
            showBudgetExceededNotification()
        }
    }

    private fun createNotificationChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val channelName = "Expense Tracker Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("expense_tracker", channelName, importance)
            channel.description = "Channel for expense tracking notifications"
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun showBudgetExceededNotification() {
        val notificationBuilder = NotificationCompat.Builder(this, "expense_tracker")
            .setContentTitle("Budget Exceeded!")
            .setContentText("Your total expense has crossed ₹$budget")
            .setSmallIcon(R.drawable.tracker) // Replace with your notification icon drawable resource
            .setAutoCancel(true)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notificationBuilder.build())
    }

    override fun onExpenseAdded(expenseAmount: Double, expenseCategory: String) {
        calculateAndAddTotalItem()
    }

    @SuppressLint("SetTextI18n")
    override fun onDeleteButtonClick(position: Int) {

        expenseList[position]


        expenseList.removeAt(position)


        adapter.notifyItemRemoved(position)


        val totalAmount = expenseList.toList().sumOf { it.amount }


        totalAmountTextView.text = ": RS ${String.format("%.2f", totalAmount)}"


        saveExpensesToSharedPreferences()


        loadExpensesFromSharedPreferences()
    }



    override fun onPause() {
        super.onPause()
        saveExpensesToSharedPreferences()
    }

    override fun onResume() {
        super.onResume()
        loadExpensesFromSharedPreferences()
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (pressedTime + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
            saveExpensesToSharedPreferences()
            finishAffinity()
        } else {
            Toast.makeText(baseContext, "Press back again to exit", Toast.LENGTH_SHORT).show()
        }
        pressedTime = System.currentTimeMillis()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadExpensesFromSharedPreferences() {
        val savedExpensesJson = sharedPreferences.getString("expenses", null)
        if (savedExpensesJson != null) {
            val type = object : TypeToken<MutableList<ExpenseItem>>() {}.type
            val savedExpenses = Gson().fromJson<MutableList<ExpenseItem>>(savedExpensesJson, type)
            expenseList.clear()
            expenseList.addAll(savedExpenses)
            adapter.notifyDataSetChanged()
        }
    }

    private fun showExpenseDialog() {
        val expenseDialog = ExpenseDialog(this, adapter)
        expenseDialog.setOnExpenseAddedListener(this)
        expenseDialog.show()
    }



    private fun saveExpensesToSharedPreferences() {
        val updatedExpensesJson = Gson().toJson(expenseList)
        sharedPreferences.edit().putString("expenses", updatedExpensesJson).apply()
    }
}
