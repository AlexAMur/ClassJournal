package com.catshome.classJournal.screens.Visit

import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.viewModelScope
import com.catshome.classJournal.R
import com.catshome.classJournal.context
import com.catshome.classJournal.domain.Visit.VisitInteract
import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewVisitViewModel @Inject constructor(private val visitInteract: VisitInteract) :
    BaseViewModel<NewVisitState, NewVisitAction, NewVisitEvent>(
        installState = NewVisitState(
            listVisit = emptyList()
        )
    ) {
    private val exceptionHandlerVisit = CoroutineExceptionHandler { coroutineContext, throwable ->
        if (throwable.message?.contains("UID ребенка") == true) {
            viewState = viewState.copy(
                isPriceError = true,
                isSnackbarShow = true,
                errorMessage = throwable.message.toString(),
                snackbarAction = context.getString(R.string.ok),
                onDismissed = { viewState = viewState.copy(isSnackbarShow = false) },
                onAction = { viewState = viewState.copy(isSnackbarShow = false) }
            )
            return@CoroutineExceptionHandler
        }
        if (throwable.message?.contains("нулевым или отрицательным.") == true) {
            viewState = viewState.copy(
                isPriceError =  true,
                errorMessage = throwable.message ?: "Error!!!"
            )
            return@CoroutineExceptionHandler
        }

        if (throwable.message?.contains("SQLITE_CONSTRAINT_UNIQUE") == true) {
            viewState = viewState.copy(
                isSnackbarShow = true,
                errorMessage = context?.getString(R.string.error_unique_child) ?: "Ошибка!!!",
                snackbarAction = context.getString(R.string.ok),
                onDismissed = { viewState = viewState.copy(isSnackbarShow = false) },
                onAction = { viewState = viewState.copy(isSnackbarShow = false) }
            )
            return@CoroutineExceptionHandler
        }
        if (throwable.message?.contains("SQLITE_CONSTRAINT_PRIMARYKEY") == true) {
            viewState = viewState.copy(
                isSnackbarShow = true,
                errorMessage = " ${context?.getString(R.string.error_primarykey_group)}",
                snackbarAction = context.getString(R.string.ok),
                onDismissed = { viewState.isSnackbarShow = false },
                onAction = { viewState.isSnackbarShow = false }
            )
            return@CoroutineExceptionHandler
        } else {
            viewState = viewState.copy(
                isSnackbarShow = true,
                errorMessage = "${context?.getString(R.string.error_save_group)} ${throwable.message} ",
                snackbarAction = context.getString(R.string.ok),
                onDismissed = { viewState.isSnackbarShow = false },
                onAction = { viewState.isSnackbarShow = false }
            )
            return@CoroutineExceptionHandler
        }
    }
    val TEXT_FILD_COUNT = 3
    val listTextField = List<FocusRequester>(TEXT_FILD_COUNT) { FocusRequester() }
    override fun obtainEvent(viewEvent: NewVisitEvent) {
        when (viewEvent) {
            NewVisitEvent.CancelClicked -> viewAction = NewVisitAction.CloseScreen
            NewVisitEvent.SaveClicked -> {
                if (viewState.listVisit.isEmpty()) {
                    return
                }
                viewModelScope.launch(context = exceptionHandlerVisit) {
                    visitInteract.saveVisit(viewState.listVisit)
                }
            }
            is NewVisitEvent.Search -> {}
        }
    }
}
