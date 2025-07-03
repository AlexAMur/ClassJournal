package com.catshome.classJournal

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.catshome.classJournal.screens.ItemScreen

val child = listOf("Sahsa", "Masha") //,"Varaiy", "Zina")

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ResourceAsColor", "SuspiciousIndentation")
@Composable
fun MainScreen(
    navController: NavController,
    //          currentSettings: SettingsBundle
) {
 //   val settingsEventBus = LocalSettingsEventBus.current
//    val colorSettings = remember { baseLightPalette }
//     val currentSettings by settingsEventBus.currentSettings.collectAsState()
//    MainTheme(
//        style = currentSettings.style,
//        darkTheme = currentSettings.isDarkMode,
//        corners = currentSettings.cornerStyle,
//        textSize = currentSettings.textSize,
//        paddingSize = currentSettings.paddingSize
//    ) {
//        CompositionLocalProvider(
//            LocalSettingsEventBus provides settingsEventBus
//        ) {
    Surface(Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(color = ClassJournalTheme.colors.primaryBackground)
    ) {
        Scaffold(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = ClassJournalTheme.colors.primaryBackground)
        ) {
            Column(Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(color = ClassJournalTheme.colors.primaryBackground))
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
                FloatingActionButton(
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .align(alignment = Alignment.End),
                    onClick = { navController.navigate(ItemScreen.NewChildScreen.name) }) {
                    Icon(
                        Icons.Sharp.Add,
                        null
                    )//, modifier = Modifier , ClassJournalTheme.colors.primaryBackground)
                }


            }
        }
    }
}
//    }
//}