package com.example.a7minworkout

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minworkout.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbarHistoryActivity)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "HISTORY"
        }
        binding.toolbarHistoryActivity.setNavigationOnClickListener {
            onBackPressed()
        }
        getAllCompletedDates()
    }

    private fun getAllCompletedDates() {

        // Instance of the Sqlite Open Helper class.
        val dbHandler = SqliteOpenHelper(this, null)

        val allCompletedDatesList = dbHandler.getAllCompletedDatesList()

        //  We will pass that list to the adapter class which we have created and bind it to the recycler view.)
        // START
        if (allCompletedDatesList.size > 0) {
            // Here if the List size is greater then 0 we will display the item in the recycler view or else we will show the text view that no data is available.
            binding.tvHistory.visibility = View.VISIBLE
            binding.rvHistory.visibility = View.VISIBLE
            binding.tvNoDataAvailable.visibility = View.GONE

            // Creates a vertical Layout Manager
            binding.rvHistory.layoutManager = LinearLayoutManager(this)

            // History adapter is initialized and the list is passed in the param.
            val historyAdapter = HistoryAdapter(allCompletedDatesList)

            // Access the RecyclerView Adapter and load the data into it
            binding.rvHistory.adapter = historyAdapter
        } else {
            binding.tvHistory.visibility = View.GONE
            binding.rvHistory.visibility = View.GONE
            binding.tvNoDataAvailable.visibility = View.VISIBLE
        }
        // END
    }

}