package com.haroldcalayan.githubrepository.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.haroldcalayan.githubrepository.data.source.local.dao.KeywordDao
import com.haroldcalayan.githubrepository.data.source.local.entity.Keyword

const val VERSION_NUMBER = 1

@Database(
    entities = [Keyword::class],
    version = VERSION_NUMBER
)
abstract class AppDatabase: RoomDatabase() {
    abstract fun keywordDao():KeywordDao
}