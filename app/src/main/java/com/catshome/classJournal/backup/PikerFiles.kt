package com.catshome.classJournal.backup

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.communs.DialogScreen
import com.catshome.classJournal.communs.SnackBarAction
import com.catshome.classJournal.communs.restartApp
import com.catshome.classJournal.di.AppModule
import com.catshome.classJournal.di.AppModule_ProvideAppDataBaseFactory.provideAppDataBase
import com.catshome.classJournal.resource.R

@Composable
fun FilePickerScreen(context: Context) {

    var selectedFileUri by remember { mutableStateOf<List<Uri?>>(emptyList()) }
    var showAlertDialog by remember { mutableStateOf(false) }
    var isCanShowSnackBar by remember { mutableStateOf(false) }
    val sbHostState = remember { SnackbarHostState() }

    // Создаем Launcher для вызова системного диалога
    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenMultipleDocuments()//OpenDocument()
    ) { uri: List<Uri?> ->
        // Этот блок срабатывает, когда пользователь выбрал файл
        selectedFileUri = uri
    }
    Column(
        modifier = Modifier
            .background(ClassJournalTheme.colors.primaryBackground)
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            colors = ButtonColors(
                containerColor = ClassJournalTheme.colors.tintColor,
                contentColor = ClassJournalTheme.colors.primaryText,
                disabledContainerColor = ClassJournalTheme.colors.disableColor,
                disabledContentColor = ClassJournalTheme.colors.primaryText
            ),
            onClick = {
                // Запуск диалога. Указываем массив MIME-типов, например "image/*" или "application/pdf"

                showAlertDialog = true

            }) {
            Text(
                color = ClassJournalTheme.colors.primaryText,
                text = "Выбрать файл базы данных <classJournal>"
            )
        }
        LaunchedEffect(isCanShowSnackBar) {
            if (isCanShowSnackBar)
                SnackBarAction(
                    message = context.getString(R.string.restore_db_snackbar),
                    actionLabel = "",
                    snackBarState = sbHostState,
                    withDismissAction = false,
                    onDismissed = {
                        isCanShowSnackBar = false
                        restartApp(context)
                    },
                    onActionPerformed = {
                        isCanShowSnackBar = false
                    }
                )
        }
        if (showAlertDialog) {
            DialogScreen(
                title = context.getString(R.string.restore_db_titele),
                text = context.getString(R.string.restore_db_message),
                onDismiss = { showAlertDialog = false },
                dissmissText = context.getString(R.string.bottom_no),
                onConfirm = {
                    showAlertDialog = false
                    filePickerLauncher.launch(arrayOf("application/*"))

                },
                confimText = context.getString(R.string.bottom_yes)
            )
        }
        LaunchedEffect(selectedFileUri) {

            if (selectedFileUri.size > 2){
                isCanShowSnackBar = restoreDB(
                    context = context,
                    dataBase = provideAppDataBase(
                        AppModule(), context
                    ),
                    fileList = selectedFileUri.map {
                        it?.let {
                            getFileFromUri(
                                context = context,
                                it
                            )
                        }
                    })
        }}

        Spacer(modifier = Modifier.height(16.dp))

    }
}
