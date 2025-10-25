package com.catshome.classJournal.screens.viewModels

import android.annotation.SuppressLint
import com.catshome.classJournal.R
import com.catshome.classJournal.context
import com.catshome.classJournal.domain.Group.GroupInteractor
import com.catshome.classJournal.domain.Group.Models.Group
import com.catshome.classJournal.screens.group.ComposeAction
import com.catshome.classJournal.screens.group.NewGroupEvent
import com.catshome.classJournal.screens.group.NewGroupState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewGroupViewModel @Inject constructor(private val groupInteractor: GroupInteractor) :
    BaseViewModel<NewGroupState, ComposeAction, NewGroupEvent>(installState = NewGroupState()) {
    @SuppressLint("SuspiciousIndentation")
    val cs = CoroutineScope(Dispatchers.IO)
    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        if (throwable.message?.contains("SQLITE_CONSTRAINT_UNIQUE") == true) {
            viewState = viewState.copy(
                isError = true,
                errorMessage = context?.getString(R.string.error_unique_name_group) ?: "Ошибка!!!"
            )
            return@CoroutineExceptionHandler
        }
        if (throwable.message?.contains("SQLITE_CONSTRAINT_PRIMARYKEY") == true) {
            viewState = viewState.copy(
                isError = true,
                errorMessage = " ${context?.getString(R.string.error_primarykey_group)}"
            )
            return@CoroutineExceptionHandler
        } else {
            viewState = viewState.copy(
                isError = true,
                errorMessage = "${context?.getString(R.string.error_save_group)} ${throwable.message} "
            )

        }
    }

    override fun obtainEvent(viewEvent: NewGroupEvent) {
        when (viewEvent) {
            is NewGroupEvent.OpenGroup -> {
                viewState = viewState.copy(groupInteractor.getGroupByID(viewEvent.id))
            }

            is NewGroupEvent.ChangeName -> {
                if (viewState.isError)
                    viewState = viewState.copy(isError = false)
                viewState = viewState.copy(nameGroup = viewEvent.nameGroup)
            }

            is NewGroupEvent.DeleteGroup -> {
                viewState = viewState.copy(isDelete = viewState.isDelete)
            }

            NewGroupEvent.ActionInvoked -> viewAction = ComposeAction.CloseScreen
            NewGroupEvent.SaveClicked -> {
                if (viewState.nameGroup.trim() == "" || viewState.nameGroup.isEmpty())
                    viewState = viewState.copy(
                        isError = true,
                        errorMessage = context?.getString(R.string.error_name_group) ?: "пусто"
                    )
                else {
                    cs.launch(exceptionHandler) {
                        groupInteractor.saveGroupUseCase(
                            Group(
                                viewState.uid,
                                viewState.nameGroup.trim(),
                                viewState.isDelete
                            )
                        )
                        viewAction = ComposeAction.CloseScreen
                    }
                }
            }
            NewGroupEvent.NextClicked -> viewAction = null
        }
    }
}
