package com.catshome.classJournal.screens.child

import android.util.Log
import androidx.compose.ui.focus.FocusRequester
import com.catshome.classJournal.child.ChildGroupsRepositoryImpl
import com.catshome.classJournal.domain.Child.Child
import com.catshome.classJournal.domain.Child.ChildInteractor
import com.catshome.classJournal.screens.group.ComposeAction
import com.catshome.classJournal.screens.viewModels.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewChildViewModel @Inject constructor(
    private val childInteract: ChildInteractor,
    private val childGroups: ChildGroupsRepositoryImpl
) :
    BaseViewModel<NewChildState, ComposeAction, NewChildEvent>(installState = NewChildState(
        child = Child(
                name = "Vasia",
            surname = "Ivanov",
            birthday = "10.11.2015",
            phone =  "+7904-035-89-11",
            note = "dude"

        ),
    )) {
    val TEXT_FILD_COUNT = 4
    val listTextField = List<FocusRequester>(TEXT_FILD_COUNT) { FocusRequester() }
    val groups = childGroups.getGroups()

    fun nameChange(newValue: String) {
        Log.e("CLJR", "indexFocus before- ${viewState.indexFocus}")
         viewState= viewState.copy(child = viewState.child.copy(name = newValue))
        Log.e("CLJR", "indexFocus after- ${viewState.indexFocus}")
    }

    fun surnameChange(newValue: String) {
        viewState= viewState.copy(child = viewState.child.copy(surname =  newValue))
    }

    fun phoneChange(newValue: String) {
        viewState= viewState.copy(child = viewState.child.copy(phone = newValue))
    }

    fun birthdayChange(newValue: String) {
        viewState= viewState.copy(child = viewState.child.copy(birthday = newValue))
    }

    fun noteChange(newValue: String) {
        viewState= viewState.copy(child = viewState.child.copy(note = newValue))
    }

    override fun obtainEvent(viewEvent: NewChildEvent) {
        when (viewEvent) {
            is NewChildEvent.OpenChild -> {
                val child = childInteract.getChildByID(viewEvent.uid)

                viewState.copy(child)
            }

            is NewChildEvent.SaveChild -> {
                childInteract.saveChildUseCase(viewEvent.child)
                viewAction = ComposeAction.Success
            }

            is NewChildEvent.SaveClicked -> {
                obtainEvent(NewChildEvent.SaveChild(
                    viewState.child
//                        uid = viewState.uid,
//                        name = viewState.name,
//                        surname = viewState.surname,
//                        birthday = viewState.birthday,
//                        phone = viewState.phone,
//                        note = viewState.note,
//                        isDelete = viewState.isDelete
                                    ))

            }

            is NewChildEvent.CloseClicked -> {
                viewAction = ComposeAction.CloseScreen
            }
        }
    }
}
