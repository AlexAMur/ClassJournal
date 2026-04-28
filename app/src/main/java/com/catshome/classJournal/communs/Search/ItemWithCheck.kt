package com.catshome.classJournal.communs.Search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.communs.TextField
import com.catshome.classJournal.resource.R

@Composable
fun ItemWithCheck(
    startImage: Painter?,
    onClick:(String)->Unit,
    item: String,
    price: String,
    onChangePrice:(String)->Unit,
    isChecked: Boolean,
    texStyle: TextStyle? =  null
    ){
    Card(
        shape = ClassJournalTheme.shapes.cornersStyle,
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 8.dp),
        colors = CardDefaults.cardColors(ClassJournalTheme.colors.secondaryBackground),
    ) {
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
            val modifier = Modifier.padding(
                start = 16.dp,
                top = 8.dp,
                bottom = 8.dp,

            )
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
                modifier = modifier.fillMaxWidth()
                    .weight(0.9f),
                style = texStyle?:ClassJournalTheme.typography.body,
                color = ClassJournalTheme.colors.primaryText
            )
            TextField(
                modifier = modifier
                    .padding(top = 16.dp)
                    .weight(0.8f)
                    ,
                value = price,
                label = "Стоймость", //stringResource(com.catshome.classJournal.resource.R.string.visit_price),
                supportingText = "",//if (isPriceError) errorPriceMessage else "",
                onValueChange = {
                    onChangePrice(it)
//                viewModel.obtainEvent(
//                    NewVisitEvent.ChangePriceOnScheduler(
//                        key = mapScheduler.key,
//                        dayInt = page,
//                        index = itemIndex,
//                        text = it,
//                    )
//                )
                },
                trailingIcon = null,
                minLines = 1,
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.merge(
                    KeyboardOptions(keyboardType = KeyboardType.Number)
                ),
                errorState = false,// isPriceError,
                readOnly = false,
//                colors = OutlinedTextFieldDefaults.colors(
//                    focusedTextColor = ClassJournalTheme.colors.primaryText,
//                    unfocusedTextColor = ClassJournalTheme.colors.primaryText,
//                    focusedBorderColor = ClassJournalTheme.colors.tintColor,
//                    focusedSupportingTextColor = ClassJournalTheme.colors.tintColor,
//                    unfocusedSupportingTextColor = ClassJournalTheme.colors.primaryText,
//                    focusedLabelColor = ClassJournalTheme.colors.tintColor,
//                    unfocusedLabelColor = ClassJournalTheme.colors.primaryText,
//                    errorLabelColor = ClassJournalTheme.colors.errorColor,
//                    errorBorderColor = ClassJournalTheme.colors.errorColor,
//                    errorSupportingTextColor = ClassJournalTheme.colors.errorColor,
//                    errorTextColor = ClassJournalTheme.colors.errorColor,
//                    cursorColor = ClassJournalTheme.colors.tintColor,
//                    errorCursorColor = ClassJournalTheme.colors.errorColor,
//                )
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
                    painter = painterResource( R.drawable.box_out),//R.drawable.box_out),
                    modifier = modifier.widthIn(min = 56.dp),
                    contentDescription = "",
                    tint = ClassJournalTheme.colors.primaryText
                )
            }

        }
    }
}