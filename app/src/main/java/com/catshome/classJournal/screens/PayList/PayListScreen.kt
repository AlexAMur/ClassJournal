package com.catshome.classJournal.screens.PayList

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.catshome.classJournal.communs.FilterScreen.FilterSetting
import com.catshome.classJournal.navigate.DetailsPay
import com.catshome.classJournal.navigate.OptionFilterPaysList
import com.catshome.classJournal.screens.ItemScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PayListScreen(
    optionFilter: OptionFilterPaysList? = null,
    detailsPay: DetailsPay? = null,
    navController: NavController,
    viewModel: PayListViewModel = viewModel()
) {
    val viewState by viewModel.viewState().collectAsState()
    val viewAction by viewModel.viewActions().collectAsState(null)

    LaunchedEffect(Unit) {
        optionFilter?.let {
            viewModel.obtainEvent(PayListEvent.SetOption(optionFilter))

        }
        detailsPay?.let {
            viewModel.obtainEvent(PayListEvent.ShowSnackBar(it))
        }
        viewModel.obtainEvent(PayListEvent.ReloadScreen)

        CoroutineScope(Dispatchers.Default).launch {
            viewModel.obtainEvent(PayListEvent.ShowFAB(false))
            delay(100)
            viewModel.obtainEvent(PayListEvent.ShowFAB(true))
        }
    }
    /*  detailsPayList?.let {
          // if (detailsPayList.isShowSnackBar) {
          viewModel.obtainEvent(
              PayListEvent.ShowSnackBar(
                  detailsPayList.isShowSnackBar,
                  detailsPayList.Message
              )
          )
      }*/
    DisposableEffect(Unit) {
        onDispose {
            viewModel.obtainEvent(PayListEvent.ShowFAB(false))
        }
    }
    PayListContent(viewState, viewModel)
    when (viewAction) {
        PayListAction.CloseScreen -> {
            viewModel.clearAction()
        }
        PayListAction.NewPay -> {
            if (viewModel.viewActions().replayCache.last().toString() == "NewPay") {
                navController.navigate(ItemScreen.NewPayScreen.name)
                viewModel.clearAction()
            }
        }
        PayListAction.OpenFilter -> {
            navController.navigate(
                FilterSetting(
                    childId = viewState.selectChild?.uid,
                    childFIO = viewState.selectChild?.fio,
                    optionsIndex = viewState.selectedOption,
                    sortEnum = viewState.sortValue,
                    beginDate = viewState.beginDate,//"01.${LocalDateTime.now().month.value}.${LocalDateTime.now().year}",
                    endDate = viewState.endDate,//LocalDate.now().plusDays(1).atStartOfDay().minusSeconds(1)
                )
            )
            viewModel.clearAction()
        }
        null -> {}
    }
}
