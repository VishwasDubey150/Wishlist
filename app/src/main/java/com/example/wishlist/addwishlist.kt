package com.example.wishlist

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.pm.PackageManager
import android.graphics.Camera
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.PackageManagerCompat
import com.karumi.dexter.Dexter
import java.util.*
import android.Manifest
import android.content.Intent
import android.provider.MediaStore
import androidx.core.app.ActivityCompat

class addwishlist : AppCompatActivity() {

    companion object{
        private const val CAMERA_PERMISSION_CODE=1
        private const val CAMERA_REQUIRED_CODE=2
    }


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

    fun addimage(view: android.view.View) {
        val pictureDialog=AlertDialog.Builder(this)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems= arrayOf("Gallery","Camera")
        pictureDialog.setItems(pictureDialogItems)
        {
            dialogs, which -> when(which) {
            0 -> choosePhotofromGalary()
            1 -> Opencamera()
            }
        }
        pictureDialog.show()
    }

    private fun Opencamera() {
        if (ContextCompat.checkSelfPermission(
                this, Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, CAMERA_REQUIRED_CODE)
        }
        else
        {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_CODE)
        }

    }
    private fun choosePhotofromGalary()
    {

    }


}