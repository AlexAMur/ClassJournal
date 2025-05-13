package com.catshome.classjornal.Screens.viewmadels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel


@HiltViewModel
class GroupViewModel():ViewModel() {
    var groupName by  mutableStateOf("")
        private set

    fun upDateGroup(value: String){
        groupName=value
    }
    fun onClickSave(){

    }
}
