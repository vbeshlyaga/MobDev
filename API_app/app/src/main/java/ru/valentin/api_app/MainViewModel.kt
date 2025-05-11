package ru.valentin.api_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {

    private val _photos = MutableStateFlow<List<String>>(emptyList())
    val photos = _photos.asStateFlow()

    private var isLoading = false

    fun loadPhotos() {
        if (isLoading) return
        isLoading = true

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val newPhotos = mutableListOf<String>()

                repeat(20) {
                    val response = APIClient.instance.getCat().execute()
                    if (response.isSuccessful) {
                        response.body()?.firstOrNull()?.let {
                            newPhotos.add(it.url)
                        }
                    }
                }

                _photos.value += newPhotos
                isLoading = false
            }
        }
    }
}