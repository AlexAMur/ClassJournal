package com.catshome.classJournal.screens.child

import android.util.Log
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.viewModelScope
import com.catshome.classJournal.child.ChildGroupsRepositoryImpl
import com.catshome.classJournal.domain.Child.Child
import com.catshome.classJournal.domain.Child.ChildGroup
import com.catshome.classJournal.domain.Child.ChildInteractor
import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class NewChildViewModel @Inject constructor(
    private val childInteract: ChildInteractor,
    private val childGroups: ChildGroupsRepositoryImpl
) :
    BaseViewModel<NewChildState, NewChildAction, NewChildEvent>
        (installState = NewChildState()) {

    val TEXT_FILD_COUNT = 4
    val listTextField = List<FocusRequester>(TEXT_FILD_COUNT) { FocusRequester() }

    init {
        getScreenGroups()
    }

    fun nameChange(newValue: String) {
        viewState = viewState.copy(child = viewState.child.copy(name = newValue))
    }

    fun surnameChange(newValue: String) {
        viewState = viewState.copy(child = viewState.child.copy(surname = newValue))
    }

    fun phoneChange(newValue: String) {
        viewState = viewState.copy(child = viewState.child.copy(phone = newValue))
    }

    fun birthdayChange(newValue: String) {
        viewState = viewState.copy(child = viewState.child.copy(birthday = newValue))
    }

    fun noteChange(newValue: String) {
        viewState = viewState.copy(child = viewState.child.copy(note = newValue))
    }

    override fun obtainEvent(viewEvent: NewChildEvent) {
        when (viewEvent) {
            is NewChildEvent.ReloadScreen -> {}
            is NewChildEvent.OpenChild -> {
                val child = childInteract.getChildByID(viewEvent.uid)
                viewModelScope.launch {
                    childGroups.getChildGroups(child.uid).collect { getScreenGroups(it) }
                }
                viewState = viewState.copy(child)
            }

            is NewChildEvent.SaveChild -> {
                if (viewState.child.uid.isEmpty()) {
                    viewState = viewState.copy(
                        child = viewState.child.copy(
                            uid = UUID.randomUUID().toString()
                        )
                    )
                }
                childInteract.saveChildUseCase(
                    viewState.child,
                    viewState.listScreenChildGroup?.filter { list ->
                        list.isChecked == true
                    }?.map {
                        ChildGroup(
                            uid = UUID.randomUUID().toString(),
                            groupId = it.group?.uid.toString(),
                            childId = viewState.child.uid
                        )
                    } ?: emptyList()
                )
                viewAction = NewChildAction.CloseClicked
            }

            is NewChildEvent.SaveClicked -> {
                obtainEvent(NewChildEvent.SaveChild(viewState.child))
            }

            is NewChildEvent.CloseClicked -> {
                viewAction = NewChildAction.CloseClicked
            }

            is NewChildEvent.SelectGroup -> {
                viewState =
                    viewState.copy(listScreenChildGroup = viewState.listScreenChildGroup?.map {
                        if (it.group?.uid == viewEvent.uidGroup)
                            return@map it.copy(isChecked = !it.isChecked)
                        else
                            return@map it
                    })
            }
        }
    }
// создает списко групп с отметками по для state Класс ItemScreenGroup
    private fun getScreenGroups(
       childGroup: List<ChildGroup> = emptyList()
    ) {
        viewModelScope.launch {
            childInteract.getGroup().collect { list ->
                viewState =
                    viewState.copy(listScreenChildGroup = list.map { group ->
                        ItemScreenChildGroup(
                            group,
                            childGroup.find { it.groupId == group.uid } != null // если группа найдена == true
                        )
                    })
            }
        }
    }
}
