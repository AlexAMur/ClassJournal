package com.catshome.classJournal

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.catshome.classJournal.communs.SearchField
import com.catshome.classJournal.screens.PayList.ItemChildInSearch
import com.catshome.classJournal.screens.Visit.NewVisitViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import com.catshome.classJournal.navigate.VisitDetails
import com.catshome.classJournal.resource.R
import com.catshome.classJournal.screens.Visit.NewVisitAction
import com.catshome.classJournal.screens.Visit.NewVisitContent
import com.catshome.classJournal.screens.Visit.NewVisitEvent
import com.catshome.classJournal.screens.Visit.NewVisitState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NewVisitScreen(
    viewModel: NewVisitViewModel = viewModel(),
    details: VisitDetails? = null
) {
    val viewAction by viewModel.viewActions().collectAsState(null)
    Surface(
        Modifier
            .fillMaxSize(),
        color = ClassJournalTheme.colors.primaryBackground
    ) {
        Card(
            Modifier
                .fillMaxWidth()
                .systemBarsPadding(),
            colors = CardDefaults.cardColors(
                ClassJournalTheme.colors.secondaryBackground
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ClassJournalTheme.colors.primaryBackground)
            )
            {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(onClick = onCancelClick) {
                        Icon(
                            Icons.Default.Close, "", tint = ClassJournalTheme.colors.tintColor
                        )
                    }
                    Text(
                        stringResource(R.string.new_visit_dialog_headline),
                        color = ClassJournalTheme.colors.primaryText,
                        style = ClassJournalTheme.typography.caption
                    )
                    TextButton(onClick = onSaveClick) {
                        Text(
                            stringResource(R.string.save_button),
                            color = ClassJournalTheme.colors.tintColor
                        )
                    }
                }
            NewVisitContent(viewModel)
        }
    }
    when (viewAction) {
        NewVisitAction.CloseScreen -> TODO("TODO Close window implemented create")
        null -> {}
    }
}
