package com.example.mrn_nabati

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.mrn_nabati.Akun.AkunFragment
import com.example.mrn_nabati.Beranda.BerandaFragment
import com.example.mrn_nabati.Catat.CatatFragment
import com.example.mrn_nabati.Kantong.KantongFragment
import com.example.mrn_nabati.Statistik.StatistikFragment
import com.example.mrn_nabati.databinding.ActivityBaseBinding

class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        // Halaman pertama yang tampil adalah Beranda
        replaceFragment(BerandaFragment())

        binding.bottomNavView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.beranda -> {
                    replaceFragment(BerandaFragment())
                    true
                }
                R.id.statistik -> {
                    replaceFragment(StatistikFragment())
                    true
                }
                R.id.catat -> {
                    replaceFragment(CatatFragment())
                    true
                }
                R.id.kantong -> {
                    replaceFragment(KantongFragment())
                    true
                }
                R.id.akun -> {
                    replaceFragment(AkunFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            //.addToBackStack(null) -> dimatikan supaya tombol back langsung keluar aplikasi
            .commit()
    }
}
