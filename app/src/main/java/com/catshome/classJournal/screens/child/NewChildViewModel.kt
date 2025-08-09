package com.catshome.classJournal.screens.child

import android.util.Log
import com.catshome.classJournal.domain.Child.ChildInteractor
import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewChildViewModel @Inject constructor(private val childInteract: ChildInteractor) :
    BaseViewModel<NewChildState, NewChildAction, NewChildEvent>(installState = NewChildState()){
        init {
            viewState = NewChildState()
        }
    fun nameChange(newValue: String){
        viewState =viewState.copy(name = newValue)
    }
    fun surnameChange(newValue: String){
        viewState =viewState.copy(surname = newValue)
    }
    fun phoneChange(newValue: String){
        viewState =viewState.copy(phone = newValue)
    }

    fun birthdayChange(newValue: String){
        viewState =viewState.copy(birthday = newValue)
    }
    fun noteChange(newValue: String){
        viewState =viewState.copy(note = newValue)
    }

    override fun obtainEvent(viewEvent: NewChildEvent) {
        when(viewEvent){
            is NewChildEvent.OpenChild -> {childInteract.getChildByID(viewEvent.uid
                )}
            is NewChildEvent.SaveChild -> {
                childInteract.saveChildUseCase(viewEvent.child)
            viewAction = NewChildAction.SaveChild()
            }
        }
    }

 }