package com.catshome.classjornal.Screens.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import androidx.lifecycle.ViewModel
import com.catshome.ClassJournal.domain.Group.GroupInteractor
import com.catshome.ClassJournal.domain.Group.Models.Group
import com.catshome.classjornal.Screens.Group.GroupState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class GroupViewModel @Inject constructor(val groupInteractor: GroupInteractor,val group: Group):ViewModel() {
    val state =   mutableStateOf( GroupState)
        private set

    fun upDateGroup(value: String){
        groupName=value
    }
    fun onClickSave(){
        groupInteractor.saveGroupUseCase(group)
    }
}
