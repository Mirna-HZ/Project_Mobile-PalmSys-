package com.example.mrn_nabati.OnBoarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.example.mrn_nabati.AuthActivity
import com.example.mrn_nabati.databinding.ActivityOnBoardingBinding

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnBoardingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1. Daftar halaman on board
        val fragmentsList = listOf(OnBoarding1Fragment(), OnBoarding2Fragment(), OnBoarding3Fragment())

        // 2. Pasang adapter ke ViewPager2
        val adapter = OnBoardingAdapter(this, fragmentsList)
        binding.viewPagerOnBoarding.adapter = adapter

        // 3. Hubungkan titik indikator dengan ViewPager2
        binding.dotsIndicator.attachTo(binding.viewPagerOnBoarding)

        // 4. Teks tombol berubah menjadi "Mulai" di halaman terakhir
        binding.viewPagerOnBoarding.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == fragmentsList.size - 1) {
                    binding.btnLanjut.text = "Mulai"
                } else {
                    binding.btnLanjut.text = "Lanjut"
                }
            }
        })

        binding.btnLanjut.setOnClickListener {
            val posisi = binding.viewPagerOnBoarding.currentItem
            if (posisi < fragmentsList.size - 1) {
                binding.viewPagerOnBoarding.currentItem = posisi + 1
            } else {
                selesaiOnBoarding()
            }
        }

        binding.btnLewati.setOnClickListener {
            selesaiOnBoarding()
        }
    }

    private fun selesaiOnBoarding() {
        // Simpan tanda bahwa on board sudah dilihat, supaya tidak muncul lagi
        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)
        sharedPref.edit().putBoolean("onboardingSelesai", true).apply()

        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }
}
