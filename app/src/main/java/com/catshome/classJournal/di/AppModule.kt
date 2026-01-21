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
        db.execSQL("CREATE TABLE PAYS ('uid' TEXT NOT NULL PRIMARY KEY, 'uid_child' TEXT NOT NULL," +
                " 'date_pay' INTEGER NOT NULL," +
                " 'pay' INTEGER NOT NULL,  FOREIGN KEY ('uid_child') REFERENCES 'child'('uid') ON DELETE CASCADE)")
        db.execSQL("Create Index paysindex on pays ('uid_child','date_pay',  'pay') ")
        db.execSQL("Drop table 'payentity'")
    }
}
internal val MIGRATION_6_7 = object : Migration(6, 7) {
    override fun migrate(db: SupportSQLiteDatabase) {
        db.execSQL("CREATE TABLE VISITS ('uid' TEXT NOT NULL PRIMARY KEY, 'uidChild' TEXT NOT NULL," +
                " 'dateVisit' INTEGER NOT NULL," +
                " 'priceVisit' INTEGER NOT NULL,  FOREIGN KEY ('uidChild') REFERENCES 'child'('uid') ON DELETE CASCADE)")
        db.execSQL("Create Index indexVisit on visits ('dateVisit')")

        db.execSQL("CREATE TABLE SCHEDULER ('uid' TEXT NOT NULL PRIMARY KEY, 'dayOfWeek' INTEGER NOT NULL," +
                " 'uidGroup' TEXT, 'uidChild' TEXT, 'startLesson' INTEGER NOT NULL, " +
                "'duration' INTEGER NOT NULL, " +
                "FOREIGN KEY ('uidChild') REFERENCES 'child'('uid') ON DELETE CASCADE, " +
                "FOREIGN KEY ('uidGroup') REFERENCES 'groups'('uid') ON DELETE CASCADE)"
            )
        db.execSQL("Create Index indexScheduler on SCHEDULER ('dayOfWeek', 'startLesson')")
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
            .addMigrations(MIGRATION_6_7)
            .build()

    }
}