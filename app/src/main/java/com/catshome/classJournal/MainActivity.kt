package com.catshome.classJournal


import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.List
import androidx.compose.material.icons.automirrored.sharp.List
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material.icons.sharp.DateRange
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
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.catshome.classJournal.screens.ItemBottomBar
import com.catshome.classJournal.screens.ItemScreen
import com.catshome.classJournal.screens.PayList.PayListScreen
import com.catshome.classJournal.screens.group.GroupScreen
import com.catshome.classJournal.theme.MainTheme
import dagger.hilt.android.AndroidEntryPoint

lateinit var context: Context

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    var isShowExitDialog = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //LocalActivity.provides(this)
        context = application.applicationContext

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
//            var isShowDialog by rememberSaveable { mutableStateOf(false) }
//            var backHandler by rememberSaveable { mutableStateOf(true) }
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            var selectedItem by rememberSaveable { mutableIntStateOf(0) }
            val settingsEventBus = remember { SettingsEventBus() }
            val currentSettings by settingsEventBus.currentSettings.collectAsState()
//            BackHandler(backHandler) {
//                isShowDialog = true
//                Log.e("CLJR", "Back in onBack $backHandler")
//                backHandler = false
//            }
//            if (isShowDialog) {
//
//                AlertDialog(
//                    onDismissRequest = {
//                        isShowDialog = false
//                        backHandler = true
//                    },
//                    title = { Text(text = "Выход из приложения") },
//                    text = { Text("Вы уверены, что хотите выйти?") },
//                    confirmButton = {
//                        Button(
//                    //        shape = ClassJournalTheme.shapes.cornersStyle,
////                            colors = ButtonDefaults.buttonColors(
////                                contentColor = ClassJournalTheme.colors.primaryText,
////                                containerColor = ClassJournalTheme.colors.tintColor
////                            ),
//                            onClick = {
//                                isShowDialog = false
//                                backHandler = false
//
//                               // (context as? Activity)?.finish() // Завершаем Activity
//
//                            }) {
//                            Text(stringResource(R.string.bottom_yes))
//                        }
//                    },
//                    dismissButton = {
//                        Button(
//                          //  shape = ClassJournalTheme.shapes.cornersStyle,
////                            colors = ButtonDefaults.buttonColors(
////                                contentColor = ClassJournalTheme.colors.primaryText,
////                                containerColor = ClassJournalTheme.colors.tintColor
////                            ),
//                            onClick = {
//                                backHandler = true
//                                isShowDialog = false
//                            }
//                        ) {
//                            Text(stringResource(R.string.bottom_no))
//                        }
//                    })
//
//            }
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
                            stringResource(R.string.item_bottom_pay),
                            ItemScreen.PayListScreen.name,
                            Icons.Sharp.ShoppingCart,
                            Icons.Outlined.ShoppingCart,
                            PayListScreen(navController = navController)//,currentSettings)
                        ), ItemBottomBar(
                            stringResource(R.string.item_bottom_visit),
                            ItemScreen.GroupScreen.name,
                            Icons.Sharp.DateRange,
                            Icons.Outlined.DateRange,
                            GroupScreen(navController)
                        ), ItemBottomBar(
                            stringResource(R.string.item_bottom_client),
                            ItemScreen.MainScreen.name,
                            Icons.Sharp.Face,
                            Icons.Outlined.Face,
                            RateScreen()
                        ), ItemBottomBar(
                            stringResource(R.string.item_bottom_report),
                            ItemScreen.ReportScreen.name,
                            Icons.AutoMirrored.Sharp.List,
                            Icons.AutoMirrored.Outlined.List,
                            ReportScreen()
                        )
                    )
                    Surface(
                        Modifier
                            .background(ClassJournalTheme.colors.primaryBackground)
                            .fillMaxSize()
                    ) {
                        Scaffold(
                            Modifier.background(ClassJournalTheme.colors.primaryBackground),
                            bottomBar = {
                                NavigationBar(containerColor = ClassJournalTheme.colors.tintColor) {
                                    bottomItems.forEachIndexed { index, screen ->
                                        NavigationBarItem(

                                            label = {
                                                Text(
                                                    screen.label,
                                                    style = ClassJournalTheme.typography.body
                                                )
                                            },
                                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                            onClick = {
                                                // selectedItem = index
                                                if (currentDestination?.route != screen.route) navController.navigate(
                                                    screen.route
                                                )
                                            },
                                            icon = {
                                                Icon(if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) screen.icon else screen.iconUnselect,
                                                    "",
                                                    tint = if (currentDestination?.hierarchy?.any { it.route == screen.route } == true) ClassJournalTheme.colors.primaryBackground
                                                    else ClassJournalTheme.colors.secondaryBackground)

                                            })
                                    }
                                }
                            }) { innerPadding ->
                            currentSettings.innerPadding = innerPadding
                            classJournalApp(
                                this, navController, currentSettings
                            )
                        }
                    }
                }
            }
        }

    }
}
