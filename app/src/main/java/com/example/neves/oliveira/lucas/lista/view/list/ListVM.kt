package com.example.neves.oliveira.lucas.lista.view.list

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.example.neves.oliveira.lucas.lista.view.Item

class ListVM: ViewModel() {

    private val _items = mutableStateListOf<Item>()
    val items: List<Item> = _items

    fun addItem(item: Item) {
        _items.add(item)
    }
}