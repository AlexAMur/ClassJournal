package com.catshome.classjornal


import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.catshome.classjornal.Screens.Group.GroupEvent
import com.catshome.classjornal.Screens.viewModels.GroupViewModel

@Composable
fun NewGroupScreen  (viewModel: GroupViewModel = viewModel{ GroupViewModel()}){
    val viewState by viewModel.viewState().collectAsState()
Card{
  Column {
   OutlinedTextField(
       value = viewState.nameGroup,
       onValueChange = {viewModel.obtainEvent(GroupEvent.ChangeName(it))}
   )

   Button(onClick = {viewModel.obtainEvent(GroupEvent.NextClicked)}) {
        Text("OК")
   }
  }
}
 }