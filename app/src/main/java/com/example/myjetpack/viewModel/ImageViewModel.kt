package com.example.myjetpack.viewModel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class ImageViewModel: ViewModel() {
    private val _images = mutableStateListOf<String>()
    val images = _images

    init {
        fetchRandlomImages(20)
    }

    fun fetchRandlomImages(count: Int){

        repeat(count){
            _images.add("https://picsum.photos/200/${(300..500).random()}")
        }

    }

    fun clearImages(){
        _images.clear()
    }
}