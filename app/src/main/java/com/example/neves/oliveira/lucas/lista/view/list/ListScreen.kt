package com.example.neves.oliveira.lucas.lista.view.list

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import com.example.neves.oliveira.lucas.lista.view.Item
import com.example.neves.oliveira.lucas.lista.view.createEdit.CreateEditActivity
import org.koin.androidx.compose.koinViewModel
import java.io.File

@Composable
fun ListScreen(
    modifier: Modifier = Modifier,
    viewModel: ListVM = koinViewModel(),
    context: Activity,
){
    val items by remember { derivedStateOf { viewModel.items } }

    val startResult = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode != Activity.RESULT_OK) return@rememberLauncherForActivityResult
        val uri = it.data ?: return@rememberLauncherForActivityResult
        val title = uri.getStringExtra(CreateEditActivity.TITLE_KEY) ?: ""
        val desc = uri.getStringExtra(CreateEditActivity.DESC_KEY) ?: ""
        val image = (uri.getSerializableExtra(CreateEditActivity.IMAGE_KEY) as? File) ?: return@rememberLauncherForActivityResult
        val item = Item(title, desc, image)
        viewModel.addItem(item)
    }

    Box(modifier = modifier){
        LazyColumn(modifier = Modifier.fillMaxSize()){
            items(items){ item ->
                ItemView(item)
                HorizontalDivider()
            }

            item { Spacer(Modifier.size(72.dp)) }
        }

        FloatingActionButton(
            onClick = {
                val intent = Intent(context, CreateEditActivity::class.java)
                startResult.launch(intent)
            },
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp)
        ){
            Icon(Icons.Filled.Add, null)
        }
    }
}

@Composable
private fun ItemView(item: Item){

    ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
        val (imageRef, titleRef, descRef) = createRefs()
        Image(
            painter = rememberImagePainter(item.image),
            contentDescription = "Selected Image",
            modifier = Modifier.size(150.dp).constrainAs(imageRef){
                top.linkTo(parent.top)
                start.linkTo(parent.start)
                bottom.linkTo(parent.bottom)
            }
        )
        Text(
            text = item.title,
            style = MaterialTheme.typography.titleLarge
                .copy(color = MaterialTheme.colorScheme.tertiary, fontWeight = FontWeight.Bold),
            modifier = Modifier.constrainAs(titleRef){
                top.linkTo(parent.top, 8.dp)
                linkTo(start = imageRef.end, end = parent.end, bias = 0f, startMargin = 8.dp, endMargin = 8.dp)
            }
        )
        Text(
            text = item.description,
            modifier = Modifier.constrainAs(descRef) {
                top.linkTo(titleRef.bottom, 4.dp)
                linkTo(start = imageRef.end, end = parent.end, bias = 0f, startMargin = 8.dp, endMargin = 8.dp)
            }
        )
    }

}