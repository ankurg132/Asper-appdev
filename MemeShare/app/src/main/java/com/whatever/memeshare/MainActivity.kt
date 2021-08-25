package com.whatever.memeshare

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import org.json.JSONObject

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        volleycall()
    }
    var urlJson: String? = null
    private fun volleycall(){

        val imageView = findViewById<ImageView>(R.id.imageView)
        val progressbar = findViewById<ProgressBar>(R.id.progressbar)
        progressbar.visibility = View.VISIBLE
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"


// Request a string response from the provided URL.
        val jsonReq = JsonObjectRequest(Request.Method.GET, url, null,
                Response.Listener<JSONObject> { response ->
                    // Display the first 500 characters of the response string.
                    //Log.d("Success",response.substring(0,500))
                    urlJson = response.getString("url")
                    Glide.with(this).load(urlJson).listener(
                            object:RequestListener<Drawable>{
                                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                                    progressbar.visibility = View.GONE
                                    return false
                                }

                                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                                    progressbar.visibility = View.GONE
                                    return false
                                }
                            }
                    ).into(imageView)
                },
                Response.ErrorListener {
                    //Log.d("Error",it.localizedMessage)
                    Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show()
                })

// Add the request to the RequestQueue.
        queue.add(jsonReq)
    }
    fun sharebtnclick(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Checkout this meme I got from Reddit: $urlJson")
        val chooser = Intent.createChooser(intent,"Share this meme on..")
        startActivity(chooser)
    }
    fun nextbtnclick(view: View) {
        volleycall()
    }
}