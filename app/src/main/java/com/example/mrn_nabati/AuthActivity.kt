package com.example.mrn_nabati

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mrn_nabati.databinding.ActivityAuthBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Status login (dibaca SplashScreenActivity)
        val sessionPref = getSharedPreferences("user_pref", MODE_PRIVATE)
        // Data hasil registrasi (disimpan RegisterActivity)
        val registerPref = getSharedPreferences("UserPref", MODE_PRIVATE)

        binding.btnRegisterGmail.setOnClickListener {
            startActivity(Intent(this, EmailActivity::class.java))
        }

        binding.btnLogin.setOnClickListener {
            val username = binding.inputUsername.text.toString().trim()
            val password = binding.inputPassword.text.toString()

            val savedUsername = registerPref.getString("username", null)
            val savedPassword = registerPref.getString("password", null)

            val rule1 = username == password
            val rule2 = savedUsername != null &&
                    username == savedUsername &&
                    password == savedPassword

            if (username.isNotEmpty() && (rule1 || rule2)) {
                val editor = sessionPref.edit()
                editor.putBoolean("isLogin", true)
                editor.putString("username", username)
                editor.apply()

                startActivity(Intent(this, BaseActivity::class.java))
                finish()
            } else {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Login Gagal")
                    .setMessage("Username atau password salah.")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }
    }
}