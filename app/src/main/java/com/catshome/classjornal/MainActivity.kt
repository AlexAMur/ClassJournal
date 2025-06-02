package com.catshome.classjornal



import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.navigation.compose.rememberNavController
import com.catshome.ClassJournal.classJournalApp
import com.catshome.classjornal.Screens.ItemBottomBar
import com.catshome.classjornal.Screens.ItemScreen
import com.catshome.classjornal.Screens.viewModels.GroupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
//        val appDatabase = getDatabaseBuilder(applicationContext).build()
//        val dao =appDatabase.groupsDAO()
//        lifecycleScope.launch {
//            dao.insert(GroupEntity(2, name = "test1", isDelete = false))
//        }
            enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()
            var selectedItem by rememberSaveable {  mutableIntStateOf(0) }
            val bottomItems = listOf(
                ItemBottomBar("Главный", ItemScreen.MainScreen.name,  Icons.Sharp.Face,
                    Icons.Outlined.Face, MainScreen( navController)),
                ItemBottomBar("Группы", ItemScreen.GroupScreen.name, Icons.Sharp.AccountBox,
                    Icons.Outlined.AccountBox, GroupScreen(navController)),
                ItemBottomBar("Тарифы", ItemScreen.RateScreen.name, Icons.Sharp.ShoppingCart,
                    Icons.Outlined.ShoppingCart, RateScreen()),
                ItemBottomBar("Отчеты", ItemScreen.ReportScreen.name,
                    Icons.AutoMirrored.Sharp.List, Icons.AutoMirrored.Outlined.List, ReportScreen())
            )


            Scaffold(
                bottomBar = {
                    NavigationBar {
                        bottomItems.forEachIndexed {index, screen ->
                            NavigationBarItem(
                                label = { Text(screen.label) },
                                selected = selectedItem==index,
                                onClick = {
                                    selectedItem = index
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

                }) {// innerPadding ->
            classJournalApp(navController)    //Text(, Modifier.padding(innerPadding))

            }

                }

            }
        }
