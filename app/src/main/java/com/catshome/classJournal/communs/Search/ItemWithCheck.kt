package com.catshome.classJournal.communs.Search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.room.util.TableInfo
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.communs.TextField
import com.catshome.classJournal.resource.R

@Composable
fun ItemWithCheck(
    startImage: Painter?,
    onClick: (String) -> Unit,
    item: String,
    price: String,
    onChangePrice: (String) -> Unit,
    isChecked: Boolean,
    texStyle: TextStyle? = null
) {
    val modifier = Modifier.padding(
        start = 16.dp,
        top = 8.dp,
        bottom = 8.dp,

        )
    Card(
        shape = ClassJournalTheme.shapes.cornersStyle,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp),
        colors = CardDefaults.cardColors(ClassJournalTheme.colors.secondaryBackground),
    ) {
        Column(Modifier.fillMaxWidth()) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .clickable {
                        //выбор элемента
                        onClick(item)

                    },
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {

                startImage?.let {
                    Icon(
                        painter = it,
                        modifier = modifier.widthIn(min = 56.dp),
                        contentDescription = "",
                        tint = ClassJournalTheme.colors.primaryText
                    )
                }
                Text(
                    text = item,
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(0.9f),
                    style = texStyle ?: ClassJournalTheme.typography.body,
                    color = ClassJournalTheme.colors.primaryText
                )

                if (isChecked) {
                    Icon(
                        painter = painterResource(R.drawable.box_ckeck),
                        modifier = modifier.widthIn(min = 56.dp),
                        contentDescription = "",
                        tint = ClassJournalTheme.colors.primaryText
                    )
                } else {

                    Icon(
                        painter = painterResource(R.drawable.box_out),//R.drawable.box_out),
                        modifier = modifier.widthIn(min = 56.dp),
                        contentDescription = "",
                        tint = ClassJournalTheme.colors.primaryText
                    )
                }
            }

            Row(
                Modifier
                    .fillMaxWidth(0.7f),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    modifier = Modifier
                        .padding(
                            start = 32.dp,
                        )
                        .weight(0.8f),
                    value = price,
                    label = "Стоймость", //stringResource(com.catshome.classJournal.resource.R.string.visit_price),
                    supportingText = "",//if (isPriceError) errorPriceMessage else "",
                    onValueChange = {
                        onChangePrice(it)
                    },
                    trailingIcon = null,
                    minLines = 1,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.merge(
                        KeyboardOptions(keyboardType = KeyboardType.Number)
                    ),
                    errorState = false,// isPriceError,
                    readOnly = false,
                )

            }
        }
    }
}