package com.catshome.classJournal.domain.Child

import android.util.Log
import com.catshome.classJournal.domain.Group.Models.Group
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ChildInteractor @Inject constructor (private val childRepository: ChildRepository) {
    fun  getChildByID(uid: String): Child{
        if (uid=="")
            return Child()
        return childRepository.getChildById(uid)
    }
    fun saveChildUseCase(child: Child){
        Log.e("CLJR", "SaveChildUseCase uid = ${child.uid}, name=  ${child.name}, birthday=  ${child.birthday} ")
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