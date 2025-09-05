package com.catshome.classJournal.screens.child

import android.util.Log
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.ui.focus.FocusRequester
import com.catshome.classJournal.child.ChildGroupsRepositoryImpl
import com.catshome.classJournal.domain.Child.ChildGroup
import com.catshome.classJournal.domain.Child.ChildGroupRepository
import com.catshome.classJournal.domain.Child.ChildInteractor
import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewChildViewModel @Inject constructor(private val childInteract: ChildInteractor,
                                            private val childGroups: ChildGroupsRepositoryImpl) :
    BaseViewModel<NewChildState, NewChildAction, NewChildEvent>(installState = NewChildState()){
        val TEXT_FILD_COUNT =4
        val listTextField =List<FocusRequester>(TEXT_FILD_COUNT){FocusRequester()}
        val groups = childGroups.getGroups()
    init {
            viewState = NewChildState()

        }
    var focusRequester: FocusRequester= FocusRequester()
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
            is NewChildEvent.OpenChild -> {

                childInteract.getChildByID(viewEvent.uid)
            }
            is NewChildEvent.SaveChild -> {
                childInteract.saveChildUseCase(viewEvent.child)
            viewAction = NewChildAction.SaveChild()
            }
        }
    }

 }