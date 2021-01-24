package com.finance.plutus.mobile.login.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.databinding.ActivityLoginBinding
import com.finance.plutus.mobile.app.ui.MainActivity
import org.koin.android.ext.android.inject

class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by inject()
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer
            binding.loginLoading.visibility = View.INVISIBLE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            }
        })

        binding.loginPasswordEt.apply {
            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        login()
                }
                false
            }
        }
        binding.loginBtn.setOnClickListener {
            login()
        }
    }

    private fun login() {
        binding.loginLoading.visibility = View.VISIBLE
        loginViewModel.login(
            binding.loginUsernameEt.text.toString(),
            binding.loginPasswordEt.text.toString()
        )
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }
}