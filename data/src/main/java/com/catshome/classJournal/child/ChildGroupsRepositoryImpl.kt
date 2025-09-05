package com.catshome.classJournal.child

import com.catshome.classJournal.Group.GroupStorege.GroupStorage
import com.catshome.classJournal.Group.GroupStorege.mapToGroup
import com.catshome.classJournal.domain.Child.ChildGroup
import com.catshome.classJournal.domain.Child.ChildGroupRepository
import com.catshome.classJournal.domain.Group.GroupRepository
import com.catshome.classJournal.domain.Group.Models.Group
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class ChildGroupsRepositoryImpl @Inject constructor(val childGroupDAO: ChildGroupDAO) :
    ChildGroupRepository {
    override fun getGroups(): Flow<List<Group>> {
        return childGroupDAO.getGroups().map { list ->
            list.map { it.mapToGroup() }
        }
    }

    override fun deleteGroup(childGroup: ChildGroup) {
        CoroutineScope(Dispatchers.IO).launch {
            childGroupDAO.delete(
                ChildGroupEntity(
                    uid = childGroup.uid,
                    childId = childGroup.childId,
                    groupId = childGroup.groupId
                )
            )
        }
    }

    override fun insertChildGroup(childGroup: ChildGroup) {
        CoroutineScope(Dispatchers.IO).launch {
            childGroupDAO.insert(
                childGroup = ChildGroupEntity(
                    uid = childGroup.uid,
                    childId = childGroup.childId,
                    groupId = childGroup.groupId
                )
            )
        }
    }
}