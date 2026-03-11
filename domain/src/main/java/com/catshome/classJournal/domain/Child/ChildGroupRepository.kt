package com.catshome.classJournal.domain.Child

import com.catshome.classJournal.domain.Group.Models.Group
import kotlinx.coroutines.flow.Flow

interface ChildGroupRepository {
    fun getChildGroups(uid: String): Flow<List<ChildGroup>>
    fun insertChildGroup(childGroup: ChildGroup)
}