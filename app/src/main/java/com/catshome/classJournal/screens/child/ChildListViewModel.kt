package com.catshome.classJournal.screens.child

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import com.catshome.classJournal.child.ChildGroupsRepositoryImpl
import com.catshome.classJournal.domain.Child.ChildInteractor
import com.catshome.classJournal.domain.Child.ChildWithGroups
import com.catshome.classJournal.domain.Group.Models.Group
import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.toSortedMap

@OptIn(ExperimentalWearMaterialApi::class)
@HiltViewModel
class ChildListViewModel @Inject constructor(
    private val childInteract: ChildInteractor,
    private val childGroups: ChildGroupsRepositoryImpl
) : BaseViewModel<ChildListState, ChildListAction, ChildListEvent>(installState = ChildListState()) {
    init {
        obtainEvent(ChildListEvent.ReloadScreen)
    }

    override fun obtainEvent(viewEvent: ChildListEvent) {
        when (viewEvent) {
            is ChildListEvent.DeleteChildClicked -> TODO()
            is ChildListEvent.DeleteGroupClicked -> TODO()
//            is ChildListEvent.ItemChildClicked -> {}
//            is ChildListEvent.ItemGroupClicked -> TODO()
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
            val l = childInteract.getListChildsWithGroups().map {
                ChildItem(child = it)
            }
            val g = mutableListOf<ChildItem>()
            l.forEach {
                g.add(it)
            }
            childInteract.getGroup().collect { gr ->
                gr.forEach {
                    g.add(
                        ChildItem(
                            child = ChildWithGroups(
                                childUid = "",
                                childName = "",
                                childSurname = "",
                                groupUid = it.uid,
                                groupName = it.name
                            )
                        )
                    )
                    viewState = viewState.copy(
                        uidDelete = "",
                        isDelete = false,
                        swipeUid = "",
                        item = g.groupBy { it.child.groupName }.toSortedMap()
                    )
                }
            }

            viewState = viewState.copy(
                uidDelete = "",
                isDelete = false,
                swipeUid = "",
                item = g.groupBy { it.child.groupName }.toSortedMap()
            )

//            listGroup->
//                listGroup.forEach { group ->
//                    Log.e("CLJR", "group map $group")
//                    l.forEach {childItem->
//                        Log.e("CLJR", "ChildItem $childItem")
//                        if(group.uid == childItem.child.groupUid)
//                            Log.e("CLJR", "add group $group")
//                    }
//                }
//            }


        }
    }
}