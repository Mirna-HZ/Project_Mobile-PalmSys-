package com.example.mrn_nabati.Akun

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mrn_nabati.BiometricHelper
import com.example.mrn_nabati.MainReesultActivity
import com.example.mrn_nabati.ProfilActivity
import com.example.mrn_nabati.WebViewActivity
import com.example.mrn_nabati.databinding.FragmentAkunBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
// Pastikan import BiometricHelper disesuaikan jika berada di package lain, misal:
// import com.example.mrn_nabati.helper.BiometricHelper

class AkunFragment : Fragment() {
    private var _binding: FragmentAkunBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAkunBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Akun"
        }

        binding.btnProfil.setOnClickListener {
            startActivity(Intent(requireContext(), ProfilActivity::class.java))
        }

        binding.btnWebView.setOnClickListener {
            startActivity(Intent(requireContext(), WebViewActivity::class.java))
        }

        binding.btnSnack.setOnClickListener {
            startActivity(Intent(requireContext(), MainReesultActivity::class.java))
        }

        val sharedPref = requireContext().getSharedPreferences("user_pref", MODE_PRIVATE)

        // --- Langkah 5: Logika Switch Biometrik ---
        binding.switchBiometric.isChecked = sharedPref.getBoolean("biometricEnabled", false)

        binding.switchBiometric.setOnClickListener {
            val inginAktif = binding.switchBiometric.isChecked

            if (inginAktif) {
                if (!BiometricHelper.isAvailable(requireActivity())) {
                    binding.switchBiometric.isChecked = false
                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Biometrik Belum Tersedia")
                        .setMessage("Daftarkan sidik jari atau kunci layar di Pengaturan perangkat terlebih dahulu.")
                        .setPositiveButton("OK", null)
                        .show()
                } else {
                    BiometricHelper.authenticate(
                        activity = requireActivity(),
                        title = "Aktifkan Kunci Biometrik",
                        subtitle = "Verifikasi untuk mengaktifkan fitur ini",
                        onSuccess = {
                            sharedPref.edit().putBoolean("biometricEnabled", true).apply()
                        },
                        onFailed = {
                            binding.switchBiometric.isChecked = false
                        }
                    )
                }
            } else {
                sharedPref.edit().putBoolean("biometricEnabled", false).apply()
            }
        }
        // ------------------------------------------

        binding.btnLogout.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Keluar")
                .setMessage("Yakin ingin keluar?")
                .setPositiveButton("Ya") { dialog, _ ->
                    val editor = sharedPref.edit()
                    editor.clear()
                    editor.apply()

                    dialog.dismiss()
                    requireActivity().finish()
                }
                .setNegativeButton("Tidak") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}