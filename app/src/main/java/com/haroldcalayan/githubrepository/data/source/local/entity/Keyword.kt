package com.haroldcalayan.githubrepository.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Keyword(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    var keyword: String
)