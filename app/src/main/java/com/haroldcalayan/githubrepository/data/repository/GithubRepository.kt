package com.haroldcalayan.githubrepository.data.repository

import com.haroldcalayan.githubrepository.base.BaseRepository
import com.haroldcalayan.githubrepository.data.model.Item
import com.haroldcalayan.githubrepository.data.model.Owner
import com.haroldcalayan.githubrepository.data.source.local.database.AppDatabase
import com.haroldcalayan.githubrepository.data.source.local.entity.Keyword
import com.haroldcalayan.githubrepository.data.source.remote.response.SearchResponse
import com.haroldcalayan.githubrepository.data.source.remote.service.GithubApiService

interface GithubRepository {
    suspend fun searchRepositories(originalQuery: String, encodedQuery: String): SearchResponse?
    suspend fun getAllKeywords(): List<Keyword>?
}

class GithubRepositoryImpl(val database: AppDatabase, val api: GithubApiService): BaseRepository(), GithubRepository {

    override suspend fun searchRepositories(originalQuery: String, encodedQuery: String): SearchResponse? {
        val response = if (USE_DUMMY) getDummyResponse() else api.searchRepositories(encodedQuery)
        database.keywordDao().insert(Keyword(keyword = originalQuery))
        return response
    }

    override suspend fun getAllKeywords() = database.keywordDao().all()

    private fun getDummyResponse() : SearchResponse {
        val dummyList = ArrayList<Item>()
        val url = "https://github.githubassets.com/images/modules/logos_page/GitHub-Mark.png"
        val htmlUrl = "https://github.com/flutter/flutter"
        val owner = Owner(login = "flutter")
        dummyList.apply {
            add(Item(name = "Item 1", id = 123, url = url, htmlUrl = htmlUrl, owner = owner))
            add(Item(name = "Item 2", id = 124, url = url, htmlUrl = htmlUrl))
            add(Item(name = "Item 3", id = 125, url = url, htmlUrl = htmlUrl))
            add(Item(name = "Item 4", id = 126, url = url, htmlUrl = htmlUrl))
            add(Item(name = "Item 5", id = 127, url = url, htmlUrl = htmlUrl))
            add(Item(name = "Item 6", id = 128, url = url, htmlUrl = htmlUrl))
            add(Item(name = "Item 7", id = 129, url = url, htmlUrl = htmlUrl))
            add(Item(name = "Item 8", id = 130, url = url, htmlUrl = htmlUrl))
            add(Item(name = "Item 9", id = 131, url = url, htmlUrl = htmlUrl))
            add(Item(name = "Item 10", id = 132, url = url, htmlUrl = htmlUrl))
        }

        return SearchResponse(false, dummyList, 79693)
    }

    companion object {
        const val USE_DUMMY = false
    }
}