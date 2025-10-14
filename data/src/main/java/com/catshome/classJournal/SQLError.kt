package com.catshome.classJournal

sealed class SQLError {
    object SqlUniqueError: SQLError()
    object SqlSError: SQLError()
}