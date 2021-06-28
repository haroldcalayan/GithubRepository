package com.haroldcalayan.githubrepository.data.source.local.dao

import androidx.room.Dao
import androidx.room.Query
import com.haroldcalayan.githubrepository.base.BaseDao
import com.haroldcalayan.githubrepository.data.source.local.entity.Keyword

@Dao
interface KeywordDao : BaseDao<Keyword> {

    @Query("SELECT * FROM $TABLE_NAME")
    suspend fun all(): List<Keyword>?

    @Query("SELECT * FROM $TABLE_NAME WHERE id = :id")
    suspend fun get(id: Int): Keyword?

    @Query("SELECT * FROM $TABLE_NAME ORDER BY id DESC LIMIT 1")
    suspend fun getFirst(): Keyword?

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun nukeTable()

    companion object {
        const val TABLE_NAME = "keyword"
    }

}