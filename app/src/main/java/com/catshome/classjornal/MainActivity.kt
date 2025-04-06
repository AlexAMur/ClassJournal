package com.catshome.classjornal


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.sharp.List
import androidx.compose.material.icons.sharp.AccountBox
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material.icons.sharp.Face
import androidx.compose.material.icons.sharp.Home
import androidx.compose.material.icons.sharp.List
import androidx.compose.material.icons.sharp.ShoppingCart
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.catshome.classjornal.Screens.ItemBottomBar
import com.catshome.classjornal.ui.theme.ClassJournalTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // val navControler = rememberNavController()
        setContent {
            var selectedItem by rememberSaveable {  mutableIntStateOf(0) }
            val bottomItems = listOf(
                ItemBottomBar("Главный", Icons.Sharp.Face, MainScreen()),
                ItemBottomBar("Группы", Icons.Sharp.AccountBox, GroupScreen()),
                ItemBottomBar("Тарифы", Icons.Sharp.ShoppingCart, RateScreen()),
                ItemBottomBar("Отчеты", Icons.AutoMirrored.Sharp.List, ReportScreen())
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
                                                selectedItem = index

                                              },
                                    icon = { Icon(screen.icon, "") }
                                )
                            }
                        }

                    }) { innerPadding ->
                    Column {
                        Text("Screen", Modifier.padding(innerPadding))
                        FloatingActionButton(onClick = {}) {
                            Icon(Icons.Sharp.Add, "")
                        }
                    }
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ClassJournalTheme {
        Greeting("Android")
    }
}