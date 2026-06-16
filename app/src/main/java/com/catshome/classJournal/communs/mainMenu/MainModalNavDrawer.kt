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
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.backup.backUp
import com.catshome.classJournal.communs.DialogScreen
import com.catshome.classJournal.communs.SnackBarAction
import com.catshome.classJournal.context
import com.catshome.classJournal.di.AppModule
import com.catshome.classJournal.di.AppModule_ProvideAppDataBaseFactory.provideAppDataBase
import com.catshome.classJournal.resource.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun MainModalNavDrawer(
    padding: PaddingValues,
    content: @Composable () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val sbHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var showSnackBar by remember { mutableStateOf(false) }


        if (showSnackBar) {
            DialogScreen(
                "Tet    ",
                text = " Save",
                onConfirm = {
                    showSnackBar= false
                },
                onDismiss = {},
                confimText = "Ok",
                dissmissText = "",
              //  textContentColor = TODO()
            )
        }
//            drawerState.close()
//            Log.e("CLJR", "show Snake ${showSnackBar}")
//            SnackBarAction(
//                message = context.getString(R.string.save_successful),
//                actionLabel = context.getString(R.string.ok),
//                snackBarState = sbHostState,
//                withDismissAction = false,
//                onDismissed = {
//                },
//                onActionPerformed = {
//                    Log.e("CLJR", "onAction")
//                    showSnackBar = false
//                }
//            )
//        }
//
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
                     CoroutineScope(scope.coroutineContext).launch {
                        drawerState.close()
                    }

                    showSnackBar = backUp(
                            dataBase = provideAppDataBase(AppModule(), context),
                            context = context
                        )

                },
                onClickRestore = {}
            )
        },
        modifier = Modifier,
        drawerState = drawerState,
        scrimColor = DrawerDefaults.scrimColor, //ClassJournalTheme.colors.secondaryBackground,
        content = content
    )
}