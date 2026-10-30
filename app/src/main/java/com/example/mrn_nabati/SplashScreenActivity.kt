package com.example.mrn_nabati

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.mrn_nabati.databinding.ActivitySplashScreenBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val sharedPref = getSharedPreferences("user_pref", MODE_PRIVATE)

        lifecycleScope.launch {
            delay(2000) //simulasi pengambilan data selama 2 detik

            val isLogin = sharedPref.getBoolean("isLogin", false)
            val biometrikAktif = sharedPref.getBoolean("biometricEnabled", false)

            if (isLogin && biometrikAktif && BiometricHelper.isAvailable(this@SplashScreenActivity)) {
                BiometricHelper.authenticate(
                    activity = this@SplashScreenActivity,
                    title = "Verifikasi Identitas",
                    subtitle = "Gunakan sidik jari atau wajah untuk membuka aplikasi",
                    onSuccess = { bukaHalaman(BaseActivity::class.java) },
                    onFailed = { bukaHalaman(AuthActivity::class.java) }
                )
            } else if (isLogin) {
                bukaHalaman(BaseActivity::class.java)
            } else {
                bukaHalaman(AuthActivity::class.java)
            }
        }
    }

    private fun bukaHalaman(tujuan: Class<*>) {
        startActivity(Intent(this, tujuan))
        finish()
    }
}