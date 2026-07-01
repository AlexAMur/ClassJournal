package com.catshome.classJournal.domain.Child

import kotlin.String

data class Child(
    var uid : String = "",
    var name: String = "",
    var surname: String ="",
    var birthday: String = "",
    var phone: String = "",
    var note: String = "",
    val saldo: Int = 0,
    var isDelete: Boolean = false
)
fun Child.mapToMiniChild(): MiniChild{
    return MiniChild(
       uid =this.uid,
     fio ="${this.surname} ${this.name}",
     saldo =this.saldo,
     name =this.name,
     surname =this.surname
    )
}
