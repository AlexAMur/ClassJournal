package com.catshome.classJournal.screens.child

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import com.catshome.classJournal.domain.Child.ChildInteractor
import com.catshome.classJournal.domain.Child.ChildWithGroups
import com.catshome.classJournal.domain.Group.GroupRepository
import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
            is ChildListEvent.showSnackBar -> {
                viewState.snackMessage = viewEvent.message
                viewState.snackActionLabel = viewEvent.actionLabel
                viewState.onDismissed = viewEvent.onDismissed
                viewState.onActionPerformed = viewEvent.onActionPerformed
                viewState.snackBarShow = true
            }

            is ChildListEvent.deleteChild -> {
                //Удаление ребенка
                //TODO обработчик ошибок SQL
                CoroutineScope(Dispatchers.IO).launch {
                    childInteract.getChildByID(viewEvent.uid)?.let {
                        childInteract.deleteChildUseCase(it)
                    }
                }
                viewState.snackBarShow = false
            }

            is ChildListEvent.deleteGroup -> {
                //удаление группы
                CoroutineScope(Dispatchers.IO).launch {
                    if (viewState.uidDelete.isNotEmpty()) {
                        groupRepository.deleteGroup(groupRepository.getGroupById(viewState.uidDelete))
                    }
                }
                viewState.snackBarShow = false
            }

            ChildListEvent.NewChildClicked -> {
                viewState.reloadScreen = true
                viewAction = ChildListAction.NewChildClicked

            }

            ChildListEvent.NewGroupClicked -> {
                viewState.reloadScreen = true
                viewAction = ChildListAction.NewGroupClicked
            }

            is ChildListEvent.undoDelete -> {
                //отмена удаления
                Log.e("CLJR", "Undo delete")
                viewState.uidDelete = ""
                viewState.snackBarShow = false
                viewState.reloadScreen = true
                obtainEvent(ChildListEvent.ReloadScreen)
            }

            ChildListEvent.ReloadScreen -> {
                loadScreen()
                viewState.reloadScreen = false
                viewState.snackBarShow = false
            }

            is ChildListEvent.ChangeRevealed -> {
                ChangeOptionsRevealed(
                    childItem = viewEvent.item,
                    key = viewEvent.key,
                    isOptionsRevealed = viewEvent.isOptionsRevealed
                )
            }

            is ChildListEvent.deleteClicked -> {
                if (viewEvent.uidChild.isNotEmpty()) {
                    viewState = viewState.copy(uidDelete = viewEvent.uidChild)
                    viewState = viewState.copy(
                        item = hideChild(
                            item = viewState.item,
                            uidChild = viewEvent.uidChild
                        )
                    )
                } else {
                    viewState = viewState.copy(uidDelete = viewEvent.uidGroup)
                    hideGroup(
                        viewState = viewState,
                        key = viewEvent.key,
                        uidGroup = viewState.uidDelete
                    ).let {
                        if (it != null) {
                            viewState = it
                        }
                    }
                }
            }

            ChildListEvent.SelectItem -> {
                viewState.reloadScreen = true
            }

            is ChildListEvent.showFAB -> {
                    viewState = viewState.copy(showFAB = viewEvent.isShowFAB)
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
        } ?: emptyList()
        val i = viewState.item.plus(mapOf(Pair(key, y)))
        viewState = viewState.copy(item = i.toMutableMap())

    }

    private fun loadScreen() {
        viewModelScope.launch {
            val childItem = mutableListOf<ChildItem>()
            childInteract.getListChildsWithGroups().map {
                //val listChildGroups = childInteract.getListChildsWithGroups().map {
                childItem.add(ChildItem(child = it))
            }


            childInteract.getGroup().collect { listgroup ->
                listgroup.map { group ->
                    var add = false
                    childItem.forEach { item ->
                        //if (group.uid == item.child.groupUid)
                        //   add = true
                    }
                    if (!add) {
                        childItem.add(
                            ChildItem(

                                child = ChildWithGroups(
                                    childUid = "",
                                    childName = "",
                                    childSurname = "",
                                    groupUid = group.uid,
                                    groupName = group.name,
                                    childBirthDay = ""
                                )
                            )
                        )
                    }
                }
                viewState = viewState.copy(

                    snackMessage = viewState.snackMessage,
                    snackActionLabel = viewState.snackActionLabel,
                    snackBarShow = viewState.snackBarShow,
                    item = childItem.groupBy {
                        it.child.groupName
                    }.toSortedMap()
                )
            }
        }
    }
}
