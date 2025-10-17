package com.catshome.classJournal.screens.child

import androidx.lifecycle.viewModelScope
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import com.catshome.classJournal.child.ChildGroupsRepositoryImpl
import com.catshome.classJournal.context
import com.catshome.classJournal.domain.Child.ChildGroupRepository
import com.catshome.classJournal.domain.Child.ChildInteractor
import com.catshome.classJournal.domain.Child.ChildWithGroups
import com.catshome.classJournal.domain.Group.GroupRepository
import com.catshome.classJournal.domain.Group.Models.Group
import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalWearMaterialApi::class)
@HiltViewModel
class ChildListViewModel @Inject constructor(
    private val childInteract: ChildInteractor,
    private val groupRepository: GroupRepository
) : BaseViewModel<ChildListState, ChildListAction, ChildListEvent>(installState = ChildListState()) {
    init {
        obtainEvent(ChildListEvent.ReloadScreen)
    }

    override fun obtainEvent(viewEvent: ChildListEvent) {
        when (viewEvent) {
            is ChildListEvent.DeleteChildClicked -> TODO()
            is ChildListEvent.DeleteGroupClicked -> {
                groupRepository.deleteGroup(groupRepository.getGroupById(viewEvent.uid))
            }
            ChildListEvent.NewChildClicked -> viewAction = ChildListAction.NewChildClicked
            ChildListEvent.NewGroupClicked -> viewAction = ChildListAction.NewGroupClicked
            is ChildListEvent.UndoDeleteChildClicked -> TODO()
            is ChildListEvent.UndoDeleteGroupClicked -> TODO()
            ChildListEvent.ReloadScreen -> {
                loadScreen()
            }
        }
    }

    fun loadScreen() {
        viewModelScope.launch {
            val listChildGroups = childInteract.getListChildsWithGroups().map {
                ChildItem(child = it)
            }
            val childItem = mutableListOf<ChildItem>()
            listChildGroups.forEach {
                childItem.add(it)
            }
            childInteract.getGroup().collect { listgroup ->
              listgroup.map {
                    var add = false
                    listChildGroups.forEach { item ->
                        if (it.uid == item.child.groupUid)
                            add = true
                    }
                     if (!add){
                            childItem.add(ChildItem(
                                child = ChildWithGroups(
                                    childUid = "",
                                    childName = "",
                                    childSurname = "",
                                    groupUid = it.uid,
                                    groupName = it.name
                                )
                            ))
                        }
                    }
                    viewState = viewState.copy(
                        uidDelete = "",
                        isDelete = false,
                        swipeUid = "",
                        item =childItem .groupBy {
                            it.child.groupName
                        }.toSortedMap()
                    )
                }


//
//            viewState = viewState.copy(
//                uidDelete = "",
//                isDelete = false,
//                swipeUid = "",
//                item = childItem.groupBy {
//                    Group(
//                        it.child.groupUid,
//                        it.child.groupName,
//                        false
//                    )
//                }//.toSortedMap()
//            )
        }
    }
}
