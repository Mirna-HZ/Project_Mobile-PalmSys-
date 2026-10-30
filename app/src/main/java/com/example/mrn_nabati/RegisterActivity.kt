package com.example.mrn_nabati

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mrn_nabati.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        // Email terbawa dari halaman input email
        val email = intent.getStringExtra("email") ?: ""
        binding.etEmail.setText(email)

        binding.btnDaftar.setOnClickListener {
            val nama = binding.etNama.text.toString().trim()
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()

            if (nama.isEmpty()) {
                binding.etNama.error = "Nama wajib diisi"
            } else if (username.isEmpty()) {
                binding.etUsername.error = "Username wajib diisi"
            } else if (username.contains(" ")) {
                binding.etUsername.error = "Username tidak boleh mengandung spasi"
            } else if (password.isEmpty()) {
                binding.etPassword.error = "Password wajib diisi"
            } else if (password.length < 6) {
                binding.etPassword.error = "Password minimal 6 karakter"
            } else {
                val sharedPref = getSharedPreferences("UserPref", MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putString("nama", nama)
                editor.putString("email", email)
                editor.putString("username", username)
                editor.putString("password", password)
                editor.apply()

                val intent = Intent(this, RegisterSuccessActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}