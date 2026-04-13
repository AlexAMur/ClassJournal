package com.catshome.classJournal.screens.Visit

import android.util.Log
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.viewModelScope
import com.catshome.classJournal.resource.R
import com.catshome.classJournal.context
import com.catshome.classJournal.domain.Visit.Visit
import com.catshome.classJournal.domain.Visit.VisitInteract
import com.catshome.classJournal.domain.communs.DayOfWeek
import com.catshome.classJournal.domain.communs.toLocalDateTimeRuString
import com.catshome.classJournal.domain.communs.toTimeString
import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class NewVisitViewModel @Inject constructor(private val visitInteract: VisitInteract) :
    BaseViewModel<NewVisitState, NewVisitAction, NewVisitEvent>(
        installState = NewVisitState()
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
                    searchText = "",
                    selectChild = null,
                    visit = viewState.visit?.copy(
                        uidChild = null,
                        fio = ""
                    )
                )
            }

            is NewVisitEvent.SelectDate -> {
                viewState = viewState.copy(selectDate = viewEvent.date,
                    visit = viewState.visit?.copy(data = viewEvent.date?.toLocalDateTimeRuString())
                )
                Log.e("CLJR", " Data change ${viewSta te.visit?.data} select date ${viewState.selectDate?.toLocalDateTimeRuString()}")
            }

            is NewVisitEvent.SelectChild -> {
                viewState = viewState.copy(
                    visit = viewState.visit?.copy(
                        uidChild = viewEvent.selectChild.uid,
                        fio = viewEvent.selectChild.fio
                    ),
                    searchText = viewEvent.selectChild.fio,
                    listChild = null
                )
            }

            NewVisitEvent.CancelClicked -> {
                viewAction = NewVisitAction.CloseScreen
            }

            NewVisitEvent.SaveClicked -> {
                if (viewState.scheduler.isEmpty()) {
                    Log.e("CLJR", "Нет данных для сохранения ${viewState.scheduler}")
                    return
                }
                viewModelScope.launch(context = exceptionHandlerVisit) {
                    viewState.scheduler?.let { scheduler ->
                        //  visitInteract.saveVisit(scheduler)
                    }
                }
            }

            is NewVisitEvent.ChangePrice -> {
                viewState = viewState.copy(priceScreen = viewEvent.price)
                try {
                    viewState =
                        viewState.copy(visit = viewState.visit?.copy(price = viewEvent.price.toInt()))
                } catch (e: NumberFormatException) {
                    viewState = viewState.copy(
                        isPriceError = true,
                        priceErrorMessage = "${context.getString(R.string.error_invalid_value)} \r\n" +
                                "${e.message}"
                    )
                }
            }

            is NewVisitEvent.Search -> {
                viewState = viewState.copy(searchText = viewEvent.searchText)
                if (viewState.searchText.isEmpty()) {
                    viewState = viewState.copy(listChild = null, isSearchError = false)
                    return
                } else {
                    if (viewState.isSearchError)
                        viewState = viewState.copy(
                            isSearchError = false,
                            searchErrorMessage = ""
                        )
                    viewModelScope.launch {
                        visitInteract.searchChild(viewEvent.searchText).collect {
                            viewState = viewState.copy(listChild = it)
                            Log.e("CLJR", "List child ${viewState.listChild}")
                            Log.e("CLJR", "IT ${it}")
                        }
                    }
                }
            }

            NewVisitEvent.getScheduler -> {
                val scheduler =
                    MutableList<Map<String, List<Visit>>>(0) { mapOf(Pair("", listOf())) }
                Log.e("CLJR", "Get scheduler")
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
//                        scheduler.forEachIndexed { index1, map ->
//                            Log.e("CLJR", " index,, ${index1}S= $map")
//                        }
                            //    Log.e("CLJR", " ,,  $scheduler")

                            return@async scheduler
                        }
                     //   val s = jod.await()
                        //Log.e("CLJR","Lisit schrduler  $s")
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
                Log.e("CLJR", "Page ${viewState.pageIndex}")
            }
        }
    }
}
