package com.catshome.ClassJournal.domain.Group

import com.catshome.ClassJournal.domain.Group.Models.Group
import javax.inject.Inject

class GroupInteractor @Inject constructor(groupRepository: GroupRepository) {
    fun saveGroupUseCase(group: Group){
        //g
    }
    fun getGroupUseCase():List<Group>{
        return emptyList()
    }
    fun getGroupAllUseCase():List<Group>{

        return emptyList()
    }
    fun deleteGroupUseCase(group: Group){

    }
}