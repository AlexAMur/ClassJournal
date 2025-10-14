package com.catshome.classJournal.screens.viewModels

import androidx.lifecycle.viewModelScope
import com.catshome.classJournal.R
import com.catshome.classJournal.context
import com.catshome.classJournal.domain.Group.GroupInteractor
import com.catshome.classJournal.domain.Group.Models.Group
import com.catshome.classJournal.screens.group.ComposeAction
import com.catshome.classJournal.screens.group.NewGroupEvent
import com.catshome.classJournal.screens.group.NewGroupState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewGroupViewModel @Inject constructor(private val groupInteractor: GroupInteractor) :
    BaseViewModel<NewGroupState, ComposeAction, NewGroupEvent>(installState = NewGroupState()) {
    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->

        viewState = viewState.copy(
            isError = true, errorMessage = (if (throwable.message?.contains("UNIQUE") == true)
                context?.getString(R.string.error_unique_name_group) else
                context?.getString(R.string.error_name_group)).toString()
        )
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
                        errorMessage = context?.getString(R.string.error_name_group)?:"пусто"

                    )
                else {

                    viewModelScope.launch(exceptionHandler) {
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
