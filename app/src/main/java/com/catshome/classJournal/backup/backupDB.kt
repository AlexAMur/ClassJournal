package com.catshome.classJournal.backup

import android.content.Context
import android.util.Log
import androidx.sqlite.db.SimpleSQLiteQuery
import com.catshome.classJournal.AppDataBase
import com.catshome.classJournal.PayList.PayScreenEntity
import com.catshome.classJournal.communs.restartApp
import com.catshome.classJournal.di.AppModule
import com.catshome.classJournal.di.AppModule_ProvideAppDataBaseFactory.provideAppDataBase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import com.catshome.classJournal.context
import com.catshome.classJournal.domain.communs.SortEnum
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

fun backUp(dataBase: AppDataBase,backupDir: File, context: Context): Boolean {
    CoroutineScope(Dispatchers.IO).launch {
        val databaseDir =
            context.applicationContext.getDatabasePath("classJournal.db").parentFile
        if (databaseDir != null) {
            dataBase.query(SimpleSQLiteQuery("pragma wal_checkpoint(full)"))
            dataBase.close()
            if (!backupDir.exists()) {
                backupDir.mkdirs()
            }
            val filesToCopy = listOf(
                File(databaseDir, "classJournal"),
                File(databaseDir, "classJournal-wal"),
                File(databaseDir, "classJournal-shm")
            )
            for (file in filesToCopy) {
                if (file.exists()) {
                    val backupFile = File(backupDir, file.name)
                    FileInputStream(file).use { input ->
                        FileOutputStream(backupFile).use { output ->
                            input.copyTo(output)
                        }
                    }
                } else
                    Log.e("CLJR", "File not exists ${file.name}")
            }
        }
    }
    return true
}

fun restoreDB(dataBase: AppDataBase, context: Context, fileList: List<File?>): Boolean {
    var result = false
    val job = CoroutineScope(Dispatchers.IO).async {
        val databaseDir = context.applicationContext.getDatabasePath("classJournal.db").parentFile
        if (databaseDir != null) {
            dataBase.query(SimpleSQLiteQuery("pragma wal_checkpoint(full)"))
            dataBase.close()
            val backupDir = File(
                context.getExternalFilesDir(null),
                "BackupFolder") // Путь к бэкапу
            if (!backupDir.exists()) {
                backupDir.mkdirs()
            }
            val filesToCopy = listOf(
                File(databaseDir, "classJournal"),
                File(databaseDir, "classJournal-wal"),
                File(databaseDir, "classJournal-shm")
            )
            for (file in filesToCopy) {
                if (file.exists()) {
                    val backupFile = File(databaseDir, "${file.name}_bak") //переименовываем db
                    FileInputStream(file).use { input ->
                        FileOutputStream(backupFile).use { output -> //копируем оригиналы
                            input.copyTo(output)
                        }
                    }
                    file.delete()// удаляем оригиналы
                }
            }
// записываем на место базы данных импортируемую базу
            for (importFile in fileList) {
                importFile?.let {
                    val dataBaseFile =
                        File(databaseDir, importFile.name) //переименовываем db
                    FileInputStream(importFile).use { input ->
                        FileOutputStream(dataBaseFile).use { output -> //копируем оригиналы
                            input.copyTo(output)
                        }
                    }
                }
            }
            // удаляем копии
            for (file in filesToCopy) {
                val backupFile =
                    File(databaseDir, "${file.name}_bak") //переименовываем db
                backupFile.delete()// удаляем оригиналы
            }
            Log.e("CLJR", "Corutine")
            return@async true
        }
        return@async false
    }

//    CoroutineScope(Dispatchers.Default).launch {
    CoroutineScope(Dispatchers.Main).launch {
        Log.e("CLJR", "runBloking")
        result = job.await()
        if (result){
            Log.e("CLJR", "Restart")

        }
        //return@launch
    }

    return true
}