package com.example.logchatapp3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.localappdata.R
import kotlinx.android.synthetic.main.activity_create_post.*
import org.json.JSONObject

class CreatePost : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_post)
        buttonCreatePost.setOnClickListener {
            val content = editTextContent.text.toString().trim()
            val templateId = "12212.img1"
            if (content.isNotEmpty()) {
                createPost(content, templateId)
            }
        }
    }

    private fun createPost(content: String, templateId: String) {
        val url = "https://slogger.live/api/posts"
        val userId = "65845de0f3aac0d35e7d6172"

        val jsonObject = JSONObject().apply {
            put("content", content)
            put("template_id", templateId)
        }

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.POST, url, jsonObject,
            Response.Listener { response ->
                // Handle successful response (if needed)
                // For example, show a success message or navigate back to the feed activity
            },
            Response.ErrorListener { error ->
                // Handle error
                // For example, show an error message to the user
            }).apply {
            // Add user_id header to the request
            setRequestHeaders(mapOf("user_id" to userId))
        }
        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(jsonObjectRequest)
    }
}
