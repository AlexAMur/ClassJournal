package com.catshome.classJournal.domain.Child


import android.content.Context
import android.util.Log
import com.catshome.classJournal.domain.Group.GroupRepository
import com.catshome.classJournal.domain.Group.Models.Group
import kotlinx.coroutines.flow.Flow
import java.util.UUID
import javax.inject.Inject

class ChildInteractor @Inject constructor (private val childRepository: ChildRepository,
                private val childGroupRepository: ChildGroupRepository,
            private val groupRepository: GroupRepository,
) {
    fun getListChildsWithGroups():  List<ChildWithGroups>{
        return childRepository.getChildWithGroups()
    }
    fun getChilds(isDelete: Boolean =false): Flow<List<Child>>{
        return childRepository.getChilds(isDelete)
    }
    fun  getChildByID(uid: String): Child{
        if (uid=="")
            return Child()
        return childRepository.getChildById(uid)
    }
    fun saveChildUseCase(child: Child, childGroup: List<ChildGroup>){
        if (child.uid.isEmpty())
            child.uid = UUID.randomUUID().toString()

        if(child.name.trim().isEmpty())
            return
        if(child.surname.trim().isEmpty())
            return
        //Если все проверки пройдены сохраняем данные
        childRepository.saveChild(child, childGroup )
    }
    fun getChild(uid: String): Child{
        return childRepository.getChildById(uid)
    }

    fun getGroupByChildUID(uid: String):Flow<List<ChildGroup>>?{
        return  childGroupRepository.getChildGroups(uid)
    }
    //снять пометку на удаление
    fun  unDeleteChildUseCase(child: Child){
        childRepository.setDelete(child.copy(isDelete = false))
    }
    //Пометить на удаление
    fun deleteChildUseCase(uidChild: String){
        childRepository.setDelete(childRepository.getChildById(uidChild).copy(isDelete = true))

    }
    fun removeUseCase(child: Child){
        childRepository.deleteChild(child)

    }
    //Вызывается для возврата списка групп
    fun getGroup():Flow<List<Group>>{
        return groupRepository.getGroups()
    }

}