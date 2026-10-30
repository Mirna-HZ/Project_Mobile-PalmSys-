package com.example.mrn_nabati

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.mrn_nabati.databinding.ActivityEmailBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class EmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        binding.btnLanjut.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val domain = "@gmail.com"

            if (email.isEmpty()) {
                tampilkanError("Email tidak boleh kosong")
            } else if (!email.lowercase().endsWith(domain) || email.length <= domain.length) {
                tampilkanError("Email harus menggunakan domain @gmail.com")
            } else {
                val intent = Intent(this, RegisterActivity::class.java)
                intent.putExtra("email", email)
                startActivity(intent)
            }
        }
    }

    private fun tampilkanError(pesan: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Email Tidak Valid")
            .setMessage(pesan)
            .setPositiveButton("OK", null)
            .show()
    }
}