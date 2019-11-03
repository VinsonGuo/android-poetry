package com.tech502.poetry.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tech502.poetry.model.Poem
import com.tech502.poetry.model.Resource
import com.tech502.poetry.util.Repository
import kotlinx.coroutines.launch

class PoemViewModel : ViewModel() {

    val poemModel = MutableLiveData<Resource<Poem>>()

    private val repository by lazy { Repository.instance }

    fun loadData(id: String, name: String) {
        viewModelScope.launch {
            poemModel.value = repository.poemInfo(id, name)
        }
    }


}