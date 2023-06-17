package com.example.meme

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {
    var currentmeme:String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        https://meme-api.com/gimme

        loadmeme()
    }

    private fun loadmeme() {
        val url = "https://meme-api.com/gimme"
        val progress_bar = findViewById<ProgressBar>(R.id.progress_bar)
        progress_bar.visibility = View.VISIBLE
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener { response ->
                val url = response.getString("url")
                currentmeme = url
                val imageView = findViewById<ImageView>(R.id.imageView)
                Glide.with(this).load(url).listener(object: RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progress_bar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progress_bar.visibility = View.GONE
                        return false
                    }
                }).into(imageView)

            },
            Response.ErrorListener { error ->
                // TODO: Handle error
            }
        )

// Access the RequestQueue through your singleton class.
        Mysingleton.MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    fun nextmeme(view :View){
        loadmeme()
    }

    fun sharememe(view: View){
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Hey, Checkout this! I found a cool meme $currentmeme")
        val chooser = Intent.createChooser(intent, "Choose an app")
        startActivity(chooser)
    }
}