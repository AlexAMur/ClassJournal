package com.catshome.classJournal.screens.Visit.ListVisit

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.catshome.classJournal.context
import com.catshome.classJournal.domain.Visit.VisitInteract
import com.catshome.classJournal.domain.communs.DATE_FORMAT_RU
import com.catshome.classJournal.resource.R
import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.get
import kotlin.collections.minus
import kotlin.collections.plus

/*
 *Удаление записи VisitEvent VisitDelete сначала уделяем из списка viewState.itemList
 * функция deleteItemFromList
 */


@HiltViewModel
class VisitListViewModel @Inject constructor(private val visitInteractor: VisitInteract) :
    BaseViewModel<VisitListState, VisitListAction, VisitListEvent>(installState = VisitListState()) {

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
//            is VisitListEvent.isDelete -> {
//                viewState.listVisit[viewEvent.key]?.let { list ->
//                    viewState = viewState.copy(
//                        listVisit = viewState.listVisit.plus(
//                            mapOf(
//                                Pair(
//                                    viewEvent.key, viewState.listVisit[viewEvent.key]?.plus(
//                                        list.get(viewEvent.index)?.copy(isDelete = true)
//
//                                    )
//                                )
//                            )
//                        )
//                    )
//                }
//                Log.e("CLJR", "isDelete ${viewState.listVisit}")
//            }

            VisitListEvent.NewVisit -> {
                viewAction = VisitListAction.NewVisit
            }

            is VisitListEvent.ShowFAB -> {
                viewState = viewState.copy(isShowFAB = viewEvent.isShowFAB)
            }

            is VisitListEvent.DeleteVisit -> {
                deleteItemFromList(key = viewEvent.key, uidVisit = viewEvent.uidVisit)
                viewState = viewState.copy(
                    messageSnackBar = context.getString(R.string.message_cancel),
                    snackBarLabel = context.getString(R.string.bottom_yes),
                    deleteKey = viewEvent.key,
                    onDismissed = {
                        viewState = viewState.copy(messageSnackBar = null)
                        viewEvent.uidVisit?.let { uidVisit ->
                            viewModelScope.launch {
                                viewEvent.uidVisit?.let {
                                    visitInteractor.deleteVisit(uidVisit)
                                }
                            }
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
                    visitInteractor.getVisitAll()?.collect { listVisit ->
                        viewState = viewState.copy(
                            listVisit =
                                listVisit.groupBy {
                                    it.data?.substring(
                                        0,
                                        DATE_FORMAT_RU.length
                                    )!!
                                })
                    }
                }
            }
        }
    }

    fun deleteItemFromList(key: String, uidVisit: String) {

        viewState.listVisit?.let { mapVisit ->
            viewState.deleteVisit = mapVisit[key]?.find { uidVisit == it?.uid }

            viewState = viewState.copy(
                listVisit = mapVisit.plus(
                    Pair(
                        key,
                        mapVisit[key]?.minus(viewState.deleteVisit)
                    )
                )
            )
        }
        viewState = viewState.copy(deleteKey = key)
        Log.e("CLJR", "List after delete: ${viewState.listVisit}")
    }

    fun unDeleteItem() {
        viewState.listVisit?.let { mapVisit ->
            viewState.deleteVisit?.isDelete = false
            viewState = viewState.copy(
                listVisit = mapVisit.plus(
                    Pair(
                        viewState.deleteKey!!,
                        mapVisit[viewState.deleteKey]?.plus(viewState.deleteVisit)
                    )
                )
            )
        }
        Log.e("CLJR", "List after Undelete: ${viewState.listVisit}")
    }
}


