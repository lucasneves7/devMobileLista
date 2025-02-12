package com.example.neves.oliveira.lucas.lista

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.neves.oliveira.lucas.lista.ui.theme.ListaTheme
import com.example.neves.oliveira.lucas.lista.view.createEdit.CreateEditVM
import com.example.neves.oliveira.lucas.lista.view.list.ListActivity
import com.example.neves.oliveira.lucas.lista.view.list.ListVM
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicializa o Koin para injeção de dependências, se ainda não estiver inicializado.
        if (savedInstanceState == null) {
            startKoin {
                // Define o contexto do Android para o Koin
                androidContext(this@MainActivity)
                // Registra os módulos de dependências do Koin
                modules(appModule)
            }
        }

        val intent = Intent(this, ListActivity::class.java)
        startActivity(intent)
        finish()
    }
}

private val appModule = module {
    viewModel { ListVM() } // ViewModel responsável pelo cadastro de usuários
    viewModel { CreateEditVM() } // ViewModel responsável pelo login de usuários
}