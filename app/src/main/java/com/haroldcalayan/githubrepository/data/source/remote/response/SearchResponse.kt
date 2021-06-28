package com.haroldcalayan.githubrepository.data.source.remote.response

import com.haroldcalayan.githubrepository.data.model.Item
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SearchResponse(
    @Json(name = "incomplete_results")
    val incompleteResults: Boolean,
    val items: List<Item>,
    @Json(name = "total_count")
    val totalCount: Int
)