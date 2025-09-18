package com.catshome.classJournal.screens.Visit

import com.catshome.classJournal.domain.Group.GroupInteractor
import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VisitListViewModel @Inject constructor(private val groupInteractor: GroupInteractor) :
    BaseViewModel<VisitListState, VisitListAction, VisitListEvent>(installState = VisitListState()) {

        override fun obtainEvent(viewEvent: VisitListEvent) {
            TODO("Not yet implemented")
        }
    }
