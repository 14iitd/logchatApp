package com.example.logchatapp3

//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import com.example.localappdata.R
//
//class FeedActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_feed)
//    }
//}
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.localappdata.R
import org.json.JSONArray
import org.json.JSONObject

class FeedActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FeedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = FeedAdapter()
        recyclerView.adapter = adapter

        callFeedApi()
    }

    private fun callFeedApi() {
        val url = "https://slogger.live/api/feed/121212"

        val jsonObject = JSONObject().apply {
            put("email", "asa@abhish")
            put("name", "this is first post")
        }

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, jsonObject,
            Response.Listener { response ->
                processResponse(response)
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "API Failed: ${error.message}", Toast.LENGTH_SHORT).show()
            })

        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(jsonObjectRequest)
    }

    private fun processResponse(response: JSONObject) {
        val dataList = mutableListOf<String>()

        // Example: Parsing data from API response
        val jsonArray = response.optJSONArray("data")
        jsonArray?.let {
            for (i in 0 until it.length()) {
                val item: JSONObject = it.optJSONObject(i)
                val dataItem = item.optString("data_key")
                dataList.add(dataItem)
            }
        }

        // Update RecyclerView with obtained data
        adapter.setData(dataList)
    }
}
