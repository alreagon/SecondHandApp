package com.example.finalprojectbinaracademy_secondhandapp.utils

import android.graphics.Bitmap
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.text.NumberFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun rupiah(number: Double): String {
    val localeID = Locale("in", "ID")
    val numberFormat = NumberFormat.getCurrencyInstance(localeID)
    return numberFormat.format(number).toString()
}

fun convertISOTimeToDate(isoTime: String): String? {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    var convertedDate: Date? = null
    var formattedDate: String? = null
    try {
        convertedDate = sdf.parse(isoTime)
        formattedDate = SimpleDateFormat("dd MMM, HH:mm").format(convertedDate)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return formattedDate
}

fun bitmapToFile(
    bitmap: Bitmap,
    fileNameToSave: String,
    activity: FragmentActivity
): File? { // File name like "image.png"
    //create a file to write bitmap data
    var file: File? = null

    return try {
        if (File(activity.filesDir.toString() + File.separator + fileNameToSave).exists()) {
            if (File(activity.filesDir.toString() + File.separator + fileNameToSave).delete()) {
                Toast.makeText(activity, "berhasil hapus", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(activity, "gagal hapus", Toast.LENGTH_SHORT).show()
            }
        }

        file = File(activity.filesDir.toString() + File.separator + fileNameToSave)
        file.createNewFile()

        //Convert bitmap to byte array
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos) // YOU can also save it in JPEG
        val bitmapdata = bos.toByteArray()

        //write the bytes in file
        val fos = FileOutputStream(file)
        fos.write(bitmapdata)
        fos.flush()
        fos.close()
        file
    } catch (e: Exception) {
        e.printStackTrace()
        Toast.makeText(activity, "gagalllll", Toast.LENGTH_SHORT).show()
        file // it will return null
    }

}