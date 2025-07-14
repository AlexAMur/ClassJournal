package com.catshome.classJournal.screens.viewModels

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import com.catshome.classJournal.domain.Group.GroupInteractor
import com.catshome.classJournal.screens.group.GroupAction
import com.catshome.classJournal.screens.group.GroupAction.OpenGroup
import com.catshome.classJournal.screens.group.GroupEvent
import com.catshome.classJournal.screens.group.GroupItem
import com.catshome.classJournal.screens.group.GroupState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

fun mapToListItem(){

}
@OptIn(ExperimentalWearMaterialApi::class)
@HiltViewModel
class GroupViewModel @Inject constructor(val groupInteractor: GroupInteractor) :
    BaseViewModel<GroupState, GroupAction, GroupEvent>(installState = GroupState()) {

//    init {
//        reloadScreen()
//    }

    override fun obtainEvent(viewEvent: GroupEvent) {
        when (viewEvent) {
//            is GroupEvent.CancelDelete -> {
//                viewState.uidDelete = -1
//                viewState.isDelete = false
//                obtainEvent(GroupEvent.ReloadScreen)
//            }

//            is GroupEvent.RequestDelete -> {
//                viewState.uidDelete = viewEvent.uid
//                viewState.isDelete = true
//                viewAction = GroupAction.RequestDelete
//            }

            is GroupEvent.ReloadScreen -> {
                reloadScreen()
            }

            is GroupEvent.NewClicked -> {
                viewAction = OpenGroup(0)
            }

            is GroupEvent.DeleteClicked -> {
                groupInteractor.deleteGroupUseCase(groupInteractor.getGroupByID(viewEvent.uid))
                viewState = viewState.copy()
                obtainEvent(GroupEvent.ReloadScreen)
            }

//            is GroupEvent.UpdateGroupClicked -> {
//                groupInteractor.saveGroupUseCase(viewEvent.group)
//            }

            is GroupEvent.UndoDeleteClicked -> {
                viewState.isDelete = false
                viewState.uidDelete = -1
            }

            is GroupEvent.SwipeUpdate -> {
                Log.e("CLJR", "ViewModel event SwipeUpdate")
                viewState.listItems[viewEvent.index].revealState =viewEvent.revealState
            }
        }
    }

    private fun reloadScreen() {
        viewModelScope.launch(Dispatchers.Default) {

            groupInteractor.getGroupUseCase(false).collect { listGroup ->
                viewState =
                    viewState.copy(viewState.uidDelete,
                        isDelete = viewState.isDelete,
                        listItems = listGroup.map { group -> GroupItem(
                            revealState = viewState.listItems.firstOrNull{it.group.uid == group.uid}?.revealState,
                            group = group
                        ) }

                    )
            }
        }
    }
}
