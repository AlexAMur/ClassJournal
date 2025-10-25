package com.catshome.classJournal.domain.Group

import com.catshome.classJournal.domain.Group.Models.Group
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class GroupInteractor @Inject constructor (private val groupRepository: GroupRepository) {
    fun  getGroupByID(uid: String): Group{
        if (uid=="")
            return Group()
        return groupRepository.getGroupById(uid)
    }
    suspend fun saveGroupUseCase(group: Group){
        groupRepository.saveGroup(group)
    }
    fun getGroupUseCase(isDelete: Boolean): Flow<List<Group>>{
        return groupRepository.getGroups(isDelete)
    }
    fun getGroupAllUseCase(): Flow<List<Group>>{
        return groupRepository.getAllGroups()
    }
    fun deleteUseCase(group: Group){
        CoroutineScope(Dispatchers.IO).launch {
            groupRepository.deleteGroup(group)
        }

    }
}