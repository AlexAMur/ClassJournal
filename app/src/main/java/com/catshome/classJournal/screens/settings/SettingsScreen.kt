package com.catshome.classJournal.screens.settings

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.datastore.dataStore
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.catshome.classJournal.ClassJournalStyle
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.LocalSettingsEventBus
import com.catshome.classJournal.communs.AppHeader
import com.catshome.classJournal.context
import com.catshome.classJournal.dataStore
import com.catshome.classJournal.proto.AppSettings
import com.catshome.classJournal.proto.ColorMode
import com.catshome.classJournal.resource.R
import com.catshome.classJournal.theme.blueDarkPalette
import com.catshome.classJournal.theme.blueLightPalette
import com.catshome.classJournal.theme.greenDarkPalette
import com.catshome.classJournal.theme.greenLightPalette
import com.catshome.classJournal.theme.orangeDarkPalette
import com.catshome.classJournal.theme.orangeLightPalette
import com.catshome.classJournal.theme.purpleDarkPalette
import com.catshome.classJournal.theme.purpleLightPalette
import com.catshome.classJournal.theme.redDarkPalette
import com.catshome.classJournal.theme.redLightPalette
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun SettingsScreen(
    navController: NavController,
    viewModel: SettingsViewModel = viewModel()
) {
    val viewState by viewModel.viewState().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)

    SettingsView(
        viewState = viewState,
        eventHandler = viewModel::obtainEvent
    )

    when (viewAction) {
        SettingsAction.NavigateBack -> {
            navController.popBackStack()
            viewModel.clearAction()
        }

        null -> {}
    }
}

@Composable
private fun SettingsView(
    viewState: SettingsViewState,
    eventHandler: (SettingsEvent) -> Unit
) {
    val settingsEventBus = LocalSettingsEventBus.current
    val currentSettings by settingsEventBus.currentSettings.collectAsState()

    Surface(
        color = ClassJournalTheme.colors.primaryBackground,
    ) {
        Column(
            Modifier.fillMaxSize()
        ) {
            AppHeader(
                title = stringResource(R.string.imenu_settings),
                isBackButtonAvailable = true,
                backClicked = { eventHandler(SettingsEvent.BackClicked) }
            )

            Row(
                modifier = Modifier.padding(ClassJournalTheme.shapes.padding),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = stringResource(R.string.dark_mode),
                    color = ClassJournalTheme.colors.primaryText,
                    style = ClassJournalTheme.typography.body
                )

                Checkbox(
                    checked = currentSettings.isDarkMode,
                    onCheckedChange = {
                        settingsEventBus.updateDarkMode(!currentSettings.isDarkMode)
                        CoroutineScope(Dispatchers.IO).launch {
                            context.dataStore.updateData {
                                it.copy(colorMode = if (currentSettings.isDarkMode)
                                    ColorMode.Dark
                                else
                                    ColorMode.Light)
                            }
                        }
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = ClassJournalTheme.colors.tintColor,
                        uncheckedColor = ClassJournalTheme.colors.secondaryText
                    )
                )
            }

            Divider(
                modifier = Modifier.padding(start = ClassJournalTheme.shapes.padding),
                thickness = 0.5.dp,
                color = ClassJournalTheme.colors.secondaryText.copy(
                    alpha = 0.3f
                )
            )

            Row(
                modifier = Modifier
                    .padding(ClassJournalTheme.shapes.padding)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ColorCard(
                    color = if (currentSettings.isDarkMode) purpleDarkPalette.tintColor else purpleLightPalette.tintColor,
                    onClick = {
                        settingsEventBus.updateStyle(ClassJournalStyle.Purple)

                    })
                ColorCard(
                    color = if (currentSettings.isDarkMode) orangeDarkPalette.tintColor else orangeLightPalette.tintColor,
                    onClick = {
                        settingsEventBus.updateStyle(ClassJournalStyle.Orange)
                    })
                ColorCard(
                    color = if (currentSettings.isDarkMode) blueDarkPalette.tintColor else blueLightPalette.tintColor,
                    onClick = {
                        settingsEventBus.updateStyle(ClassJournalStyle.Blue)
                    })
            }

            Row(
                modifier = Modifier
                    .padding(ClassJournalTheme.shapes.padding)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ColorCard(
                    color = if (currentSettings.isDarkMode) redDarkPalette.tintColor else redLightPalette.tintColor,
                    onClick = {
                        settingsEventBus.updateStyle(ClassJournalStyle.Red)
                    })
                ColorCard(
                    color = if (currentSettings.isDarkMode) greenDarkPalette.tintColor else greenLightPalette.tintColor,
                    onClick = {
                        settingsEventBus.updateStyle(ClassJournalStyle.Green)
                    })
            }

//            HabitCardItem(
//                model = HabitCardItemModel(
//                    habitId = "",
//                    title = stringResource(R.string.save_button),
//                    isChecked = true,
//                )
//            )

//            Text(
//                modifier = Modifier.padding(16.dp).clickable {
//                    eventHandler(SettingsEvent.ClearAllQueries)
//                },
//                text = stringResource(R.string.settings_clear),
//                color = ClassJournalTheme.colors.errorColor,
//                style = ClassJournalTheme.typography.body
//            )
//

//            MenuItem(
//                model = MenuItemModel(
//                    title = stringResource(Res.string.title_font_size),
//                    currentIndex = when (currentSettings.textSize) {
//                        JetHabitSize.Small -> 0
//                        JetHabitSize.Medium -> 1
//                        JetHabitSize.Big -> 2
//                    },
//                    values = listOf(
//                        stringResource(R.string.title_font_size_small),
//                        stringResource(R.string.title_font_size_medium),
//                        stringResource(R.string.title_font_size_big)
//                    )
//                ),
//                onItemSelected = {
//                    settingsEventBus.updateTextSize(
//                        when (it) {
//                            0 -> JetHabitSize.Small
//                            1 -> JetHabitSize.Medium
//                            2 -> JetHabitSize.Big
//                            else -> throw NotImplementedError("No valid value for this $it")
//                        }
//                    )
//                }
//            )

//            MenuItem(
//                model = MenuItemModel(
//                    title = stringResource(Res.string.title_padding_size),
//                    currentIndex = when (currentSettings.paddingSize) {
//                        JetHabitSize.Small -> 0
//                        JetHabitSize.Medium -> 1
//                        JetHabitSize.Big -> 2
//                    },
//                    values = listOf(
//                        stringResource(Res.string.title_padding_small),
//                        stringResource(Res.string.title_padding_medium),
//                        stringResource(Res.string.title_padding_big)
//                    )
//                ),
//                onItemSelected = {
//                    settingsEventBus.updatePaddingSize(
//                        when (it) {
//                            0 -> JetHabitSize.Small
//                            1 -> JetHabitSize.Medium
//                            2 -> JetHabitSize.Big
//                            else -> throw NotImplementedError("No valid value for this $it")
//                        }
//                    )
//                }
//            )

//            MenuItem(
//                model = MenuItemModel(
//                    title = stringResource(Res.string.title_corners_style),
//                    currentIndex = when (currentSettings.cornerStyle) {
//                        JetHabitCorners.Rounded -> 0
//                        JetHabitCorners.Flat -> 1
//                    },
//                    values = listOf(
//                        stringResource(Res.string.title_corners_style_rounded),
//                        stringResource(Res.string.title_corners_style_flat)
//                    )
//                ),
//                onItemSelected = {
//                    settingsEventBus.updateCornerStyle(
//                        when (it) {
//                            0 -> JetHabitCorners.Rounded
//                            1 -> JetHabitCorners.Flat
//                            else -> throw NotImplementedError("No valid value for this $it")
//                        }
//                    )
//                }
//            )

        }
    }
}


// карточка для отабражение цвета
@Composable
internal fun ColorCard(
    color: Color,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .size(60.dp)
            .clickable {
                onClick.invoke()
            },
        backgroundColor = color,
        elevation = 8.dp,
        shape = ClassJournalTheme.shapes.cornersStyle
    ) { }
}