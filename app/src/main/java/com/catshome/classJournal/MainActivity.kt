package com.catshome.classJournal


import android.content.Context
import android.os.Bundle
import android.util.Log
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.datastore.dataStore
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.catshome.classJournal.communs.mainMenu.MainModalNavDrawer
import com.catshome.classJournal.proto.AppSettings
import com.catshome.classJournal.proto.AppSettingsSerializer
import com.catshome.classJournal.proto.ColorMode
import com.catshome.classJournal.screens.ItemBottomBar
import com.catshome.classJournal.screens.ItemScreen
import com.catshome.classJournal.screens.PayList.PayListScreen
import com.catshome.classJournal.theme.MainTheme
import dagger.hilt.android.AndroidEntryPoint
import com.catshome.classJournal.resource.R
import com.catshome.classJournal.screens.Visit.ListVisit.visitListScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.io.readBytes

lateinit var context: Context
val Context.dataStore by dataStore("app-settings.json", AppSettingsSerializer)
var appSetting: AppSettings? = null

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private suspend fun setAppSettings(settings: AppSettings) {
        dataStore.updateData {
            it.copy(colorMode = settings.colorMode, style = settings.style)
        }
    }

    private fun getSettings(settings: AppSettings, eventBus: SettingsEventBus) {
        if (settings.colorMode.name == ColorMode.Dark.name)
            eventBus.updateDarkMode(true)
        else
            eventBus.updateDarkMode(false)
        eventBus.updateStyle(settings.style)
    }

    override fun onDestroy() {
        super.onDestroy()
        appSetting?.let {
            CoroutineScope(Dispatchers.IO).launch {
                setAppSettings(it)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = application.applicationContext
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            appSetting = dataStore.data.collectAsState(null).value
            appSetting?.let { appSettings ->
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                var selectedItem by rememberSaveable { mutableIntStateOf(0) }
                val settingsEventBus = remember { SettingsEventBus() }
                val currentSettings by settingsEventBus.currentSettings.collectAsState()
                LaunchedEffect(Unit) {
                    if (navBackStackEntry?.destination?.route.isNullOrEmpty())
                        navBackStackEntry?.destination?.route = ItemScreen.PayListScreen.name
                }
                LaunchedEffect(appSetting) {
                    getSettings(appSettings, settingsEventBus)
                }
                DisposableEffect(Unit) {
                    onDispose {
                        CoroutineScope(Dispatchers.IO).launch {
                            CoroutineScope(Dispatchers.IO).launch {
                                context.dataStore.updateData {
                                    it.copy(colorMode = if (currentSettings.isDarkMode) ColorMode.Dark else ColorMode.Light)
                                }
                            }
                            setAppSettings(
                                AppSettings(
                                    colorMode = if (currentSettings.isDarkMode)
                                        ColorMode.Dark
                                    else
                                        ColorMode.Light,
                                    style = currentSettings.style
                                )
                            )
                        }
                    }
                }
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
                                ItemScreen.VisitListScreen.name,
                                Icons.Sharp.DateRange,
                                Icons.Outlined.DateRange,
                                visitListScreen(navController)
                            ), ItemBottomBar(
                                stringResource(R.string.item_bottom_client),
                                ItemScreen.MainScreen.name,
                                Icons.Sharp.Face,
                                Icons.Outlined.Face,
                                RateScreen()
                            ), ItemBottomBar(
                                stringResource(R.string.item_bottom_scheduler),
                                ItemScreen.SchedulerScreen.name,
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
                                MainModalNavDrawer(innerPadding, navController) {
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
    }
}
