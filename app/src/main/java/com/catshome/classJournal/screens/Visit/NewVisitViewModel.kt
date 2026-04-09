package com.catshome.classJournal.screens.Visit

import android.util.Log
import androidx.compose.runtime.key
import androidx.compose.ui.focus.FocusRequester
import androidx.lifecycle.viewModelScope
import com.catshome.classJournal.resource.R
import com.catshome.classJournal.context
import com.catshome.classJournal.domain.Visit.Visit
import com.catshome.classJournal.domain.Visit.VisitInteract
import com.catshome.classJournal.domain.communs.DayOfWeek
import com.catshome.classJournal.domain.communs.toTimeString
import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Collections.emptyList
import javax.inject.Inject


@HiltViewModel
class NewVisitViewModel @Inject constructor(private val visitInteract: VisitInteract) :
    BaseViewModel<NewVisitState, NewVisitAction, NewVisitEvent>(
        installState = NewVisitState(
            //listVisit = emptyList()
        )
    ) {
//    init {
//        obtainEvent(NewVisitEvent.getScheduler)
//    }

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
                isPriceError = true,
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
                errorMessage = "${context?.getString(R.string.error_save)} ${throwable.message} ",
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
            NewVisitEvent.CancelClicked -> {
                viewAction = NewVisitAction.CloseScreen
            }

            NewVisitEvent.SaveClicked -> {
                if (viewState.scheduler.isNullOrEmpty()) {
                    Log.e("CLJR", "Нет данных для сохранения ${viewState.scheduler}")
                    return
                }
                viewModelScope.launch(context = exceptionHandlerVisit) {
                    viewState.scheduler?.let { scheduler ->
                        //  visitInteract.saveVisit(scheduler)
                    }
                }
            }

            is NewVisitEvent.Search -> {}
            NewVisitEvent.getScheduler -> {
                val scheduler= MutableList<Map<String, List<Visit>>>(0){mapOf(Pair("", listOf()))}
                Log.e("CLJR", "Get scheduler")
                CoroutineScope(Dispatchers.Main).launch {
                    val t = visitInteract.getScheduler()?.collect { listVisit ->
                val jod = CoroutineScope(Dispatchers.IO).async {

                        DayOfWeek.entries.forEachIndexed { index, _ ->
                             scheduler.add(index = index,listVisit.filter { it.dayOfWeek == index }
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
                    Log.e("CLJR", " ,,  $scheduler")

                    return@async scheduler
                    }
                        val s = jod.await()
                        //Log.e("CLJR","Lisit schrduler  $s")
                        viewState = viewState.copy(scheduler = scheduler)

                }

                }
            }

            NewVisitEvent.LessonClicked -> {
                viewState = viewState.copy()
            }

            is NewVisitEvent.ChangePageIndex -> {
                viewState = viewState.copy(pageIndex = viewEvent.index)
                Log.e("CLJR", "Page ${viewState.pageIndex}")

            }
        }
    }
}
