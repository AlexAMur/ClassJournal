package com.catshome.classJournal.screens.Visit.ListVisit

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.catshome.classJournal.context
import com.catshome.classJournal.domain.Visit.Visit
import com.catshome.classJournal.domain.Visit.VisitInteract
import com.catshome.classJournal.domain.communs.DATE_FORMAT_RU
import com.catshome.classJournal.domain.communs.toDateTimeRuString
import com.catshome.classJournal.domain.communs.toLong
import com.catshome.classJournal.resource.R
import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.time.Clock
import kotlin.time.Duration

/*
 *Удаление записи VisitEvent VisitDelete сначала уделяем из списка viewState.itemList
 * функция deleteItemFromList
 */


@HiltViewModel
class VisitListViewModel @Inject constructor(private val visitInteractor: VisitInteract) :
    BaseViewModel<VisitListState, VisitListAction, VisitListEvent>(
        installState = VisitListState(
            beginDate = Clock.System.now().minus(
                value = 1,
                unit = DateTimeUnit.MONTH,
                timeZone = TimeZone.currentSystemDefault()
            ).toLocalDateTime(
                timeZone = TimeZone.currentSystemDefault()
            ),
            endDate = Clock.System.now().toLocalDateTime(
                timeZone = TimeZone.currentSystemDefault()
            )
        )
    ) {


    override fun obtainEvent(viewEvent: VisitListEvent) {

        when (viewEvent) {
            is VisitListEvent.EditVisit -> {
                CoroutineScope(Dispatchers.Main).launch {
                    val job = CoroutineScope(Dispatchers.IO).async {
                        visitInteractor.getVisitByUid(viewEvent.uidVisit)
                    }
                    job.await()?.let { visit ->
                        viewAction = VisitListAction.EditVisit(visit)
                    }
                }
            }

            VisitListEvent.NewVisit -> {
                viewAction = VisitListAction.NewVisit
            }

            is VisitListEvent.ShowFAB -> {
                viewState = viewState.copy(isShowFAB = viewEvent.isShowFAB)
            }

            is VisitListEvent.DeleteVisit -> {
                viewState.deleteVisit?.let { deleteVisit ->
                    deleteVisitFromDB(deleteVisit)
                }

//                if(viewEvent.uidVisit != viewState.deleteVisit?.uid)
                deleteItemFromList(key = viewEvent.key, uidVisit = viewEvent.uidVisit)
                viewState = viewState.copy(
                    messageSnackBar = context.getString(R.string.message_cancel),
                    snackBarLabel = context.getString(R.string.bottom_yes),
                    deleteKey = viewEvent.key,
                    onDismissed = {
                        viewState = viewState.copy(messageSnackBar = null)
                        viewState.deleteVisit?.let { deleteVisit ->
                            deleteVisitFromDB(deleteVisit)
                        }

                    },
                    onAction = {
                        viewState = viewState.copy(
                            messageSnackBar = null,
                            onDismissed = null,
                        )
                        unDeleteItem()
                    }
                )
            }

            VisitListEvent.Reload -> {
                viewModelScope.launch {
                    visitInteractor.getVisitByPeriod(
                        begin = viewState.beginDate.toLong(),
                        end = viewState.endDate.toLong()
                    )?.collect { listVisit ->
                        viewState = viewState.copy(
                            listVisit =
                                listVisit.map {
                                    if (viewState.deleteVisit?.uid == it.uid)
                                        it.copy(isDelete = true)
                                    else
                                        it

                                }.groupBy {
                                    it.data?.substring(
                                        0,
                                        DATE_FORMAT_RU.length
                                    ) ?: ""
                                })
                    }
                }
            }
        }
    }

    private fun deleteVisitFromDB(visit: Visit) {
        visit.uid?.let { uidVisit ->
            viewModelScope.launch {
                visitInteractor.deleteVisit(uidVisit)
            }
        }
        viewState.deleteVisit = null
    }

    fun deleteItemFromList(key: String, uidVisit: String) {
        viewState.listVisit.let { mapVisit ->
//            if (viewState.deleteVisit?.uid?.isNotEmpty() == true)
//                viewState.deleteVisit = mapVisit[key]?.find { uidVisit == it?.uid }

            viewState = viewState.copy(
                listVisit = mapVisit.plus(
                    Pair(
                        key,
                        mapVisit[key]?.map {
                            if (it?.uid == uidVisit) {
//                                it.copy(isDelete = true)
                                viewState.deleteVisit = it.copy(isDelete = true)
                                return@map viewState.deleteVisit
                            } else
                                it
                        }
                    )
                )
            )
        }

//        viewState = viewState.copy(
//            deleteKey = key,
//            messageSnackBar = null,
//            onDismissed = null
//        )
    }


    fun unDeleteItem() {
        viewState.listVisit?.let { mapVisit ->
            viewState.deleteKey?.let { deleteKey ->
                viewState.deleteVisit?.isDelete = false
                viewState = viewState.copy(
                    listVisit = mapVisit.plus(
                        Pair(
                            deleteKey,
                            mapVisit[viewState.deleteKey]?.map {
                                if (it?.uid == viewState.deleteVisit?.uid)
                                    it?.copy(isDelete = false)
                                else
                                    it
                            }
                        )
                    )
                )
            }
            viewState = viewState.copy(deleteVisit = null)

//            viewState = viewState.copy(
//                listVisit = mapVisit.plus(
//                    Pair(
//                        viewState.deleteKey!!,
//                        mapVisit[viewState.deleteKey]?.plus(viewState.deleteVisit)
//                    )
//                )
//            )
        }
    }
}


