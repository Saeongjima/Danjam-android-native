package com.danjam.context

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import java.io.File
import java.io.FileOutputStream

fun processAndAdjustImage(context: Context, uri: Uri): File {
    val bitmap = uri.getBitmapFromUri(context)
    val file = bitmap?.convertResizeImage(context)
    file?.updateExifOrientation(context, uri)
    return file ?: File("")
}

private fun Uri.getBitmapFromUri(context: Context): Bitmap? {
    return context.contentResolver.openInputStream(this)?.use {
        BitmapFactory.decodeStream(it)
    }
}

private fun Bitmap.convertResizeImage(context: Context): File {
    val maxWidth = 500
    val scaleFactor = maxWidth.toFloat() / width
    val newWidth = (width * scaleFactor).toInt()
    val newHeight = (height * scaleFactor).toInt()
    val resizedBitmap = Bitmap.createScaledBitmap(this, newWidth, newHeight, false)

    val tempFile = File.createTempFile("resized_image", ".jpg", context.cacheDir)

    FileOutputStream(tempFile).use { fileOutputStream ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            resizedBitmap.compress(Bitmap.CompressFormat.WEBP_LOSSY, 80, fileOutputStream)
        } else {
            resizedBitmap.compress(Bitmap.CompressFormat.WEBP, 80, fileOutputStream)
        }
    }

    resizedBitmap.recycle()
    return tempFile
}

private fun File.updateExifOrientation(context: Context, uri: Uri) {
    context.contentResolver.openInputStream(uri)?.use {
        val exif = ExifInterface(it)
        exif.getAttribute(ExifInterface.TAG_ORIENTATION)?.let { attribute ->
            val newExif = ExifInterface(absolutePath)
            newExif.setAttribute(ExifInterface.TAG_ORIENTATION, attribute)
            newExif.saveAttributes()
        }
    }
}
