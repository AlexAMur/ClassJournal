package com.catshome.classJournal.screens.Visit

import android.util.Log
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.viewModelScope
import com.catshome.classJournal.resource.R
import com.catshome.classJournal.context
import com.catshome.classJournal.domain.Child.MiniChild
import com.catshome.classJournal.domain.Visit.Visit
import com.catshome.classJournal.domain.Visit.VisitInteract
import com.catshome.classJournal.domain.communs.DayOfWeek
import com.catshome.classJournal.domain.communs.FormatDate
import com.catshome.classJournal.domain.communs.toLocalDateTimeRuString
import com.catshome.classJournal.domain.communs.toTimeString
import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.UUID
import javax.inject.Inject
import kotlin.time.Clock


@HiltViewModel
class NewVisitViewModel @Inject constructor(private val visitInteract: VisitInteract) :
    BaseViewModel<NewVisitState, NewVisitAction, NewVisitEvent>(
        installState = NewVisitState(
            selectDate = Clock.System.now().toEpochMilliseconds()
        )
    ) {
    private val exceptionHandlerVisit = CoroutineExceptionHandler { coroutineContext, throwable ->
        if (throwable.message?.contains("UID ребенка") == true) {
            viewState = viewState.copy(
                isPriceError = true,
                isSnackBarShow = true,
                errorMessage = throwable.message.toString(),
                snackBarAction = context.getString(R.string.ok),
                onDismissed = { viewState = viewState.copy(isSnackBarShow = false) },
                onAction = { viewState = viewState.copy(isSnackBarShow = false) }
            )
            return@CoroutineExceptionHandler
        }
        if (throwable.message?.contains("нулевым или отрицательным.") == true) {
            viewState = viewState.copy(
                isPriceError = true,
                errorMessage = throwable.message ?: "Error!!!"
            )
            return@CoroutineExceptionHandler
        }

        if (throwable.message?.contains("SQLITE_CONSTRAINT_UNIQUE") == true) {
            viewState = viewState.copy(
                isSnackBarShow = true,
                errorMessage = context?.getString(R.string.error_unique_child) ?: "Ошибка!!!",
                snackBarAction = context.getString(R.string.ok),
                onDismissed = { viewState = viewState.copy(isSnackBarShow = false) },
                onAction = { viewState = viewState.copy(isSnackBarShow = false) }
            )
            return@CoroutineExceptionHandler
        }
        if (throwable.message?.contains("SQLITE_CONSTRAINT_PRIMARYKEY") == true) {
            viewState = viewState.copy(
                isSnackBarShow = true,
                errorMessage = " ${context?.getString(R.string.error_primarykey_group)}",
                snackBarAction = context.getString(R.string.ok),
                onDismissed = { viewState.isSnackBarShow = false },
                onAction = { viewState.isSnackBarShow = false }
            )
            return@CoroutineExceptionHandler
        } else {
            viewState = viewState.copy(
                isSnackBarShow = true,
                errorMessage = "${context?.getString(R.string.error_save)} ${throwable.message} ",
                snackBarAction = context.getString(R.string.ok),
                onDismissed = { viewState.isSnackBarShow = false },
                onAction = { viewState.isSnackBarShow = false }
            )
            return@CoroutineExceptionHandler
        }
    }
    val TEXT_FILD_COUNT = 3
    val listTextField = List<FocusRequester>(TEXT_FILD_COUNT) { FocusRequester() }
    override fun obtainEvent(viewEvent: NewVisitEvent) {
        when (viewEvent) {
            NewVisitEvent.ClearSelect -> {
                viewState = viewState.copy(
                    searchText = viewState.searchText.copy(""),
                    selectChild = MiniChild(
                        uid = "",
                        fio = ""
                    ),
                    isSelectChild = false,
                    listChild = null
                )
            }

            is NewVisitEvent.SelectDate -> {
                viewState = viewState.copy(
                    selectDate = viewEvent.date
                )
            }

            is NewVisitEvent.SelectChild -> {
                Log.e("CLJR", "Select Child")
                viewState = viewState.copy(
                    selectChild = viewEvent.selectChild,

                    searchText = viewState.searchText.copy(
                        text = viewEvent.selectChild.fio,
                        selection = TextRange(viewEvent.selectChild.fio.length)
                    ),
                    isSelectChild = true,
                    listChild = null
                )
                Log.e("CLJR", "List ${viewState.listChild}")
            }

            NewVisitEvent.CancelClicked -> {
                viewAction = NewVisitAction.CloseScreen
            }

            is NewVisitEvent.SaveClicked -> {
                if (viewEvent.openPage == 0) {
                    if (viewState.scheduler.isEmpty()) {
                        Log.e("CLJR", "Нет данных для сохранения ${viewState.scheduler}")
                        return
                    }
                    viewModelScope.launch(context = exceptionHandlerVisit) {
                        viewState.scheduler.let { scheduler ->
                            //  visitInteract.saveVisit(scheduler)
                        }
                    }
                }

                if (viewEvent.openPage == 1) {
                    Log.e("CLJR", "даta ${viewState.selectDate?.toLocalDateTimeRuString()}")
                    if (viewState.selectChild == null) {
                        viewState = viewState.copy(
                            isSearchError = true,
                            searchErrorMessage = context.getString(R.string.error_empty_child)
                        )
                        return
                    }
                    try {
                        if (viewState.priceScreen.toInt() <= 0)
                            throw NumberFormatException()

                    } catch (e: NumberFormatException) {
                        viewState = viewState.copy(
                            isPriceError = true,
                            priceErrorMessage = context.getString(R.string.error_invalid_value)
                        )
                        return
                    }

                    viewModelScope.launch(exceptionHandlerVisit) {
                        visitInteract.saveVisit(
                            listOf(
                                Visit(
                                    uid = if (viewState.visit?.uid.isNullOrEmpty())
                                        UUID.randomUUID().toString()
                                    else viewState.visit?.uid,
                                    uidChild = viewState.selectChild?.uid,
                                    data = viewState.selectDate?.toLocalDateTimeRuString(
                                        formatDate = FormatDate.Date),
                                    price = viewState.priceScreen.toInt()
                                )
                            )
                        )
                    }
                }
                viewAction = NewVisitAction.SaveAndCloseScreen
            }

            is NewVisitEvent.ChangePrice -> {
                viewState = viewState.copy(priceScreen = viewEvent.price)
                viewState = try {
                    viewState.copy(priceScreen = viewEvent.price)
                } catch (e: NumberFormatException) {
                    viewState.copy(
                        isPriceError = true,
                        priceErrorMessage = "${context.getString(R.string.error_invalid_value)} \r\n" +
                                "${e.message}"
                    )
                }
            }

            is NewVisitEvent.Search -> {
                if (viewState.searchText.text.isEmpty()) {
                    viewState = viewState.copy(listChild = null, isSearchError = false)
                    return
                } else {
                    if (viewState.isSearchError)
                        viewState = viewState.copy(
                            isSearchError = false,
                            searchErrorMessage = ""
                        )

                    CoroutineScope(Dispatchers.Main).launch {
                        visitInteract.searchChild(viewState.searchText.text).collect {
                            val j = CoroutineScope(Dispatchers.IO).async {
                                if (it.isNullOrEmpty()) {
                                    listOf(
                                        MiniChild(
                                            uid = "",
                                            fio = "пусто"
                                        )
                                    )
                                }
                                return@async it
                            }
                            viewState = viewState.copy(listChild = j.await())
                        }
                    }
                }
            }

            NewVisitEvent.getScheduler -> {
                val scheduler =
                    MutableList<Map<String, List<Visit>>>(0) { mapOf(Pair("", listOf())) }
                CoroutineScope(Dispatchers.Main).launch {
                    visitInteract.getScheduler()?.collect { listVisit ->
                        val jod = CoroutineScope(Dispatchers.IO).async {
                            DayOfWeek.entries.forEachIndexed { index, _ ->
                                scheduler.add(
                                    index = index,
                                    listVisit.filter { it.dayOfWeek == index }
                                        .groupBy { visit ->
                                            "${visit.startLesson.toTimeString()} ${
                                                if (!visit.groupName.isNullOrEmpty()) visit.groupName else ""
                                            }"
                                        }
                                )
                            }
                            return@async scheduler
                        }
                        viewState = viewState.copy(scheduler = jod.await())
                    }
                }
            }

            NewVisitEvent.LessonClicked -> {
                //viewState = viewState.copy()
            }

            is NewVisitEvent.ShowDateDialog -> {
                viewState = viewState.copy(isShowDateDialog = viewEvent.isShow)
            }

            is NewVisitEvent.ChangePageIndex -> {
                viewState = viewState.copy(pageIndex = viewEvent.index)
            }
        }
    }
}
