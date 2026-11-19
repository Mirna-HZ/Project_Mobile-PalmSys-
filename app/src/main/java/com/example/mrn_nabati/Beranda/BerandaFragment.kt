package com.example.mrn_nabati.Beranda

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mrn_nabati.ProfilActivity
import com.example.mrn_nabati.R
import com.example.mrn_nabati.WebViewActivity
import com.example.mrn_nabati.databinding.FragmentBerandaBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class BerandaFragment : Fragment() {
    private var _binding: FragmentBerandaBinding? = null
    private val binding get() = _binding!!

    // DATA CONTOH aktivitas terbaru
    private val transaksiList = listOf(
        TransaksiModel("Penjualan TBS", "14 Okt, 1,2 ton", "+Rp3.780.000", true),
        TransaksiModel("Upah pemanen", "14 Okt, 3 orang", "-Rp900.000", false),
        TransaksiModel("Pupuk NPK", "10 Okt, 2 karung", "-Rp760.000", false),
        TransaksiModel("Penjualan TBS", "30 Sep, 1,5 ton", "+Rp4.650.000", true),
        TransaksiModel("Sewa angkutan", "30 Sep, 1 truk", "-Rp350.000", false),
        TransaksiModel("Racun gulma", "25 Sep, 3 liter", "-Rp285.000", false)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBerandaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Beranda"
        }

        val sharedPref = requireContext().getSharedPreferences("user_pref", MODE_PRIVATE)
        binding.textHalo.text = "Halo, " + sharedPref.getString("username", "")

        // --- Menu grid ---
        binding.btnMenuCatat.setOnClickListener { pindahTab(R.id.catat) }
        binding.btnMenuKantong.setOnClickListener { pindahTab(R.id.kantong) }
        binding.btnMenuStatistik.setOnClickListener { pindahTab(R.id.statistik) }
        binding.btnMenuAkun.setOnClickListener { pindahTab(R.id.akun) }

        binding.btnMenuGapki.setOnClickListener {
            startActivity(Intent(requireContext(), WebViewActivity::class.java))
        }

        binding.btnMenuProfil.setOnClickListener {
            startActivity(Intent(requireContext(), ProfilActivity::class.java))
        }

        // --- RecyclerView aktivitas terbaru ---
        val adapter = TransaksiAdapter(transaksiList) { selectedItem ->
            Toast.makeText(
                requireContext(),
                "${selectedItem.judul}: ${selectedItem.nominal}",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.rvAktivitas.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
    }

    // Memilih menu di Bottom Navigation supaya fragment ikut berganti
    private fun pindahTab(idMenu: Int) {
        val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNav.selectedItemId = idMenu
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
