package com.catshome.classJournal.screens.child

import android.util.Log
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.viewModelScope
import com.catshome.classJournal.R
import com.catshome.classJournal.child.ChildGroupsRepositoryImpl
import com.catshome.classJournal.context
import com.catshome.classJournal.domain.Child.Child
import com.catshome.classJournal.domain.Child.ChildGroup
import com.catshome.classJournal.domain.Child.ChildInteractor
import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
    val TEXT_FILD_COUNT = 5
    val listTextField = List<FocusRequester>(TEXT_FILD_COUNT) { FocusRequester() }

    init {
        getScreenGroups()
    }

    private val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
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
            Log.e("CLJR", "Save Error - ${throwable.message}")

        }
    }

    fun nameChange(newValue: String) {
        viewState = viewState.copy(child = viewState.child.copy(name = newValue))
        if (viewState.child.name.isNotEmpty())
            viewState = viewState.copy(isNameError = false)
    }

    fun surnameChange(newValue: String) {
        viewState = viewState.copy(child = viewState.child.copy(surname = newValue))
        if (viewState.child.surname.isNotEmpty())
            viewState = viewState.copy(isSurnameError = false)
    }

    fun phoneChange(newValue: String) {
        viewState = viewState.copy(child = viewState.child.copy(phone = newValue))
    }

    fun birthdayChange(newValue: String) {
        viewState = viewState.copy(child = viewState.child.copy(birthday = newValue))
    }

    fun saldoChange(newValue: String) {
        viewState = viewState.copy(startSaldo = newValue, isSaldoError = false)
    }

    fun noteChange(newValue: String) {
        viewState = viewState.copy(child = viewState.child.copy(note = newValue))
    }

    override fun obtainEvent(viewEvent: NewChildEvent) {
        when (viewEvent) {
            is NewChildEvent.newChild -> {
                viewState = viewState.copy(isNewChild = true)
            }

            is NewChildEvent.ReloadScreen -> {}
            is NewChildEvent.OpenChild -> {
                childInteract.getChildByID(viewEvent.uid)?.let { child ->
                    viewModelScope.launch {
                        childGroups.getChildGroups(child.uid).collect { getScreenGroups(it) }
                    }
                    viewState = viewState.copy(child = child)
                }
            }

            is NewChildEvent.SaveChild -> {
                if (viewEvent.child.name.isEmpty())
                    viewState = viewState.copy(isNameError = true)
                if (viewEvent.child.surname.isEmpty())
                    viewState = viewState.copy(isSurnameError = true)
                try {
                    viewState.startSaldo.toInt()
                } catch (e: NumberFormatException) {
                    viewState = viewState.copy(
                        isSaldoError = true,
                        saldoError = context.getString(R.string.error_save_group)
                    )
                    return
                }
                if (!viewState.isNameError && !viewState.isSurnameError) {
                    if (viewState.child.uid.isEmpty()) {
                        viewState = viewState.copy(
                            child = viewState.child.copy(
                                uid = UUID.randomUUID().toString(),
                                saldo = viewState.startSaldo.toInt()
                            )
                        )
                    }
                    CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {
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
                           }?:emptyList()
                        )
                        viewAction = NewChildAction.CloseClicked
                    }
                }
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
                    }.sortedBy { it.group?.name })
            }
        }
    }
}
