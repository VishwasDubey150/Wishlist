package com.example.wishlist

import android.app.DatePickerDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import java.util.*

class addwishlist : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addwishlist)
        supportActionBar?.hide()

    }

    fun date(view: android.view.View) {
        val date=findViewById<Button>(R.id.date)
        val mycalender=Calendar.getInstance()
        val year=mycalender.get(Calendar.YEAR)
        val month=mycalender.get(Calendar.MONTH)
        val day=mycalender.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(this,DatePickerDialog.OnDateSetListener {
                view, Syear, Smonth, SdayOfMonth ->
            val selected="$SdayOfMonth/${Smonth+1}/$Syear"
            date.setText(selected)
        },year,month,day).show()
    }
}