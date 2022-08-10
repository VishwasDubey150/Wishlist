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
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.provider.Settings
import android.widget.Gallery
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.IOException

class addwishlist : AppCompatActivity() {
    companion object {
        private const val CAMERA_PERMISSION_CODE = 1
        private const val CAMERA_REQUIRED_CODE = 2
        private const val GALLERY = 1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addwishlist)
        supportActionBar?.hide()

    }

    fun date(view: android.view.View) {
        val date = findViewById<Button>(R.id.date)
        val mycalender = Calendar.getInstance()
        val year = mycalender.get(Calendar.YEAR)
        val month = mycalender.get(Calendar.MONTH)
        val day = mycalender.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, Syear, Smonth, SdayOfMonth ->
                val selected = "$SdayOfMonth/${Smonth + 1}/$Syear"
                date.setText(selected)
            },
            year,
            month,
            day
        ).show()
    }

    fun addimage(view: android.view.View) {
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Gallery", "Camera")
        pictureDialog.setItems(pictureDialogItems)
        { dialogs, which ->
            when (which) {
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
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_CODE
            )
        }

    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode== GALLERY)
        {
            if(data!=null)
            {
                val contentURI=data.data
                try {
                    val selectedimageBitmape=MediaStore.Images.Media.getBitmap(this.contentResolver,contentURI)
                    di.setImageBitmap(selectedimageBitmape)
                }catch (e:IOException)
                {
                    e.printStackTrace()
                    Toast.makeText(this@addwishlist,"Sorry,failed to load",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun choosePhotofromGalary() {
        Dexter.withActivity(this).withPermissions(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ).withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                if (report?.areAllPermissionsGranted()) {
                    val galleryIntent =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(galleryIntent, GALLERY)
                }

            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: MutableList<PermissionRequest>,
                token: PermissionToken
            ) {
                showRDforpermissions()
            }
        }).onSameThread().check()

    }

    private fun showRDforpermissions() {
        AlertDialog.Builder(this)
            .setMessage("its look like you have turned off permission required fpr this feature.It can be enabled under the Application Settings")
            .setPositiveButton("Allow from setting")
            { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("Package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }.setNegativeButton("Cancel")
            { dialog, _ ->
                dialog.dismiss()
            }.show()
    }
}