package com.haroldcalayan.githubrepository.ui.splash

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.haroldcalayan.githubrepository.R
import com.haroldcalayan.githubrepository.base.BaseActivity
import com.haroldcalayan.githubrepository.databinding.ActivitySplashBinding
import com.haroldcalayan.githubrepository.ui.main.MainActivity
import com.haroldcalayan.githubrepository.util.Constants
import com.haroldcalayan.githubrepository.util.showToastShort
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*

@AndroidEntryPoint
class SplashActivity : BaseActivity<SplashViewModel, ActivitySplashBinding>() {

    override val layoutId = R.layout.activity_splash
    override val viewModel: SplashViewModel by viewModels()

    private val activityScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        observe()
    }

    override fun onResume() {
        super.onResume()
        initPermissions()
    }

    override fun onPause() {
        activityScope.cancel()
        super.onPause()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    initPermissions()
                } else {
                    showToastShort(R.string.message_app_requires_permission)
                }
                return
            }
        }
    }

    override fun initViews() {
        super.initViews()
        hideSystemUI()
    }

    private fun openMainScreen() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun initPermissions() {
        if (areAllPermissionsGranted()) {
            startTimer()
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val permissions = arrayOf(
                    Manifest.permission.INTERNET
                )
                requestPermissions(permissions, PERMISSION_REQUEST_CODE)
            }
        }
    }

    private fun startTimer() {
        activityScope.launch {
            delay(Constants.SPLASH_SCREEN_LIFE)
            finish()
            openMainScreen()
        }
    }

    private fun areAllPermissionsGranted(): Boolean {
        return (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET)
                == PackageManager.PERMISSION_GRANTED)
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 10
    }
}