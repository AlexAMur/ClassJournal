package com.catshome.classjornal


import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.catshome.ClassJournal.localNavHost
import com.catshome.classjornal.Screens.Group.ComposeAction
import com.catshome.classjornal.Screens.Group.GroupEvent
import com.catshome.classjornal.Screens.viewModels.GroupViewModel


@Composable
fun NewGroupScreen(viewModel: GroupViewModel =viewModel()) {


    val outerNavigation= localNavHost.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val viewState by viewModel.viewState().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)
    Card {
        Column {
            OutlinedTextField(modifier = Modifier.fillMaxWidth().padding(16.dp),
                value = viewState.nameGroup,
                onValueChange = { viewModel.obtainEvent(GroupEvent.ChangeName(it)) }
            )

            Button(onClick = { viewModel.obtainEvent(GroupEvent.SaveClicked) }) {
                Text("OК")
            }
        }
    }
    when (viewAction) {
        ComposeAction.Success -> {
            keyboardController?.hide()
            outerNavigation.popBackStack()
            viewModel.clearAction()
        }

//        ComposeAction.Error -> {
//            viewModel.clearAction()
//        }
        ComposeAction.NextClicked->{}
        ComposeAction.CloseScreen -> {
            keyboardController?.hide()
            outerNavigation.popBackStack()
            viewModel.clearAction()
        }

        null -> {}
    }
}