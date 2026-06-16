package com.catshome.classJournal.backup

import android.content.Context
import android.util.Log
import androidx.sqlite.db.SimpleSQLiteQuery
import com.catshome.classJournal.AppDataBase
import com.catshome.classJournal.databaseDir
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.inject.Inject

fun backUp (dataBase: AppDataBase, context: Context): Boolean{
    CoroutineScope(Dispatchers.IO).launch {
        if (databaseDir != null) {
            dataBase.query(SimpleSQLiteQuery("pragma wal_checkpoint(full)"))
            dataBase.close()
            val backupDir = File(context.getExternalFilesDir(null), "BackupFolder") // Путь к бэкапу
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
                }else
                    Log.e("CLJR", "File not exists ${file.name}")
            }
        }
    }
    return true
}
fun restoreDB(dataBase: AppDataBase, context: Context){
    CoroutineScope(Dispatchers.IO).launch {
        if (databaseDir != null) {
            dataBase.query(SimpleSQLiteQuery("pragma wal_checkpoint(full)"))
            dataBase.close()

            val backupDir = File(context.getExternalFilesDir(null), "BackupFolder") // Путь к бэкапу
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
                }else
                    Log.e("CLJR", "File not exists ${file.name}")
            }
        }
    }
    return true

}