package com.catshome.classJournal.screens.child

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animate
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat.animate
import com.catshome.classJournal.ClassJournalTheme
import kotlinx.coroutines.launch

@Composable
fun ItemGroup(modifier: Modifier = Modifier,offset: Float,  nameGroup: String, onClick: ()-> Unit) {
    val offsetAnime = remember {
        Animatable(initialValue = 0f)
    }
    LaunchedEffect(offset) {
            offsetAnime.animateTo(offset)
        }
    Card(
       modifier= modifier
            .background(ClassJournalTheme.colors.primaryBackground)
            .fillMaxWidth()
            .clickable{onClick()},
        shape = ClassJournalTheme.shapes.cornersStyle,
        colors = CardDefaults.cardColors(ClassJournalTheme.colors.controlColor)
    ) {
        Text(
            nameGroup,
            Modifier.fillMaxWidth()
                .height(56.dp)
                .padding(start = 16.dp, top = 16.dp)
                .offset(x= offsetAnime.value.dp)
            ,
            color = ClassJournalTheme.colors.primaryText,
            style = ClassJournalTheme.typography.body

        )
    }
}