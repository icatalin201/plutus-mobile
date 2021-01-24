package com.finance.plutus.mobile.app.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.login.ui.LoginActivity
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        viewModel.result.observe(this) {
            if (it.isLoggedIn) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }
    }
}