package com.haroldcalayan.githubrepository.ui.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import com.haroldcalayan.githubrepository.R
import com.haroldcalayan.githubrepository.base.BaseActivity
import com.haroldcalayan.githubrepository.data.model.Item
import com.haroldcalayan.githubrepository.databinding.ActivityMainBinding
import com.haroldcalayan.githubrepository.util.hideKeyboard
import com.haroldcalayan.githubrepository.util.showToastShort
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>(), RepositoryAdapter.RepositoryAdapterListener {

    override val layoutId = R.layout.activity_main
    override val viewModel: MainViewModel by viewModels()

    private lateinit var repositoryAdapter: RepositoryAdapter
    private lateinit var keywordAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        observe()
        viewModel.getKeyWords()
    }

    override fun initViews() {
        super.initViews()
        initRepositoryList()
        initKeywordList()

        binding.edittextKeyword.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val keyword = v.text.toString()
                if (keyword.isEmpty()) {
                    showToastShort(R.string.message_keyword_must_not_empty)
                } else {
                    viewModel.search(keyword)
                }

                hideKeyboard()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
    }

    override fun observe() {
        super.observe()
        viewModel.searchResponse.observe(this, {
            repositoryAdapter.updateData(it?.items ?: emptyList())
        })
        viewModel.keywords.observe(this, {
            val newList = ArrayList<String>(it)
            keywordAdapter.clear()
            keywordAdapter.addAll(newList)
        })
        viewModel.errorMessageId.observe(this, {
            showToastShort(it ?: R.string.message_generic_error)
        })
    }

    override fun onItemClick(item: Item) {
        if (item.htmlUrl.isNullOrEmpty()) {
            showToastShort(R.string.message_repository_url_is_empty)
            return
        }
        openUrlToBrowser(item.htmlUrl)
    }

    private fun initRepositoryList() {
        repositoryAdapter = RepositoryAdapter(emptyList(), this)
        binding.recyclerviewRepositories.adapter = repositoryAdapter
    }

    private fun initKeywordList() {
        keywordAdapter = ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, ArrayList<String>())
        keywordAdapter.setNotifyOnChange(true)
        binding.edittextKeyword.setAdapter(keywordAdapter)
    }

    private fun openUrlToBrowser(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }
}