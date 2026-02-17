package com.catshome.classJournal.screens.Scheduler

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import com.catshome.classJournal.R
import com.catshome.classJournal.communs.SwipeToDeleteContainer
import com.catshome.classJournal.domain.Scheduler.Scheduler

@Composable
fun ItemLesson(value: Scheduler,isCollapse: Boolean, onDelete:()->Unit){
    if (isCollapse) {
        value.name?.let { text ->
            SwipeToDeleteContainer(
                item = text,
                onDelete = {
                }
            ) { name ->
                ItemListScheduler(
                    image = if (value.uidChild.isNullOrEmpty())
                        painterResource(R.drawable.fil_group_24)
                    else
                        painterResource(R.drawable.account_box_24),
                    text = name
                ) //можно добввить onClick
            }
        }
    }
}