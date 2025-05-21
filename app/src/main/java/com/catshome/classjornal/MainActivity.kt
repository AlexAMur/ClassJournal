package com.catshome.classjornal



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.catshome.classjornal.Screens.ItemBottomBar
import com.catshome.classjornal.Screens.ItemScreen
import com.catshome.classjornal.ui.theme.ClassJournalTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appDatabase = getDatabaseBuilder(applicationContext).build()
        enableEdgeToEdge()
        setContent {
            val navControler = rememberNavController()
            var selectedItem by rememberSaveable {  mutableIntStateOf(0) }
            val bottomItems = listOf(
                ItemBottomBar("Главный", ItemScreen.MainScreen.name,  Icons.Sharp.Face,Icons.Outlined.Face, MainScreen(navControler)),
                ItemBottomBar("Группы", ItemScreen.GroupScreen.name, Icons.Sharp.AccountBox, Icons.Outlined.AccountBox, GroupScreen(navControler)),
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
                                            navControler.navigate(screen.route)
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
                        NavHost(navControler, startDestination = ItemScreen.MainScreen.name){
                            composable(route = ItemScreen.MainScreen.name){ MainScreen(navControler) }
                            composable(route = ItemScreen.GroupScreen.name){ GroupScreen(navControler)}
                            composable(route = ItemScreen.RateScreen.name){ RateScreen() }
                            composable(route = ItemScreen.ReportScreen.name){ ReportScreen() }
                            composable(route = ItemScreen.NewGroupScreen.name){ NewGroupScreen() }
                            composable(route = ItemScreen.NewRateScreen.name){ NewRateScreen() }
                            composable(route = ItemScreen.NewChildScreen.name){ NewChildScreen() }
                            composable(route = ItemScreen.NewVisitScreen.name){ NewVisitScreen() }
                        }


                }

            }
        }
    }
}
