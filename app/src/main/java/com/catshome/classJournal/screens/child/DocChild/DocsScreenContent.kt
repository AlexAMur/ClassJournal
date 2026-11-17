package com.catshome.classJournal.screens.child.DocChild

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.room.util.TableInfo
import androidx.savedstate.serialization.encodeToSavedState
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.communs.ActionIcon
import com.catshome.classJournal.communs.SwipeableItemWithActions
import com.catshome.classJournal.communs.TextField
import com.catshome.classJournal.domain.Child.DocsChild.DocChild
import com.catshome.classJournal.domain.communs.toLocalDateTimeRuString
import com.catshome.classJournal.resource.R
import com.catshome.classJournal.screens.child.ChildListEvent
import kotlinx.coroutines.sync.Mutex
import kotlinx.datetime.TimeZone

@Composable
fun childDosContent(
    listDocs: List<DocChild>,
    onChange: (Int, String) -> Unit,
    onClearNote: (Int) -> Unit
) {

    LazyColumn(
        Modifier
            .height(500.dp)
            .padding(16.dp)
            .border(
                1.dp,
                color = ClassJournalTheme.colors.primaryText,
                shape = ClassJournalTheme.shapes.cornersStyle
            )
            .fillMaxWidth(),
        contentPadding = PaddingValues(top = 8.dp),
        state = rememberLazyListState()
    )
    {
        itemsIndexed(listDocs) { index, item ->
            Surface(
                Modifier
                    .background(
                        color = ClassJournalTheme.colors.primaryBackground,
                        shape = ClassJournalTheme.shapes.cornersStyle
                    )
                    .fillMaxSize(),
                shape = ClassJournalTheme.shapes.cornersStyle,
                color = ClassJournalTheme.colors.primaryBackground,
                // contentColor = ClassJournalTheme.colors.primaryBackground
            ) {
                SwipeableItemWithActions(
                    isRevealed = false,
                    actions = {
                        ActionIcon(
                            onClick = {},
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(80.dp),
                            backgroundColor = ClassJournalTheme.colors.controlColor,
                            icon = Icons.Default.Share,

                            tint = ClassJournalTheme.colors.tintColor
                        )
                        ActionIcon(
                            onClick = {
//                            //Удаление ребенка из списка
//                            viewModel.obtainEvent(
//                                ChildListEvent.deleteClicked(
//                                    uidChild = item.child.childUid,
//                                    uidGroup = "",
//                                    key = key
//                                )
//                            )
////                                                    scope.launch {
//                            viewModel.obtainEvent(
//                                ChildListEvent.showSnackBar(
//                                    message = "${context.getString(R.string.message_cancel)} ${item.child.childName} ?",
//                                    actionLabel = context.getString(R.string.bottom_cancel),
//                                    onDismissed = {
//                                        viewModel.obtainEvent(
//                                            ChildListEvent.deleteChild(
//                                                uid = item.child.childUid,
//                                                key = key
//                                            )
//                                        )
//                                    },
//                                    onActionPerformed = {
//                                        viewModel.obtainEvent(ChildListEvent.undoDelete)
//                                    }
//
//                                ))
                            },
                            icon = Icons.Default.Delete,
                            modifier = Modifier
                                .background(
                                    color = ClassJournalTheme.colors.primaryBackground,
                                )
                                .fillMaxHeight()
                                .width(80.dp),

                            )

                    },
                    modifier = Modifier
                        .background(
                            ClassJournalTheme.colors.primaryBackground,
                        )
                        .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)

                        .fillMaxWidth(),

                    onExpanded = {},
                    onCollapsed = {}
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = ClassJournalTheme.colors.primaryBackground,
                            )
                            .fillMaxWidth()
                        // .padding(start = 16.dp, end = 16.dp, top = 16.dp),
                    ) {
                        Card(
                            shape = ClassJournalTheme.shapes.cornersStyle,
                            modifier = Modifier
                                .background(
                                    color = ClassJournalTheme.colors.primaryBackground,
                                )
                                .fillMaxSize(),
                            colors = CardDefaults.cardColors(ClassJournalTheme.colors.controlColor),
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp)
                                        .clickable {
                                            //viewModel.obtainEvent(NewChildEvent.SelectGroup(item.group?.uid.toString()))
                                        },
                                    horizontalArrangement = Arrangement.Start
                                ) {

                                    Icon(
                                        painter = if (item.mimeType?.contains("pdf") == true)
                                            painterResource(R.drawable.pdf)
                                        else
                                            painterResource(R.drawable.imagefile),
                                        modifier = Modifier.padding(start = 16.dp, end = 8.dp),
                                        contentDescription = "",
                                        tint = ClassJournalTheme.colors.primaryText
                                    )
                                    Text(
                                        item.fileName ?: "null",
                                        modifier = Modifier.padding(end = 8.dp),
                                        style = ClassJournalTheme.typography.body,
                                        color = ClassJournalTheme.colors.primaryText
                                    )
                                    Text(
                                        "Datе: ${item.addedAt.toLocalDateTimeRuString(TimeZone.currentSystemDefault())} ",
                                        modifier = Modifier.padding(end = 8.dp),
                                        style = ClassJournalTheme.typography.body,
                                        color = ClassJournalTheme.colors.primaryText
                                    )
                                }
                                TextField(
                                    value = item.noteFile.toString(),
                                    label = stringResource(R.string.note_file),
                                    //supportingText = stringResource(R.string.note_file),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            start = 16.dp,
                                            top = 4.dp,
                                            end = 16.dp
                                        ),
                                    onValueChange = { onChange(index, it) },
                                    trailingIcon = {
                                        if (!item.noteFile.isNullOrEmpty())
                                            Icon(
                                                Icons.Default.Clear,
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .clickable {
                                                        onClearNote(index)
                                                    },
                                                tint = ClassJournalTheme.colors.primaryText
                                            )
                                    },
                                    minLines = 1,
                                    singleLine = true,
                                    keyboardOptions = KeyboardOptions.Default,
                                    errorState = false,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
