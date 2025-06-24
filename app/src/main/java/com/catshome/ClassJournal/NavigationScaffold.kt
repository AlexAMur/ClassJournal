package com.catshome.ClassJournal

import android.annotation.SuppressLint
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.catshome.ClassJournal.Screens.ItemBottomBar

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun navigationScaffold(navController: NavHostController, bottomItems: List<ItemBottomBar>, context: Context, settingsBundle: SettingsBundle){

Scaffold(
bottomBar = {
    FloatingActionButton(
        modifier = Modifier
            .padding(end = 16.dp)
        //     .align(alignment = Alignment.End)
        ,

        onClick = {  }) {
        Icon(Icons.Sharp.Add, "")
    }
    var selectedItem by rememberSaveable { mutableIntStateOf(0) }
    NavigationBar(containerColor = ClassJournalTheme.colors.tintColor) {
        bottomItems.forEachIndexed { index, screen ->
            NavigationBarItem(

                label = { Text(screen.label) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(screen.route)
                },
                icon = {
                    if (selectedItem == index) Icon(
                        screen.icon, "",
                        tint = ClassJournalTheme.colors.primaryBackground
                    )
                    else Icon(
                        screen.iconUnselect,
                        "",
                        tint = ClassJournalTheme.colors.secondaryBackground
                    )
                }
            )
        }
    }

}) {// innerPadding ->

    classJournalApp(
        context as ComponentActivity,
        navController,
        settingsBundle
    )    //Text(, Modifier.padding(innerPadding))

}
}