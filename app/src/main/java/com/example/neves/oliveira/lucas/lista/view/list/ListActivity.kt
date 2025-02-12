package com.example.neves.oliveira.lucas.lista.view.list

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import com.example.neves.oliveira.lucas.lista.ui.theme.ListaTheme
import com.example.neves.oliveira.lucas.lista.view.createEdit.ImagePickerScreen

class ListActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Ativa o suporte a tela cheia.

        setContent {
            ListaTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        TopAppBar(
                            title = { Text("Lista") },
                            colors = TopAppBarDefaults.topAppBarColors().copy(
                                containerColor = MaterialTheme.colorScheme.primaryContainer
                            ),
                        )
                    }
                ) { innerPadding ->
                    ListScreen(
                        modifier = Modifier.fillMaxSize().padding(innerPadding),
                        context = this
                    )
                }
            }
        }
    }
}