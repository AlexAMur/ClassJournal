package com.catshome.ClassJournal.domain.Group

import android.util.Log
import com.catshome.ClassJournal.R
import com.catshome.ClassJournal.domain.Group.Models.Group
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GroupInteractor @Inject constructor (private val groupRepository: GroupRepository) {
    fun  getGroupByID(uid: Long): Group{
        if (uid==0L)
            return Group()
        return groupRepository.getGroupById(uid)
    }
    fun saveGroupUseCase(group: Group){
        groupRepository.saveGroup(group)
    }
    fun getGroupUseCase(isDelete: Boolean): Flow<List<Group>>{
        return groupRepository.getGroups(isDelete)
    }
    fun getGroupAllUseCase(): Flow<List<Group>>{
        return groupRepository.getAllGroups()
    }
    //снять пометку на удаление
    fun  unDeleteUseCase(group: Group){
        groupRepository.updateGroup(group.copy(isDelete = false))
    }
    //Пометить на удаление
    fun deleteGroupUseCase(group: Group){
        groupRepository.updateGroup(group.copy(isDelete = true))

    }
    fun removeUseCase(group: Group){
        groupRepository.deleteGroup(group)

    }
}