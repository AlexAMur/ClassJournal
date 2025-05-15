package com.catshome.classjornal.Screens.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class GroupViewModel @Inject constructor():ViewModel() {
    var groupName by  mutableStateOf("")
        private set

    fun upDateGroup(value: String){
        groupName=value
    }
    fun onClickSave(){

    }
}
