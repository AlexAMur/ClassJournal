package com.catshome.classJournal.domain.Child

import com.catshome.classJournal.domain.Group.Models.Group
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChildInteractor @Inject constructor (private val childRepository: ChildRepository) {
    fun  getChildByID(uid: Long): Child{
        if (uid==0L)
            return Child()
        return childRepository.getChildById(uid)
    }
    fun saveChildUseCase(child: Child){
        childRepository.saveChild(child)
    }
    fun getChildUseCase(isDelete: Boolean): Flow<List<Child>>{
        return childRepository.getChilds(isDelete)
    }
    fun getGroupAllUseCase(): Flow<List<Child>>{
        return childRepository.getAllChilds()
    }
    //снять пометку на удаление
    fun  unDeleteUseCase(child: Child){
        childRepository.updateChild(child.copy(isDelete = false))
    }
    //Пометить на удаление
    fun deleteGroupUseCase(child: Child){
        childRepository.updateChild(child.copy(isDelete = true))

    }
    fun removeUseCase(child: Child){
        childRepository.deleteChild(child)

    }

}