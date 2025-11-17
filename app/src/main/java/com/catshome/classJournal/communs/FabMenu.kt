package com.catshome.classJournal.communs


import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material.icons.sharp.Close
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.BlendModeColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.LocalSettingsEventBus


@Composable
fun fabMenu(
    listFAB: List<ItemFAB>,
    modifier: Modifier = Modifier,
    fabVisible: Boolean
) {
    var showMenu by rememberSaveable { mutableStateOf(false) }
    val bottomPadding =
        LocalSettingsEventBus.current.currentSettings.collectAsState().value.innerPadding.calculateBottomPadding()
    val scale by animateFloatAsState(
        if (fabVisible) 1f else 0f,

        animationSpec = spring(
            dampingRatio = if (fabVisible) Spring.DampingRatioMediumBouncy
            else Spring.DampingRatioNoBouncy,
            stiffness = if (fabVisible) Spring.StiffnessLow else Spring.StiffnessMedium
        )
    )

    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
        if (listFAB.size > 1) {
            Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.End) {
                listFAB.forEach { itemFAB ->
                    if (showMenu) {
                        FloatingActionButton(
                            modifier = itemFAB.modifier.padding(end = 16.dp),
                            containerColor = ClassJournalTheme.colors.tintColor,
                            contentColor = ClassJournalTheme.colors.primaryText,
                            onClick = {
                                itemFAB.onClick()
                                showMenu = false
                            }) {
                            Row {
                                Icon(
                                    painter = (itemFAB.icon ?: Icons.Default.Info) as Painter,
                                    null,
                                    modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                                    tint = ClassJournalTheme.colors.primaryBackground
                                )

                            }
                        }
                    }
                }
                Spacer(Modifier.padding(bottom = 8.dp))
                FloatingActionButton(
                    modifier = Modifier
                        .padding(bottom = bottomPadding + 16.dp, end = 16.dp)
                        .scale(scale),
                    containerColor = if (showMenu) ClassJournalTheme.colors.controlColor else ClassJournalTheme.colors.tintColor,
                    contentColor = if (showMenu) ClassJournalTheme.colors.primaryText else
                        ClassJournalTheme.colors.secondaryBackground,
                    shape = if (showMenu) CircleShape else FloatingActionButtonDefaults.shape,

                    onClick = {
                        showMenu = !showMenu
                    })
                {
                    Icon(
                        if (showMenu) Icons.Sharp.Close else Icons.Sharp.Add, null,
                        tint = if (showMenu) ClassJournalTheme.colors.primaryText else
                            ClassJournalTheme.colors.secondaryBackground
                    )
                }
            }
        } else {
            if (listFAB.isNotEmpty()) {
                FloatingActionButton(
                    modifier = Modifier
                        .padding(bottom = bottomPadding + 16.dp, end = 16.dp)
                        .scale(scale),
                    containerColor =  listFAB[0].containerColor,
                    contentColor = listFAB[0].contentColor,
                    shape =  FloatingActionButtonDefaults.shape,

                    onClick =listFAB[0].onClick
                )
                {
                    Icon(
                        painter = (listFAB[0].icon?: Icons.Sharp.Add) as Painter,
                        contentDescription = null,
                        tint = ClassJournalTheme.colors.secondaryBackground,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp) ,

                    )
                }
            }
        }
    }
}