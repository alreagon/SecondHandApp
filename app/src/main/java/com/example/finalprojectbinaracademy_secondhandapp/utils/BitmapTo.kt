package com.example.finalprojectbinaracademy_secondhandapp.utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.util.Base64
import android.widget.Toast
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class BitmapTo(private val context: Context) {

    fun bitmapToString(bitmap: Bitmap): String {
        val byteArrayStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG,100, byteArrayStream)
        Bitmap.createScaledBitmap(bitmap, 150,150,false)
        val toByteArray = byteArrayStream.toByteArray()

        return Base64.encodeToString(toByteArray, Base64.DEFAULT)
    }

    fun bitmapToFile(bitmap: Bitmap, fileNameToSave: String): File? { // File name like "image.png"
        //create a file to write bitmap data
        var file: File? = null
        return try {
//            Toast.makeText(requireContext(),requireActivity().filesDir.toString(),Toast.LENGTH_SHORT).show()
            file = File(context.filesDir.toString() + File.separator + fileNameToSave)
            file.createNewFile()

            //Convert bitmap to byte array
            val bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos) // YOU can also save it in JPEG
            val bitmapdata = bos.toByteArray()

            //write the bytes in file
            val fos = FileOutputStream(file)
            fos.write(bitmapdata)
            fos.flush()
            fos.close()
            file
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context,"gagalllll", Toast.LENGTH_SHORT).show()
            file // it will return null
        }
    }
}