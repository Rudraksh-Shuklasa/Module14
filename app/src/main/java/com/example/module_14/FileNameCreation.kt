package com.example.module_14

import android.content.Context
import android.os.Environment
import android.system.Os.mkdir
import java.nio.file.Files.exists
import android.os.Environment.DIRECTORY_PICTURES
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class FileNameCreation{
    fun createImageFile(context: Context): File? {
        // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        //create image file name under Pictures directory
        val storageDir = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "TweetImages")

        //if directory doesn't exist create directory
        if (!storageDir.exists()) {
            storageDir.mkdir()
        }
        var image: File? = null
        try {
            //now create jpg image under created directory
            image = File.createTempFile(
                imageFileName, /* prefix */
                ".jpg", /* suffix */
                storageDir      /* directory */
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return image
    }

}