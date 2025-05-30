package com.catshome.ClassJournal.Group.GroupStorege

import com.catshome.ClassJournal.domain.Group.Models.Group

interface GroupStorage {
    fun insert(group: Group):Boolean
    fun delete(group: Group):Boolean
    fun update(group: Group):Boolean
    fun read():List<Group>
}