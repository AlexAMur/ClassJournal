package com.catshome.classJournal.screens.Visit

import androidx.compose.ui.focus.FocusRequester
import com.catshome.classJournal.domain.Visit.VisitInteract
import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class NewVisitViewModel @Inject constructor(private val visitInteract: VisitInteract) :
    BaseViewModel<NewVisitState, VisitListAction, NewVisitEvent>(installState = NewVisitState(listPays = emptyList())) {
    val TEXT_FILD_COUNT = 3
    val listTextField = List<FocusRequester>(TEXT_FILD_COUNT) { FocusRequester() }
    override fun obtainEvent(viewEvent: NewVisitEvent) {
       when(viewEvent){
           NewVisitEvent.CancelClicked -> TODO()
           NewVisitEvent.SaveClicked ->{
               if(viewState.listPays.isEmpty()){
                   TODO("Вывести сообщение о незаполненом листе")
                   return
               }
               visitInteract.saveVisit()
           }
           is NewVisitEvent.Search -> {}
       }
    }
}
