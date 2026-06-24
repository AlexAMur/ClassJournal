package com.catshome.classJournal.communs.mainMenu


import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.CardColors
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.backup.FilePickerScreen
import com.catshome.classJournal.backup.backUp
import com.catshome.classJournal.communs.DialogScreen
import com.catshome.classJournal.communs.SnackBarAction
import com.catshome.classJournal.context
import com.catshome.classJournal.di.AppModule
import com.catshome.classJournal.di.AppModule_ProvideAppDataBaseFactory.provideAppDataBase
import com.catshome.classJournal.resource.R
import com.catshome.classJournal.screens.ItemScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

@Composable
fun MainModalNavDrawer(
    padding: PaddingValues,
    navController: NavController,
    content: @Composable () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val sbHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var showSnackBar by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    val backupDir =
        File(context.getExternalFilesDir(null), "ClassJournalBackup") // Путь к бэкапу
    var showPickerFile by remember { mutableStateOf(false) }
    if (showPickerFile) {
        navController.navigate(ItemScreen.FilePickScreen.name)
    }
//    LaunchedEffect(showSnackBar==true) {
//        if (showSnackBar)
//        SnackBarAction(
//            message = "Sss",
//            actionLabel = "",
//            snackBarState = sbHostState,
//            withDismissAction = false,
//            onDismissed = {
//                showSnackBar = false
//
//            },
//            onActionPerformed = {}
//        )
//    }

    if (showDialog) {

        DialogScreen(
            title = context.getString(R.string.backup_db_title),
            text = "${context.getString(R.string.backup_db_message)} ${backupDir}",
            onConfirm = {
                showSnackBar = backUp(
                    dataBase = provideAppDataBase(AppModule(), context),
                    context = context,
                    backupDir = backupDir
                )
                CoroutineScope(scope.coroutineContext).launch {
                    drawerState.close()
                }
                showDialog = false
            },
            onDismiss = {
                showDialog = false
                CoroutineScope(scope.coroutineContext).launch {
                    drawerState.close()
                }
            },
            confimText = context.getString(R.string.save_button),
            dissmissText = context.getString(R.string.bottom_cancel),
           )
    }
    ModalNavigationDrawer(
        drawerContent = {
            drawerContent(
                paidding = padding,
                colorsCard = CardColors(
                    containerColor = ClassJournalTheme.colors.primaryBackground,
                    contentColor = ClassJournalTheme.colors.primaryText,
                    disabledContainerColor = ClassJournalTheme.colors.primaryBackground,
                    disabledContentColor = ClassJournalTheme.colors.primaryText
                ),
                onClickBackup = {

                    showDialog = true
                },
                onClickRestore = {
                    CoroutineScope(scope.coroutineContext).launch {
                        drawerState.close()
                    }
                    showPickerFile = true
                }
            )
        },
        modifier = Modifier,
        drawerState = drawerState,
        scrimColor = DrawerDefaults.scrimColor, //ClassJournalTheme.colors.secondaryBackground,
        content = content
    )
}