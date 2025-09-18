package com.catshome.classJournal

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.catshome.classJournal.screens.ItemScreen
import com.catshome.classJournal.screens.PayList.PlayListScreen
import com.catshome.classJournal.screens.bottomBar

val child = listOf("Sahsa", "Masha") //,"Varaiy", "Zina")

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ResourceAsColor", "SuspiciousIndentation")
@Composable
fun MainScreen(
    navController: NavController,
) {
    val bottomPadding = LocalSettingsEventBus.current.currentSettings.collectAsState()
        .value.innerPadding.calculateBottomPadding()


    Surface(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = ClassJournalTheme.colors.primaryBackground)
    ) {
        Scaffold(
            Modifier
                .fillMaxSize()
                .background(color = ClassJournalTheme.colors.primaryBackground),
            bottomBar = {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    FloatingActionButton(
                        modifier = Modifier
                            .padding(bottom = bottomPadding + 16.dp, end = 16.dp),
                        onClick = {
                            navController.navigate(ItemScreen.PayListScreen.name)
                            //TODO TMP
                            //navController.navigate(ItemScreen.NewChildScreen.name)
                        }) {
                        Icon(Icons.Sharp.Add, null)
                    }
                }
            }
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(color = ClassJournalTheme.colors.primaryBackground)
            )
            {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(
                        items = child,
                        itemContent = {
                            Text(
                                it, modifier = Modifier
                                    .padding(24.dp)
                                    .clickable {

                                        navController.navigate(ItemScreen.NewChildScreen.name)
                                    })
                        })
                }
            }
        }
    }
}
