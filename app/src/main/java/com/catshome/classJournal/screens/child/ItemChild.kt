package com.catshome.classJournal.screens.child

import android.content.Intent
import android.os.SystemClock
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.domain.communs.toLocalDateTimeRu
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.monthsUntil
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock


@Composable
fun itemChild(offset: Float,
              name: String,
              surname: String,
              birthday: String,
              saldo: String,
              onClick: () -> Unit) {
    val offsetAnime = remember {
        Animatable(initialValue = 0f)
    }
    LaunchedEffect(offset) {
        offsetAnime.animateTo(offset)
    }

    Card(
        Modifier
            .fillMaxWidth()
            .background(ClassJournalTheme.colors.primaryBackground)
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(ClassJournalTheme.colors.secondaryBackground),
        shape = ClassJournalTheme.shapes.cornersStyle
    )

    {
        Row(Modifier.fillMaxWidth()
            .offset(x = offsetAnime.value.dp)
            , horizontalArrangement = Arrangement.SpaceAround, verticalAlignment = Alignment.CenterVertically) {
            Column {
                Row(Modifier.fillMaxWidth()) {
                    Text(
                        text = name,
                        modifier = Modifier
                            .padding(start = 16.dp, top = 8.dp, bottom = 8.dp),
                        //.//fillMaxWidth(),
                        color = ClassJournalTheme.colors.primaryText,
                        style = ClassJournalTheme.typography.toolbar
                    )

                    Text(
                        text = surname,
                        modifier = Modifier
                            .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)

                            .fillMaxWidth(),
                        color = ClassJournalTheme.colors.primaryText,
                        style = ClassJournalTheme.typography.toolbar
                    )
                }
                Row(Modifier.fillMaxWidth()) {
                    Text(
                        text = "Баланс ",
                        modifier = Modifier
                            .padding(start = 16.dp, bottom = 4.dp)
                            ,
                        color = ClassJournalTheme.colors.primaryText,
                        style = ClassJournalTheme.typography.body
                    )

                    Text(
                        text = if (saldo.toInt() < 0) "-${saldo}" else saldo,
                        modifier = Modifier
                            .padding(start = 8.dp, bottom = 4.dp)
                            .fillMaxWidth(),
                        color = if (saldo.toInt() >= 0) ClassJournalTheme.colors.primaryText
                        else ClassJournalTheme.colors.errorColor,
                        style = ClassJournalTheme.typography.body
                    )
                }
                Row(Modifier.fillMaxWidth()) {
                    Text(
                        text = birthday,
                        modifier = Modifier
                            .padding(start = 8.dp, bottom = 4.dp)                            ,
                        color = ClassJournalTheme.colors.primaryText,
                        style = ClassJournalTheme.typography.body
                    )
                    Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date.let {now->
                        val month = birthday.toLocalDateTimeRu()?.date?.monthsUntil(now)?:0
                        Text(
                            text = "Возраст: "+if(month/12>0){(month/12).toString()+"г."} else {""} +
                                    if(month%12>0){(month%12).toString()+"м."}
                                    else{""},
                            modifier = Modifier
                                .padding(start = 16.dp, bottom = 4.dp)
                                .fillMaxWidth(),
                            color = ClassJournalTheme.colors.primaryText,
                            style = ClassJournalTheme.typography.body
                        )
                    }
                }
            }

        }
    }
}

