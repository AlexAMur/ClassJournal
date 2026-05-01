package com.catshome.classJournal.screens.Visit

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.key
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.TextRange
import androidx.lifecycle.viewModelScope
import com.catshome.classJournal.context
import com.catshome.classJournal.domain.Child.MiniChild
import com.catshome.classJournal.domain.Visit.Visit
import com.catshome.classJournal.domain.Visit.VisitInteract
import com.catshome.classJournal.domain.communs.DayOfWeek
import com.catshome.classJournal.domain.communs.FormatDate
import com.catshome.classJournal.domain.communs.toDateTimeRuString
import com.catshome.classJournal.domain.communs.toLocalDateTimeRu
import com.catshome.classJournal.domain.communs.toLocalDateTimeRuString
import com.catshome.classJournal.domain.communs.toLong
import com.catshome.classJournal.domain.communs.toTimeString
import com.catshome.classJournal.navigate.mapToVisit
import com.catshome.classJournal.resource.R
import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
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
            is NewVisitEvent.ChangePriceOnScheduler -> {
                val i = viewState.scheduler[viewEvent.dayInt]?.get(viewEvent.key)
                    ?.mapIndexed { index, visit ->
                        if (index == viewEvent.index) {
                            visit.copy(priceScreen = viewEvent.text)
                        } else
                            visit

                    }
                val y = viewState.scheduler[viewEvent.dayInt]?.toMutableMap()
                    ?.plus(Pair(viewEvent.key, i))
                viewState = viewState.copy(
                    scheduler = viewState.scheduler.toMutableList().mapIndexed { index, map ->
                        if (index == viewEvent.dayInt)
                            y
                        else
                            map
                    }
                )
            }

            is NewVisitEvent.ItemCheckClicked -> {
                val i = viewState.scheduler[viewEvent.dayInt]?.get(viewEvent.key)
                    ?.mapIndexed { index, visit ->
                        if (index == viewEvent.indexItem) {
                            visit.copy(check = viewEvent.isCheck)
                        } else
                            visit
                    }
                val y = viewState.scheduler[viewEvent.dayInt]?.toMutableMap()
                    ?.plus(Pair(viewEvent.key, i))
                viewState = viewState.copy(
                    scheduler = viewState.scheduler.toMutableList().mapIndexed { index, map ->
                        if (index == viewEvent.dayInt)
                            y
                        else
                            map
                    }
                )
            }

            is NewVisitEvent.EditVisit -> {
                viewState = viewState.copy(
                    visit = viewEvent.details.mapToVisit(),
                    selectChild = MiniChild(
                        uid = viewEvent.details.uidChild ?: "",
                        fio = viewEvent.details.fio.toString()
                    ),
                    selectDate = viewEvent.details.date?.toLocalDateTimeRu()?.toLong(),
                    isSelectChild = true,
                    searchText = viewState.searchText.copy(viewEvent.details.fio.toString()),
                    priceScreen = viewEvent.details.price.toString()
                )
            }

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
                viewState = viewState.copy(
                    selectChild = viewEvent.selectChild,
                    searchText = viewState.searchText.copy(
                        text = viewEvent.selectChild.fio,
                        selection = TextRange(viewEvent.selectChild.fio.length)
                    ),
                    isSelectChild = true,
                    listChild = null
                )
            }

            NewVisitEvent.CancelClicked -> {
                viewAction = NewVisitAction.CloseScreen
            }

            is NewVisitEvent.SaveClicked -> {
//                Log.e("CLJR", "Save visit = ${viewState.visit}")
                //тут будет функция сохранения
                if (viewEvent.openPage == 0) {
                    var listToSave = mutableListOf<Visit>()
                    if (viewState.scheduler.isNotEmpty()) {
                        viewState.dateOnPage?.date?.dayOfWeek?.ordinal?.let { index ->
                            viewState.scheduler[index]?.map { map ->
                                map.value?.let { listVisit ->
                                    listToSave.addAll(listVisit.filter { it.check }.toMutableList())

                                }
                            }
                        }
                    }
                    if (listToSave.isNotEmpty()) {
                        listToSave.forEachIndexed { index, visit ->
                            try {
                                visit.priceScreen?.toInt()?.let { priceScreen ->
                                    if (priceScreen < 0)
                                        throw NumberFormatException(
                                            "${
                                                context.getString(
                                                    R.string.error_invalid_valuePrice
                                                )
                                            } Не допустимое заначение ${viewState.priceScreen}"
                                        )


                                    listToSave[index] = visit.copy(price = priceScreen)

                                }
                                //listToSave[index] = visit.copy(data = "${viewState.dateOnPage?.toDateTimeRuString(formatDate = FormatDate.Date)}")

                                Log.e("CLJR", "Дата в dateOn page ${viewState.dateOnPage.toString()}")
                                Log.e("CLJR", "Даta после  ${viewState.dateOnPage?.toDateTimeRuString(formatDate = FormatDate.Date)}")

                            } catch (_: NumberFormatException) {
                                viewState.onDismissDialog = {
                                    viewState = viewState.copy(isShowDialog = false)
                                }
                                viewState = viewState.copy(
                                    errorMessage = context.getString(R.string.error_invalid_valuePrice),
                                    errorMessageHaider = context.getString(R.string.error_invalid_valuePrice),
                                    isShowDialog = true
                                )
                                return
                            }

                                 listToSave[index] = visit.copy(
                                     uid= visit.uid?: UUID.randomUUID().toString(),
//                                     uid = if (visit.uid.isNullOrEmpty())  UUID.randomUUID().toString() else visit.uid,
                            data = "${viewState.dateOnPage?.toDateTimeRuString()}")
                            Log.e("CLJR", "data on list ${listToSave}")
                        }
                        Log.e("CLJR", "Даta ${viewState.dateOnPage}")
                        Log.e("CLJR", "Данные для сохранения ${listToSave}")
                        viewModelScope.launch() {
                            listToSave.let { scheduler ->
                                visitInteract.saveVisit(scheduler)
                            }
                        }
                    }
                }
                if (viewEvent.openPage == 1) {
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

                    viewModelScope.launch() {
                        visitInteract.saveVisit(
                            listOf(
                                Visit(
                                    uid = if (viewState.visit?.uid.isNullOrEmpty())
                                        UUID.randomUUID().toString()
                                    else viewState.visit?.uid,
                                    uidChild = viewState.selectChild?.uid,
                                    data = viewState.selectDate?.toLocalDateTimeRuString(
                                        formatDate = FormatDate.Date
                                    ),
                                    price = viewState.priceScreen.toInt()
                                )
                            )
                        )
                    }
                }
                viewAction = NewVisitAction.SaveAndCloseScreen
            }

            is NewVisitEvent.ChangePrice -> {
                try {
                    viewState = viewState.copy(priceScreen = viewEvent.price)
                    //iewState.copy(priceScreen = viewEvent.price)
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
                val schedulerTmp =
                    MutableList<Map<String, List<Visit>>>(0) { mapOf(Pair("", listOf())) }
                CoroutineScope(Dispatchers.Main).launch {
                    visitInteract.getScheduler()?.collect { listVisit ->
                        val jod = CoroutineScope(Dispatchers.IO).async {
                            DayOfWeek.entries.forEachIndexed { index, _ ->
                                viewState.lessonChecked[index].clear()
                                schedulerTmp.add(
                                    index = index,
                                    listVisit.filter { it.dayOfWeek == index }
                                        .groupBy { visit ->
                                            viewState.lessonChecked[index] =
                                                viewState.lessonChecked[index].plus(false)
                                                    .toMutableList()
                                            "${visit.startLesson.toTimeString()} ${
                                                if (!visit.groupName.isNullOrEmpty()) visit.groupName else ""
                                            }"
                                        }
                                )
                            }
                            return@async schedulerTmp
                        }
                        viewState = viewState.copy(scheduler = jod.await())
//                        Log.e("CLJR", "listCheck ${viewState.scheduler}")
                        //Log.e("CLJR", "Scheduler ${viewState.scheduler}")
                    }
                }
            }

            is NewVisitEvent.LessonClicked -> {
                val a = viewState.scheduler[viewEvent.dayInt].let {
                    it?.keys?.indexOf(viewEvent.key)?.let {
                        viewState.lessonChecked[viewEvent.dayInt][it] = viewEvent.isCheck
                    }
                }

                val i = viewState.scheduler[viewEvent.dayInt]?.get(viewEvent.key)
                    ?.mapIndexed { index, visit ->
                        visit.copy(check = viewEvent.isCheck)
                    }
                val y = viewState.scheduler[viewEvent.dayInt]?.toMutableMap()
                    ?.plus(Pair(viewEvent.key, i))
                viewState = viewState.copy(
                    scheduler = viewState.scheduler.toMutableList().mapIndexed { index, map ->
                        if (index == viewEvent.dayInt)
                            y
                        else
                            map
                    }
                )
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

