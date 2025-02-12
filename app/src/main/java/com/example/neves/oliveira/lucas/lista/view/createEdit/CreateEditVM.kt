package com.example.neves.oliveira.lucas.lista.view.createEdit

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.OpenableColumns
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class CreateEditVM: ViewModel() {

    var imageState by mutableStateOf<File?>(null)
    var title by mutableStateOf("")
    var description by mutableStateOf("")

    fun onTitleChange(value: String) { title = value }

    fun onDescriptionChange(value: String) { description = value }

    fun onImageSelected(uri: Uri, context: Context) {
        val parcelFileDescriptor = context.contentResolver.openFileDescriptor(
            uri,
            "r",
            null
        )
        val file = File(
            context.cacheDir,
            context.contentResolver.getFileName(uri)
        )
        val inputStream = FileInputStream(parcelFileDescriptor?.fileDescriptor)
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)
        imageState = file
    }

    fun onImageSelected(file: File){
        imageState = file
    }

    @SuppressLint("Range")
    fun ContentResolver.getFileName(uri: Uri): String {
        var name = ""
        val cursor = query(
            uri, null, null,
            null, null
        )
        cursor?.use {
            it.moveToFirst()
            name = it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
        }
        return name
    }
}