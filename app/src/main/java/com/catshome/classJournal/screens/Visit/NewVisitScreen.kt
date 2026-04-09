package com.catshome.classJournal

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.catshome.classJournal.navigate.VisitDetails
import com.catshome.classJournal.resource.R
import com.catshome.classJournal.screens.ItemScreen
import com.catshome.classJournal.screens.Visit.NewVisitAction
import com.catshome.classJournal.screens.Visit.NewVisitByScheduler
import com.catshome.classJournal.screens.Visit.NewVisitContent
import com.catshome.classJournal.screens.Visit.NewVisitEvent
import com.catshome.classJournal.screens.Visit.NewVisitViewModel


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NewVisitScreen(
    viewModel: NewVisitViewModel = viewModel(),
    details: VisitDetails? = null,
) {
    val outerNavigation = localNavHost.current
    val viewAction by viewModel.viewActions().collectAsState(null)
    var cardEnabled by remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val bottomPadding =
        LocalSettingsEventBus.current.currentSettings.collectAsState().value.innerPadding.calculateBottomPadding()
    val pageName = listOf("По расписанию", "Свободное")

    val pagerState = rememberPagerState(
        initialPage =  Int.MAX_VALUE / 1024 -1,
        pageCount = { Int.MAX_VALUE / 512 }
    )
    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(all = 0.dp),
        pageSpacing = 0.dp,
        verticalAlignment = Alignment.Top,

        ) { index ->
        val pageIndex = index % pageName.size
        val pageOffset by remember {
            derivedStateOf {
                (pagerState.currentPage - index) + pagerState.currentPageOffsetFraction
            }
        }
        val shapeAnime by animateDpAsState(
            if (pageOffset != 0.0f)
                16.dp
            else
                0.dp,
            animationSpec = tween(delayMillis = 300)
        )
        val colorAnime by animateColorAsState(
            if (pageOffset != 0.0f)
                ClassJournalTheme.colors.primaryBackground
            else
                ClassJournalTheme.colors.secondaryBackground,
            animationSpec = tween(delayMillis = 200)
        )
        val pageSize by animateFloatAsState(
            targetValue = if (pageOffset != 0.0f) 0.8f else 1f,
            animationSpec = tween(delayMillis = 300)
        )
        LaunchedEffect(pageIndex == 0) {
            Log.e("CLJR", "Launcher!!!!!!!!!!!!!")
            if (pageOffset == 0.0f)
             viewModel.obtainEvent(NewVisitEvent.getScheduler)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(ClassJournalTheme.colors.primaryBackground),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Card(
                modifier = Modifier
                    .padding(bottom = bottomPadding)
                    .fillMaxSize()
                    .graphicsLayer {
                        scaleX = pageSize
                        scaleY = pageSize
                        cardEnabled = pageOffset == 0.0f
                    },
                shape = RoundedCornerShape(size = shapeAnime),
                colors = CardDefaults.cardColors(
                    colorAnime
                ),

                ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = pageName[pageIndex],
                        style = ClassJournalTheme.typography.caption,
                        color = ClassJournalTheme.colors.primaryText
                    )
                }
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(
                        Modifier.fillMaxWidth(0.75f),
                        DividerDefaults.Thickness,
                        ClassJournalTheme.colors.tintColor
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextButton(
                        onClick =
                            {
                                viewModel.obtainEvent(NewVisitEvent.CancelClicked)
                            }
                    ) {
                        Icon(
                            Icons.Default.Close, "", tint = ClassJournalTheme.colors.tintColor
                        )
                    }
                    Text(
                        stringResource(R.string.new_visit_dialog_headline),
                        color = ClassJournalTheme.colors.primaryText,
                        style = ClassJournalTheme.typography.caption
                    )
                    TextButton(onClick = {
                        viewModel.obtainEvent(NewVisitEvent.SaveClicked)
                    }
                    ) {
                        Text(
                            stringResource(R.string.save_button),
                            color = ClassJournalTheme.colors.tintColor
                        )
                    }
                }

                if (pageIndex == 1)
                    NewVisitContent(viewModel)
                else
                    NewVisitByScheduler(viewModel)
            }
        }
    }
    when (viewAction) {
        NewVisitAction.CloseScreen -> {
            keyboardController?.hide()
            viewModel.clearAction()
            outerNavigation.navigate(route = ItemScreen.VisitListScreen.name)
            {
                popUpTo(ItemScreen.VisitListScreen.name) {
                    inclusive = true
                }
            }
        }

        null -> {}
    }
}
