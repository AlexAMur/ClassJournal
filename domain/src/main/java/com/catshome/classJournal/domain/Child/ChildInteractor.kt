package com.catshome.classJournal.domain.Child


import android.util.Log
import com.catshome.classJournal.domain.Group.GroupRepository
import com.catshome.classJournal.domain.Group.Models.Group
import kotlinx.coroutines.flow.Flow
import java.util.Objects.isNull
import java.util.UUID
import javax.inject.Inject

class ChildInteractor @Inject constructor(
    private val childRepository: ChildRepository,
    private val childGroupRepository: ChildGroupRepository,
    private val groupRepository: GroupRepository,
) {
    fun getListChildsWithGroups(): List<ChildWithGroups> {
        return childRepository.getChildWithGroups()
    }

    fun getChildByID(uid: String): Child? {
        if (uid == "")
            return null
        return childRepository.getChildById(uid)
    }

    suspend fun saveChildUseCase(child: Child, childGroup: List<ChildGroup>): Boolean {
        if (child.uid.isEmpty())
            child.uid = UUID.randomUUID().toString()

        if (child.name.trim().isEmpty() || child.surname.trim().isEmpty() || child.birthday.trim()
                .isEmpty()
        )
            return false
        //Если все проверки пройдены сохраняем данные
// перед измененим или добавление ребенкка проверить на наличие удаленных записей и затем сохранять
        val childtmp = childRepository.childDeleteExists(child)
        //childRepository.childDuble(Child(name = "Avto4281" , surname = "avto", birthday = (1729468800000L).toDateStrungRU()))
        if (isNull(childtmp)) {
            childRepository.saveChild(child, childGroup)
        } else //есть удаленный ребенок с той же фамилией именем и датой рождения
        {
            childtmp?.let {
                childRepository.updateChild(
                    it.copy(name = "${childtmp.name}_"))
            }
            childRepository.saveChild(child, childGroup)
            Log.e("CLJR", "Child is exists")
        }
        return true
    }

    fun getGroupByChildID(uid: String): Flow<List<ChildGroup>>? {
        return childGroupRepository.getChildGroups(uid)
    }

    //Пометить на удаление
    suspend fun deleteChildUseCase(child: Child) {
        childRepository.deleteSet(child.copy(isDelete = true))
    }

    //Вызывается для возврата списка групп
    fun getGroup(): Flow<List<Group>> {
        return groupRepository.getGroups()
    }

}