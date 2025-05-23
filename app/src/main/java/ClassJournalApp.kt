package com.catshome.ClassJournal

import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.catshome.classjornal.GroupScreen
import com.catshome.classjornal.MainScreen
import com.catshome.classjornal.NewChildScreen
import com.catshome.classjornal.NewGroupScreen
import com.catshome.classjornal.NewRateScreen
import com.catshome.classjornal.NewVisitScreen
import com.catshome.classjornal.RateScreen
import com.catshome.classjornal.ReportScreen
import com.catshome.classjornal.Screens.ItemBottomBar
import com.catshome.classjornal.Screens.ItemScreen
import com.catshome.classjornal.ui.theme.ClassJournalTheme

internal val localNavHost = staticCompositionLocalOf<NavHostController> { error("No default implementation") }


@Composable
fun classJournalApp(
    navController: NavHostController = rememberNavController()
){
 val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen =backStackEntry?.destination?.route ?: ItemScreen.MainScreen.name
    var selectedItem by rememberSaveable {  mutableIntStateOf(0) }
    val bottomItems = listOf(
        ItemBottomBar("Главный", ItemScreen.MainScreen.name,  Icons.Sharp.Face,
            Icons.Outlined.Face, MainScreen(navController)),
        ItemBottomBar("Группы", ItemScreen.GroupScreen.name, Icons.Sharp.AccountBox, Icons.Outlined.AccountBox, GroupScreen(navController)),
        ItemBottomBar("Тарифы", ItemScreen.RateScreen.name, Icons.Sharp.ShoppingCart, Icons.Outlined.ShoppingCart, RateScreen()),
        ItemBottomBar("Отчеты", ItemScreen.ReportScreen.name,  Icons.AutoMirrored.Sharp.List, Icons.AutoMirrored.Outlined.List, ReportScreen())
    )

    ClassJournalTheme {
        Scaffold(
        bottomBar = {
            NavigationBar {
                bottomItems.forEachIndexed {index, screen ->
                    NavigationBarItem(
                        label = { Text(screen.label) },
                        selected = selectedItem==index,
                        onClick = {
                            selectedItem=index
                            navController.navigate(screen.route)
                        },
                        icon = {
                            if(selectedItem==index)
                                Icon(screen.icon, "")
                            else
                                Icon(screen.iconUnselect, "")
                        }
                    )
                }
            }

        }) { innerPadding ->
        Text("", Modifier.padding(innerPadding))

    }
    }//classJournalTheme


    CompositionLocalProvider(
        localNavHost provides  navController
    ) {
        NavHost(navController, startDestination = ItemScreen.MainScreen.name){
            composable(route = ItemScreen.MainScreen.name){ MainScreen(navController) }
            composable(route = ItemScreen.GroupScreen.name){ GroupScreen(navController) }
            composable(route = ItemScreen.RateScreen.name){ RateScreen() }
            composable(route = ItemScreen.ReportScreen.name){ ReportScreen() }
            composable(route = ItemScreen.NewGroupScreen.name){ NewGroupScreen() }
            composable(route = ItemScreen.NewRateScreen.name){ NewRateScreen() }
            composable(route = ItemScreen.NewChildScreen.name){ NewChildScreen() }
            composable(route = ItemScreen.NewVisitScreen.name){ NewVisitScreen() }
        }


    }
}






//
//@Composable
//fun test(){
//    Scaffold(
//        bottomBar = {
//            NavigationBar {
//                bottomItems.forEachIndexed {index, screen ->
//                    NavigationBarItem(
//                        label = { Text(screen.label) },
//                        selected = selectedItem==index,
//                        onClick = {
//                            selectedItem=index
//                            navController.navigate(screen.route)
//                        },
//                        icon = {
//                            if(selectedItem==index)
//                                Icon(screen.icon, "")
//                            else
//                                Icon(screen.iconUnselect, "")
//                        }
//                    )
//                }
//            }
//
//        }) { innerPadding ->
//        Text("", Modifier.padding(innerPadding))
//
//    }
//}