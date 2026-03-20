package com.catshome.classJournal.screens.PayList

import com.catshome.classJournal.domain.communs.SortEnum
import com.catshome.classJournal.domain.Child.MiniChild
import com.catshome.classJournal.domain.Pay.Pay

data class PayListState(
    val showFAB: Boolean = false,
    val filterCollapse: Boolean = false,
    val isFilterData: Boolean =true,
    val isFilterChild: Boolean =false,
    val selectChild: MiniChild? = null,
    val listSearch: List<MiniChild>? = null,
    val sortValue: SortEnum? = SortEnum.Surname,
    val searchText: String = "",
    val selectedOption: Int = 1,
    val beginDate: String = "",
    val endDate: String = "",
    val allRange: Boolean= true,
    val index: Int =-1,
    val items: List<PayScreen> = emptyList(),
    val isShowSnackBar: Boolean = false,
    var isCanShowSnackBar: Boolean = false,
    val showFilter: Boolean = false,
    val messageShackBar:String? = null,
    var snackBarAction: String? =null,
    var onDismissed: (()->Unit)? = null,
    var onAction: (()->Unit)? = null
    )
data class PayScreen(
    val uidPay: String= "",
    val uidChild: String= "",
    val fio: String= "",
    val datePay: String= "",
    val payment: Int = 0,
    val isOptionsRevealed: Boolean = false,
    val isDelete: Boolean = false
)

fun Pay.toPayScreen(): PayScreen{
    return PayScreen(
        uidPay = this.uidPay,
        uidChild = this.uidChild,
        fio = "${this.name} ${this.surName}",
        datePay = this.datePay ,
        payment = this.payment,
        isOptionsRevealed = false
    )
}

fun PayScreen.toPay(): Pay{
    return Pay(
        uidPay = this.uidPay,
        uidChild = this.uidChild,
        name = this.fio.substring(0,this.fio.indexOf(' ')),
        surName = this.fio.substring(this.fio.indexOf(' ')+1),
        datePay = this.datePay ,
        payment = this.payment
    )
}

