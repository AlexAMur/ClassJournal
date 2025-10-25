package com.catshome.classJournal.screens.child

import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.lifecycle.viewModelScope
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import com.catshome.classJournal.domain.Child.ChildInteractor
import com.catshome.classJournal.domain.Child.ChildWithGroups
import com.catshome.classJournal.domain.Group.GroupRepository
import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
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
            is ChildListEvent.showSnackBar->{
                viewState.snackMessage = viewEvent.message
                viewState.snackActionLabel =viewEvent.actionLabel
                viewState.onDismissed =viewEvent.onDismissed
                viewState.onActionPerformed =viewEvent.onActionPerformed
                viewState.snackBarShow = true
            }
            is ChildListEvent.deleteChild-> {
                //сохраняем идентификатор ребенка для удаления
                childInteract.deleteChildUseCase(viewEvent.uid)
            }

            is ChildListEvent.deleteGroup -> {
                //сохраняем идентификатор группы для удаления
                //удаление группы
                CoroutineScope(Dispatchers.IO).launch {
                    if (viewState.uidDelete.isNotEmpty()) {
                        groupRepository.deleteGroup(groupRepository.getGroupById(viewState.uidDelete))
                    }
                }
            }

            ChildListEvent.NewChildClicked -> viewAction = ChildListAction.NewChildClicked
            ChildListEvent.NewGroupClicked -> viewAction = ChildListAction.NewGroupClicked
            is ChildListEvent.undoDelete -> {
                //отмена удаления
                Log.e("CLJR", "Undo delete")
                viewState.uidDelete = ""
                obtainEvent(ChildListEvent.ReloadScreen)
            }

            ChildListEvent.ReloadScreen -> {
                loadScreen()
            }

            is ChildListEvent.ChangeRevealed -> {
                ChangeOptionsRevealed(
                    childItem = viewEvent.item,
                    key = viewEvent.key,
                    isOptionsRevealed = viewEvent.isOptionsRevealed
                )
            }

            is ChildListEvent.deleteClicked -> {

                if (viewEvent.uidChild.isNotEmpty()){
                    viewState = viewState.copy(uidDelete = viewEvent.uidChild)
                    hideChild(uidChild = viewEvent.uidChild, key = viewEvent.key)
                }
                else{
                    viewState = viewState.copy(uidDelete = viewEvent.uidGroup)
                    hideGroup(key = viewEvent.key, uidGroup = viewState.uidDelete)
                }
            }
        }
    }
    private fun hideChild(uidChild: String,
                          key: String){

        val a = viewState.item.filter {
            it.key != key
        }
        val i = viewState.item.remove(key)
            ?.filter { it.child.childUid != uidChild }
        val rr = i?.let { a.plus(Pair(key, it)) }?.toSortedMap()?.toMutableMap()
        rr?.let { viewState = viewState.copy(item = it) }
        Log.e("CLJR", "delete child clicked uid = ${viewState.uidDelete}")
    }

    private fun hideGroup(
        uidGroup: String,
        key: String
    ) {
        val a = viewState.item.get(key)?.filter {
            it.child.groupUid == uidGroup && it.child.childUid.isNotEmpty()
        }
        a?.let {
            if (it.size > 0) {
                val i = viewState.item.plus(mapOf(Pair(key, it)))
                viewState = viewState.copy(item = i.toMutableMap())
            } else {
                val i = viewState.item
                i.remove(key)
                viewState = viewState.copy(item = i.toMutableMap())
            }
        }
    }

    private fun ChangeOptionsRevealed(
        childItem: ChildItem, isOptionsRevealed: Boolean,
        key: String
    ) {
        val a = viewState.item.get(key)
        val y = a?.let { childItemList ->
            childItemList.map {
                if (it == childItem)
                    childItem.copy(isOptionsRevealed = isOptionsRevealed)
                else
                    it
            }
        }
        val i = viewState.item.plus(mapOf(Pair(key, y!!)))
        viewState = viewState.copy(item = i.toMutableMap())
        Log.e("CLJR", "Change option uid = ${viewState.uidDelete}")
    }

    private fun loadScreen() {
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
                    if (!add) {
                        childItem.add(
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
                    }
                }
                viewState = viewState.copy(
                    item = childItem.groupBy {
                        it.child.groupName
                    }.toSortedMap()
                )
            }
        }
    }
}
