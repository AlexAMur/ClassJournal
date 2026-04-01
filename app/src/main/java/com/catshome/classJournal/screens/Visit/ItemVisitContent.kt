package com.catshome.classJournal.screens.Visit

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.catshome.classJournal.ClassJournalTheme
import com.catshome.classJournal.resource.R
import com.catshome.classJournal.communs.TextField

@Composable
fun ItemVisitContent(
    fio: String,
    price: String,
    onValueChange: (String) -> Unit,
    errorState: Boolean = false,
    isChecked: Boolean = false,
    onCheckClick: () -> Unit
) {

    Row(Modifier.fillMaxWidth()) {
        Text(text = fio,
            modifier = Modifier
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = price,
            label = stringResource(R.string.visit_price),
            supportingText = null,
            onValueChange = onValueChange,
            trailingIcon = null,
            minLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.merge(
                KeyboardOptions(keyboardType = KeyboardType.Number)
            ),
            errorState = errorState,
            readOnly = false
        )
        if (isChecked) {
            Icon(
                painter = painterResource(R.drawable.box_ckeck),
                modifier = Modifier,
                contentDescription = "",
                tint = ClassJournalTheme.colors.primaryText
            )
        } else {
            Icon(
                painter = painterResource(R.drawable.box_out),
                modifier = Modifier.clickable(onClick = onCheckClick),
                contentDescription = "",
                tint = ClassJournalTheme.colors.primaryText
            )
        }

    }


}