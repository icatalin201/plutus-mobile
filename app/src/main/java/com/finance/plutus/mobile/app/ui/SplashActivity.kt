package com.finance.plutus.mobile.app.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.login.ui.LoginActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onResume() {
        super.onResume()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}