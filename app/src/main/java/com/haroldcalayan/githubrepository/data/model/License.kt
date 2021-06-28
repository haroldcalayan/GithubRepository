package com.haroldcalayan.githubrepository.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class License(
    val key: String?,
    val name: String?,
    @Json(name = "node_id")
    val nodeId: String?,
    @Json(name = "spdx_id")
    val spdxId: String?,
    val url: String?
)