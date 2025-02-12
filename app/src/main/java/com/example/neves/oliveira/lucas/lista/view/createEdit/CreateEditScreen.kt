package com.example.neves.oliveira.lucas.lista.view.createEdit

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import coil.compose.rememberImagePainter
import org.koin.androidx.compose.koinViewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ImagePickerScreen(
    viewModel: CreateEditVM = koinViewModel(),
    onSave: (String, String, File) -> Unit
) {
    val context = LocalContext.current
    val imageState by remember { derivedStateOf { viewModel.imageState } }
    val title by remember { derivedStateOf { viewModel.title } }
    val desc by remember { derivedStateOf { viewModel.description } }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
        uri?.let {
            viewModel.onImageSelected(it, context)
        }
    }

    val photoFile = remember {
        createImageFile(context)
    }

    val photoUri = remember {
        FileProvider.getUriForFile(
            context,
            "${context.applicationContext.packageName}.provider",
            photoFile
        )
    }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            // A imagem foi salva com sucesso no URI fornecido
            viewModel.onImageSelected(photoFile)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Exibe a imagem selecionada ou um ícone de avatar
        if (imageState != null) {
            Image(painter = rememberImagePainter(imageState), contentDescription = "Selected Image", modifier = Modifier.size(150.dp))
        } else {
            Text("*Selecione a imagem.")
            Icon(Icons.Filled.Face, contentDescription = "Avatar", Modifier.size(150.dp))
        }

        // Botões para tirar foto ou selecionar imagem
        Button(onClick = {
            // Solicitar permissão para câmera
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // Abre a câmera
                cameraLauncher.launch(photoUri)
            } else {
                // Solicitar permissão para câmera
                ActivityCompat.requestPermissions(
                    context as Activity,
                    arrayOf(Manifest.permission.CAMERA),
                    1001
                )
            }
        }) {
            Text("Tirar Foto")
        }

        Button(onClick = {
            // Abre a galeria de imagens
            launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }) {
            Text("Selecionar Imagem")
        }

        TextField(
            value = title,
            label = { Text("*Titulo") },
            onValueChange = { viewModel.onTitleChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        )

        TextField(
            value = desc,
            label = { Text("*Descrição") },
            onValueChange = { viewModel.onDescriptionChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onSave(title, desc, imageState!!) },
            enabled = desc.isNotBlank() && title.isNotBlank() && imageState != null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
        ) {
            Text("ADICIONAR ITEM")
        }
        Text(
            text = "*Campos Obrigatórios",
            style = MaterialTheme.typography.bodySmall
        )
    }
}

private fun createImageFile(context: Context): File {
    // Create an image file name
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val imageFileName = "JPEG_" + timeStamp + "_"
    val storageDir = context.cacheDir // Use the cache directory
    return File.createTempFile(
        imageFileName, /* prefix */
        ".jpg", /* suffix */
        storageDir /* directory */
    )
}
