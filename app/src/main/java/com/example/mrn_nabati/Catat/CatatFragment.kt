package com.example.mrn_nabati.Catat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mrn_nabati.databinding.FragmentCatatBinding

class CatatFragment : Fragment() {
    private var _binding: FragmentCatatBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCatatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Catat Transaksi"
        }

        binding.btnPemasukan.setOnClickListener {
            binding.textJenis.text = "Dipilih: Pemasukan"
        }

        binding.btnPengeluaran.setOnClickListener {
            binding.textJenis.text = "Dipilih: Pengeluaran"
        }

        binding.btnSimpan.setOnClickListener {
            val nominal = binding.inputNominal.text.toString()
            if (nominal.isEmpty()) {
                Toast.makeText(requireContext(), "Nominal belum diisi", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Transaksi Rp$nominal disimpan", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
