package com.haroldcalayan.githubrepository.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.haroldcalayan.githubrepository.R
import com.haroldcalayan.githubrepository.base.BaseViewModel
import com.haroldcalayan.githubrepository.data.repository.GithubRepository
import com.haroldcalayan.githubrepository.data.source.remote.response.SearchResponse
import com.haroldcalayan.githubrepository.util.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val githubRepository: GithubRepository
) : BaseViewModel() {

    private val _searchResponse = MutableLiveData<SearchResponse?>()
    val searchResponse: LiveData<SearchResponse?> = _searchResponse

    private val _keywords = MutableLiveData<List<String>?>()
    val keywords: LiveData<List<String>?> = _keywords

    private val _errorMessageId = MutableLiveData<Int>()
    val errorMessageId: LiveData<Int?> = _errorMessageId

    fun search(keyword: String) {
        if(!isKeywordValid(keyword)) {
            _errorMessageId.postValue(R.string.message_keyword_is_invalid)
            return
        }

        invoke {
            val encodedKeyword = URLEncoder.encode(keyword, Constants.ENCODING_UTF_8)
            val response = githubRepository.searchRepositories(keyword, encodedKeyword)
            _searchResponse.postValue(response)
        }
    }

    fun getKeyWords() {
        invoke {
            _keywords.postValue(githubRepository.getAllKeywords()?.asReversed()?.map { it.keyword }?.distinct())
        }
    }

    private fun isKeywordValid(keyword: String) : Boolean {
        var valid = true
        try {
            URLEncoder.encode(keyword, Constants.ENCODING_UTF_8)
        } catch (e: UnsupportedEncodingException) {
            valid = false
        }
        return valid
    }
}