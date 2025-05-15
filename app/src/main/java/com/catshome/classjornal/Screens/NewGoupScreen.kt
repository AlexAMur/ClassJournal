package com.catshome.classjornal


import androidx.compose.foundation.layout.Column
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.catshome.classjornal.Screens.viewModels.GroupViewModel

@Composable
fun NewGroupScreen  (viewModel: GroupViewModel){
Card{
  Column {
   OutlinedTextField(
       value = viewModel.groupName,
       onValueChange = {viewModel.upDateGroup(it)}
   )
   Button(onClick = {viewModel.onClickSave()}) {
    Text("OК")
   }
  }
}
 }