package com.haroldcalayan.githubrepository.data.source.remote.service

import com.haroldcalayan.githubrepository.data.source.remote.response.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApiService {

    @GET("search/repositories")
    suspend fun searchRepositories(@Query("q") query: String): SearchResponse?
}