package com.catshome.classJournal.di

import android.content.Context
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.catshome.classJournal.AppDataBase
import com.catshome.classJournal.getDatabaseBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
internal val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("ALTER TABLE child_group DROP CONSTRAINT foreignKeys")
        db.execSQL("ALTER TABLE child_group ADD CONSTRAINT foreignKeys FOREIGN KEY childId REFERENCES child (uid) ON DELETE CASCADE")
        db.execSQL("ALTER TABLE child add column saldo INTEGER default 0 NOT NULL")
    }
}
internal val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(db: SupportSQLiteDatabase) {
    //    db.execSQL("CREATE TABLE IF NOT EXISTS `PAYS` (`uid` TEXT NOT NULL, `uid_child` TEXT NOT NULL, `date_pay` INTEGER NOT NULL, `pay` INTEGER NOT NULL, PRIMARY KEY(`uid`))")
        db.execSQL("CREATE TABLE PAYS (\n" +
                " 'uid' TEXT PRIMARY KEY,\n" +
                " 'uid_child' TEXT NOT NULL,\n" +
                " 'date_pay' TEXT NOT NULL,\n" +
                " 'pay' INTEGER NOT NULL,\n" +
                " FOREIGN KEY ('uid_child') REFERENCES 'child'('iud')) DELETE CASCADE")
        db.execSQL("Drop table 'payentity'")
    }
}


@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    fun provideAppDataBase(@ApplicationContext context: Context): AppDataBase {
        return getDatabaseBuilder(context = context).fallbackToDestructiveMigration(dropAllTables = true)
            .fallbackToDestructiveMigration(dropAllTables = true)
            .addMigrations(MIGRATION_2_3)
            .addMigrations(MIGRATION_3_4)
            .build()

    }
}