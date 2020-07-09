package dev.putra.commons.utilities.extension

/**
 * Created by Chyrus on 3/28/2020.
 */

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
fun Activity.getOutputMediaFile(): Uri? {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
    val storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val newFile = File.createTempFile("IMG_$timeStamp", ".jpg", storageDir)
    return FileProvider.getUriForFile(this, String(), newFile)
}

@SuppressLint("Recycle")
fun Uri.getInitialPath(context: Context): String {
    context.contentResolver.query(this, null, null, null, null)?.use {
        it.use { }
        it.moveToFirst()
        val columnIndex = it.getColumnIndex(MediaStore.Images.Media.DATE_ADDED)
        return it.getString(columnIndex).orEmpty()
    }
    return String()
}

fun String.getFileCheckSumString(): String {
    val encryptedHash = MessageDigest.getInstance("SHA-256")
    encryptedHash.update(this.toByteArray(), 0, this.length)
    var checkSumOfMD5 = BigInteger(1, encryptedHash.digest()).toString(16)
    while (checkSumOfMD5.length < 32) {
        checkSumOfMD5 = "0$checkSumOfMD5"
    }
    return checkSumOfMD5
}