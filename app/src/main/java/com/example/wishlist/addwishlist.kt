package com.example.wishlist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class addwishlist : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addwishlist)
        supportActionBar?.hide()
    }
}