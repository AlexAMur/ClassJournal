package com.catshome.classJournal.child


import android.content.Context
import com.catshome.classJournal.R
import com.catshome.classJournal.domain.Child.Child
import com.catshome.classJournal.domain.Child.ChildGroup
import com.catshome.classJournal.domain.Child.ChildWithGroups
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class RoomChildStorage @Inject constructor(
    @ApplicationContext val context: Context,
    val childDAO: ChildDAO,
    val childGroup: ChildGroupDAO
) :ChildStorage {


    override suspend fun insert(
        child: Child,
        childGroup: List<ChildGroup>
    ) {
            childDAO.insertChild(child.mapToChildEntity(), childGroup.map {
                it.mapToChildGroupEntity()
            })
    }
//пометить на удаление  ребенка
    override suspend fun deleteSet(child: Child) {
        childDAO.update(child.copy(isDelete = true).mapToChildEntity())
    }

    override suspend fun delete(child: Child) {
            childDAO.delete(child.mapToChildEntity())
    }

    override suspend fun updateChildWithGroups(
        child: Child,
        childGroup: List<ChildGroup>
    ) {         childDAO.updateChild(child.mapToChildEntity(),
                    group = childGroup.map { it.mapToChildGroupEntity()}
                )
    }

    override suspend fun update(child: Child) {
        childDAO.update(child.mapToChildEntity())
    }

    override fun getChildById(uid: String): Child? {
        val defferedChild = CoroutineScope(Dispatchers.IO).async {
            return@async (childDAO.getChildById(uid))
        }
        val data = runBlocking {
            return@runBlocking defferedChild.await()
        }
        return data?.mapToChild()
    }

    override fun getChildByName(child: Child): Flow<List<Child>> {
        return childDAO.getChildByName(child.surname.toString()).map { list ->
            list.map { childEntity ->
                childEntity.mapToChild()
            }
        }
    }

    override fun read(isDelete: Boolean): Flow<List<Child>> {
        return childDAO.getChilds(isDelete).map { list ->
            list.map { childEntity ->
                childEntity.mapToChild()
            }
        }
    }

    override fun readAll(): Flow<List<Child>> {
        return childDAO.getFull().map { list ->
            list.map { childEntity ->
                childEntity.mapToChild()
            }
        }
    }

    override fun getChildWithGroups(isDelete: Boolean): List<ChildWithGroups> {
        val defferedChild = CoroutineScope(Dispatchers.IO).async {
            return@async childGroup.getChildAndGroups(isDelete).map {
                ChildWithGroups(
                    childUid = it.childUid,
                    childName = it.childName,
                    childSurname = it.childSurname,
                    groupUid = it.groupUid?:"",
                    groupName = it.groupName ?: context.getString(R.string.no_group)
                )
            }
        }
        val data = runBlocking {
            return@runBlocking defferedChild.await()
        }
        return data
    }

    override fun childDeleteExists(child: ChildEntity): ChildEntity? {
        return childDAO.findDeleteChild(name = child.name,
            surname = child.surname,
            birthday = child.birthday,
            isDelete = true)
    }
}
