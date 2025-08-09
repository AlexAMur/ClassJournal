package com.catshome.classJournal.screens.viewModels

import androidx.lifecycle.viewModelScope
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.RevealValue
import com.catshome.classJournal.domain.Group.GroupInteractor
import com.catshome.classJournal.screens.group.GroupAction
import com.catshome.classJournal.screens.group.GroupAction.OpenGroup
import com.catshome.classJournal.screens.group.GroupEvent
import com.catshome.classJournal.screens.group.GroupItem
import com.catshome.classJournal.screens.group.GroupState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject


@OptIn(ExperimentalWearMaterialApi::class)
@HiltViewModel
class GroupViewModel @Inject constructor(private val groupInteractor: GroupInteractor) :
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
                viewState.isDelete = true
                CoroutineScope(Dispatchers.Main).launch {
                    viewState.listItems[viewEvent.index].revealState?.snapTo(RevealValue.RightRevealed)
                }
                CoroutineScope(Dispatchers.Default).launch {
//
                    delay(4000L)
                    if (viewState.isDelete) {
                        CoroutineScope(Dispatchers.Main).launch {
                            viewState.listItems[viewEvent.index].revealState?.snapTo(RevealValue.Covered)
                        }
                        groupInteractor.deleteGroupUseCase(groupInteractor.getGroupByID(viewEvent.uid))

                    }
                }
            }


            is GroupEvent.UndoDeleteClicked -> {
                viewState.isDelete = false
                viewState.uidDelete = -1
                CoroutineScope(Dispatchers.Main).launch {
                    viewState.listItems[viewEvent.index].revealState?.snapTo(RevealValue.Covered)
                    viewAction = GroupAction.RequestDelete(viewEvent.index)
                }

            }

        }
    }
    private fun reloadScreen() {
        viewModelScope.launch(Dispatchers.Default) {

            groupInteractor.getGroupUseCase(false).collect { listGroup ->
                viewState =
                    viewState.copy(
                        -1,
                        isDelete = false,
                        listItems = listGroup.map { group ->
                            GroupItem(
                                //revealState = null,
                                group = group
//                                listGroup.map { group -> GroupItem(
//                            revealState = viewState.listItems.firstOrNull{it.group.uid == group.uid}?.revealState,
//                            group = group
                            )
                        }

                    )
            }
        }
    }
}
