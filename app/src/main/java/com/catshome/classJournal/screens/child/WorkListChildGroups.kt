package com.catshome.classJournal.screens.child

import androidx.annotation.StringRes
import com.catshome.classJournal.R
import com.catshome.classJournal.context
import kotlin.collections.filter
import kotlin.collections.plus
import kotlin.collections.toMutableMap

internal fun hideChild(
    item: MutableMap<String, List<ChildItem>>,
    uidChild: String,

): MutableMap<String, List<ChildItem>> {
    val state: MutableMap<String, List<ChildItem>> =
        emptyMap<String, List<ChildItem>>().toMutableMap()
    item.forEach { map ->
        state.put(map.key, map.value.filter { it.child.childUid != uidChild })
    }
    return state
}


fun hideGroup(
    viewState: ChildListState,
    uidGroup: String,
    key: String
): ChildListState? {
    val a = viewState.item[key]?.filter {  //список детей в группе
        it.child.groupUid == uidGroup && it.child.childUid.isNotEmpty()
    }?.toMutableList()
    a?.let {
        viewState.item.forEach { k, value ->
            if (a.isEmpty())
                return@forEach
            val isDel = MutableList<Boolean>(a.size) { false }
            if (k != key && k != context.getString(R.string.no_group)) {
                a.forEachIndexed { index, item ->
                    isDel[index] = value.any { it.child.childUid == item.child.childUid }
                }
                isDel.forEachIndexed { i, it ->
                    if (it)
                        a.remove(a[i])
                }
            }
        }
    }
    viewState.item.remove(key)
    val s = viewState.item

    a?.forEachIndexed { index, child ->
        a[index] = a[index].copy(
            child = a[index].child.copy(
                groupUid = "",
                groupName = context.getString(R.string.no_group)
            )
        )
    }
    viewState.item[context.getString(R.string.no_group)]?.map {
        a?.add(it)
    }
    return viewState.copy(item = a?.let {
        s.plus(Pair(context.getString(R.string.no_group), it)).toSortedMap().toMutableMap()
    } ?: viewState.item)
}
