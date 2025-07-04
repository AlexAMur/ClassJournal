package com.catshome.classJournal


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.automirrored.sharp.List
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.sharp.AccountBox
import androidx.compose.material.icons.sharp.Face
import androidx.compose.material.icons.sharp.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.catshome.classJournal.screens.ItemBottomBar
import com.catshome.classJournal.screens.ItemScreen
import com.catshome.classJournal.screens.group.GroupScreen
import com.catshome.classJournal.theme.MainTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
             val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            var selectedItem by rememberSaveable { mutableIntStateOf(0) }
            val settingsEventBus = remember { SettingsEventBus() }
            val currentSettings by settingsEventBus.currentSettings.collectAsState()

            MainTheme(
                style = currentSettings.style,
                darkTheme = currentSettings.isDarkMode,
                corners = currentSettings.cornerStyle,
                textSize = currentSettings.textSize,
                paddingSize = currentSettings.paddingSize

            ) {
                CompositionLocalProvider(
                    localNavHost provides navController,
                    LocalSettingsEventBus provides settingsEventBus
                ) {


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


                    Surface(Modifier.background(ClassJournalTheme.colors.primaryBackground)) {
                        Scaffold(
                            Modifier.background(ClassJournalTheme.colors.primaryBackground),
                            bottomBar = {
                                NavigationBar(containerColor = ClassJournalTheme.colors.tintColor) {
                                    bottomItems.forEachIndexed { index, screen ->
                                        NavigationBarItem(

                                            label = { Text(screen.label, style = ClassJournalTheme.typography.body) },
                                            selected =currentDestination?.hierarchy?.any{it.route== screen.route} ==true ,
                                            onClick = {
                                               // selectedItem = index
                                                navController.navigate(screen.route)
                                            },
                                            icon = {
                                                Icon(
                                                    if (currentDestination?.hierarchy?.any{it.route== screen.route} == true)
                                                            screen.icon else screen.iconUnselect , "",
                                                    tint = if (currentDestination?.hierarchy?.any{it.route== screen.route} == true)
                                                         ClassJournalTheme.colors.primaryBackground

                                                    else
                                                       ClassJournalTheme.colors.secondaryBackground
                                                )

                                            }
                                        )
                                    }
                                }

                            }) { innerPadding ->
                            currentSettings.innerPadding = innerPadding
                            classJournalApp(
                                this,
                                navController,
                                currentSettings
                            )

                        }
                    }
                }
            }
        }

    }
}
