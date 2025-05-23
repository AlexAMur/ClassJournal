package com.catshome.classjornal.Screens.viewModels

import com.catshome.ClassJournal.domain.Group.GroupInteractor
import com.catshome.classjornal.Screens.Group.ComposeAction
import com.catshome.classjornal.Screens.Group.GroupEvent
import com.catshome.classjornal.Screens.Group.GroupState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class GroupViewModel @Inject constructor() : BaseViewModel<GroupState, ComposeAction,GroupEvent>  (installSate = GroupState(), ) {
       @Inject lateinit var  repository : GroupInteractor
    override fun obtainEvent(viewEvent: GroupEvent) {
        when(viewEvent){
            is GroupEvent.ChangeName->{
                viewState =viewState.copy(nameGroup =viewEvent.nameGroup)
             }
            is GroupEvent.DeleteGroup->{
                viewState = viewState.copy(isDelete = viewState.isDelete)
            }

            GroupEvent.ActionInvoked ->viewAction = null
            GroupEvent.SaveClicked ->viewAction =ComposeAction.CloseScreen
            GroupEvent.NextClicked -> viewAction = ComposeAction.NextClicked
        }

            }
}
