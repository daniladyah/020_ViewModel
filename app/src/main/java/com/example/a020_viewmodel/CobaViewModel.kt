package com.example.a020_viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.a020_viewmodel.data.DataForm
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CobaViewModel : ViewModel() {
    var jenisKl: String by mutableStateOf("")
        private set
    var status: String by mutableStateOf("")
        private set
    var alamat: String by mutableStateOf("")
        private set
        var email: String by mutableStateOf("")
            private set

    private val _uiState = MutableStateFlow(DataForm())
    val uiState: StateFlow<DataForm> = _uiState.asStateFlow()

    fun insertData(jk: String, st: String, al:String, em:String){
        jenisKl = jk;
        status = st;
        alamat = al;
        email = em;
    }

    fun setJenisK(pilihJK:String){
        _uiState.update { currentState -> currentState.copy(sex = pilihJK) }
    }

}