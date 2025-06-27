package com.catshome.ClassJournal.domain.Group

import android.util.Log
import com.catshome.ClassJournal.R
import com.catshome.ClassJournal.domain.Group.Models.Group
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GroupInteractor @Inject constructor (private val groupRepository: GroupRepository) {
    fun  getGroupByID(uid: Long): Group{
        return groupRepository.getGroupById(uid)
    }
    fun saveGroupUseCase(group: Group){
        groupRepository.saveGroup(group)

        Log.e("CLJR", "Save group!!!!!!")
        //g
    }
    fun getGroupUseCase(isDelete: Boolean): Flow<List<Group>>{
        return groupRepository.getGroups(isDelete)
    }
    fun getGroupAllUseCase(): Flow<List<Group>>{
        return groupRepository.getGroups(false)


    }
    fun deleteGroupUseCase(group: Group){

    }
}