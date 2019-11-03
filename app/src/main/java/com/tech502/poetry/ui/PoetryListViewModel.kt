package com.tech502.poetry.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tech502.poetry.model.Poetry
import com.tech502.poetry.model.Resource
import com.tech502.poetry.util.Repository
import kotlinx.coroutines.launch

class PoetryListViewModel : ViewModel() {

    val listResource = MutableLiveData<Resource<List<Poetry>>>()

    private val repository by lazy { Repository.instance }

    fun loadListData(keyword: String, page: Int) {
        viewModelScope.launch {
            listResource.value = repository.searchPoetry(keyword, page)
        }
    }


}