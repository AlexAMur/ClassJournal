package com.catshome.classJournal


import android.R.attr.text
import android.annotation.SuppressLint
import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.Card

import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.catshome.classJournal.screens.child.TextField




@SuppressLint("LocalContextConfigurationRead")
@Composable
fun RateScreen() {

    val message = rememberSaveable { mutableStateOf("") }
    var cardColors by remember { mutableStateOf(Color.Red) }
    var isErrorDisplay by remember { mutableStateOf(false) }
    val changeOrientation = remember { mutableStateOf(false) }
    cardColors = ClassJournalTheme.colors.secondaryBackground

    val configuration = remember {  LocalConfiguration }
    val context = LocalContext.current
    val isLandscape = rememberSaveable { mutableStateOf(context.resources.configuration.orientation) }
        Column(
        modifier = Modifier
            .fillMaxSize()
            .background(ClassJournalTheme.colors.primaryBackground),
        verticalArrangement = Arrangement.Center
    ) {
    val keyboardController = LocalSoftwareKeyboardController.current
            val textStatet = rememberTextFieldState()
    val kbshow =rememberSaveable { mutableStateOf(false) }
    var indexList  by rememberSaveable { mutableStateOf(-1) }
    val focusRequester = remember { FocusRequester() }
    val focusRequester1 = remember { FocusRequester() }
    var saveElment = remember { mutableStateOf(focusRequester1)}

            LaunchedEffect(configuration) {


                if (isLandscape.value != context.resources.configuration.orientation){
                    isLandscape.value = context.resources.configuration.orientation
                    changeOrientation.value = true
                    saveElment.value.requestFocus()
                 //  if (indexList > -1){
                   //viewlist[indexList].requestFocus()
               //     keyboardController?.show()
                   //}
                }
            }

            TextField(
                value = message.value,
                label = "t1",
                supportingText = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    //.focusProperties()
                    .focusRequester(focusRequester)
                    .onFocusChanged{s->
                          if (s.isFocused){
                              saveElment.value = focusRequester

                            indexList = 0
                        }

                    }
                ,
                onValueChange = { message.value = it },

            )


                TextField(
                    value = message.value,
                    label = "t2",
                    supportingText = "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .focusRequester(focusRequester1)
                        .focusTarget()
                        .onFocusChanged{
                            if (it.isFocused){
                                saveElment.value = focusRequester1
                   //             list[1] = focusRequester1

                                indexList= 1

                            }
                        }
                            ,
                    onValueChange = { message.value = it },
                )
                Button(modifier = Modifier.padding(top = 50.dp), onClick = {
                  //  focusManager.clearFocus(true)
                  //  focusManager.moveFocus(focusRequester)

                    focusRequester.requestFocus()
                    keyboardController?.show()
                }) {
                    Text("Получить фокус")
                }

    //        }
        }

    }
//}



