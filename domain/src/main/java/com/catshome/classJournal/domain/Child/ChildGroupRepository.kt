package com.catshome.classJournal.domain.Child

import com.catshome.classJournal.domain.Group.Models.Group
import kotlinx.coroutines.flow.Flow

interface ChildGroupRepository {
    fun getGroups(): Flow<List<Group>>
    fun deleteGroup(childGroup: ChildGroup)
    fun insertChildGroup(childGroup: ChildGroup)
}