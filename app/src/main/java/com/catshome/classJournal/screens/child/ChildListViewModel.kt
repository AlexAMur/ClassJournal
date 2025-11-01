package com.catshome.classJournal.screens.child

import android.util.Log

import androidx.lifecycle.viewModelScope
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import com.catshome.classJournal.domain.Child.Child
import com.catshome.classJournal.domain.Child.ChildGroup
import com.catshome.classJournal.domain.Child.ChildInteractor
import com.catshome.classJournal.domain.Child.ChildWithGroups
import com.catshome.classJournal.domain.Group.GroupRepository
import com.catshome.classJournal.domain.Group.Models.Group
import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import kotlin.random.Random
fun TestData( childInteract: ChildInteractor,
              groupRepository: GroupRepository){
    val uidGroup = UUID.randomUUID().toString()
    CoroutineScope(Dispatchers.IO).launch {

        groupRepository.saveGroup(Group(uid = uidGroup, name = "Group ${Random.nextInt(1,100)}"))
        repeat(2) {
            val uidChild = UUID.randomUUID().toString()
            childInteract.saveChildUseCase(
                Child(
                    uid = uidChild,
                    name = "Avto${Random.nextInt(1,1000)}",
                    surname = "avto",
                    birthday = "21.10.2024"
                ),
                if (it % 2 == 0)
                    listOf(
                        ChildGroup(
                            uid = UUID.randomUUID().toString(),
                            groupId = uidGroup,
                            childId = uidChild
                        )
                    )
                else emptyList<ChildGroup>()
            )
        }
    }
}

@OptIn(ExperimentalWearMaterialApi::class)
@HiltViewModel
class ChildListViewModel @Inject constructor(
    private val childInteract: ChildInteractor,
    private val groupRepository: GroupRepository
) : BaseViewModel<ChildListState, ChildListAction, ChildListEvent>(installState = ChildListState()) {
    init {
      //  TestData(childInteract, groupRepository)
        Log.e("CLJR", "Start Init")
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
                    childInteract.deleteChildUseCase(viewEvent.uid)
                }
                viewState.snackBarShow =false
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

            ChildListEvent.NewChildClicked -> viewAction = ChildListAction.NewChildClicked
            ChildListEvent.NewGroupClicked -> viewAction = ChildListAction.NewGroupClicked
            is ChildListEvent.undoDelete -> {
                //отмена удаления
                Log.e("CLJR", "Undo delete")
                viewState.uidDelete = ""
                viewState.snackBarShow = false
                obtainEvent(ChildListEvent.ReloadScreen)
            }

            ChildListEvent.ReloadScreen -> {
                loadScreen()
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
                    hideChild(uidChild = viewEvent.uidChild, key = viewEvent.key)
                } else {
                    viewState = viewState.copy(uidDelete = viewEvent.uidGroup)
                    hideGroup(key = viewEvent.key, uidGroup = viewState.uidDelete)
                }
            //    obtainEvent(ChildListEvent.ReloadScreen)
            }
        }
    }

    private fun hideChild(
        uidChild: String,
        key: String
    ) {
        val a = viewState.item.filter {
            it.key != key
        }
        val i = viewState.item.remove(key)
            ?.filter { it.child.childUid != uidChild }
        val rr = i?.let { a.plus(Pair(key, it)) }?.toSortedMap()?.toMutableMap()
        rr?.let { viewState = viewState.copy(item = it) }
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
        } ?: emptyList()
        val i = viewState.item.plus(mapOf(Pair(key, y)))
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
