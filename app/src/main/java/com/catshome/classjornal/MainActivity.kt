package com.catshome.classjornal


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.automirrored.sharp.List
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.sharp.AccountBox
import androidx.compose.material.icons.sharp.Face
import androidx.compose.material.icons.sharp.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.painter.Painter
import androidx.navigation.compose.rememberNavController
import com.catshome.ClassJournal.classJournalApp
import com.catshome.ClassJournal.com.catshome.classjornal.ClassJournalTheme
import com.catshome.ClassJournal.com.catshome.classjornal.LocalSettingsEventBus
import com.catshome.ClassJournal.com.catshome.classjornal.SettingsBundle
import com.catshome.ClassJournal.com.catshome.classjornal.SettingsEventBus
import com.catshome.ClassJournal.localNavHost
import com.catshome.classjornal.Screens.ItemBottomBar
import com.catshome.classjornal.Screens.ItemScreen
import com.catshome.classjornal.ui.theme.MainTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    //@OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()
            var selectedItem by rememberSaveable { mutableIntStateOf(0) }
            val settingsEventBus = remember { SettingsEventBus() }
           val currentSettings by settingsEventBus.currentSettings.collectAsState()
            val bottomItems = listOf(
                ItemBottomBar(
                    "Главный",
                    ItemScreen.MainScreen.name,
                    Icons.Sharp.Face,
                    Icons.Outlined.Face,
                    MainScreen(navController)//,currentSettings)
                ), ItemBottomBar(
                    "Группы",
                    ItemScreen.GroupScreen.name,
                    Icons.Sharp.AccountBox,
                    Icons.Outlined.AccountBox,
                    GroupScreen(navController)
                ), ItemBottomBar(
                    "Тарифы",
                    ItemScreen.RateScreen.name,
                    Icons.Sharp.ShoppingCart,
                    Icons.Outlined.ShoppingCart,
                    RateScreen()
                ), ItemBottomBar(
                    "Отчеты",
                    ItemScreen.ReportScreen.name,
                    Icons.AutoMirrored.Sharp.List,
                    Icons.AutoMirrored.Outlined.List,
                    ReportScreen()
                )
            )
            CompositionLocalProvider(
                localNavHost provides navController,
                LocalSettingsEventBus provides settingsEventBus,
                //  LocalClassJournalColors provides colorSettings
            ) {

            MainTheme(
                style = currentSettings.style,
                darkTheme = currentSettings.isDarkMode,
                corners = currentSettings.cornerStyle,
                textSize = currentSettings.textSize,
                paddingSize = currentSettings.paddingSize

            ) {
                     Scaffold(
                        bottomBar = {
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

                        classJournalApp(this,
                            navController,
                           currentSettings
                        )    //Text(, Modifier.padding(innerPadding))

                    }
                }
            }
        }

    }
}
