package com.haroldcalayan.githubrepository.base

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : BaseViewModel, B : ViewDataBinding> : AppCompatActivity() {

    abstract val layoutId: Int
    abstract val viewModel: T
    lateinit var binding: B

    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)

        progressDialog = ProgressDialog(this)
    }

    override fun onPause() {
        super.onPause()
        dismissProgressDialog()
    }

    fun showProgressDialog(cancelTouchOutside: Boolean? = true) {
        if (!progressDialog.isShowing) {
            progressDialog.setCanceledOnTouchOutside(cancelTouchOutside ?: true)
            progressDialog.show()
        }
    }

    fun dismissProgressDialog() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    fun hideSystemUI() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                or View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                or View.SYSTEM_UI_FLAG_IMMERSIVE)
    }

    fun back() {
        onBackPressed()
    }

    fun close() {
        finish()
    }

    open fun initViews() {
    }

    open fun observe() {
        viewModel.showProgressBar.observe(this, {
            if (it == true) showProgressDialog()
            else dismissProgressDialog()
        })
    }
}