package com.example.mrn_nabati.Kantong

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mrn_nabati.R
import com.example.mrn_nabati.databinding.FragmentKantongBinding

class KantongFragment : Fragment() {
    private var _binding: FragmentKantongBinding? = null
    private val binding get() = _binding!!

    // DATA CONTOH kantong (total Rp12.450.000)
    private val kantongList = listOf(
        KantongModel("Kantong Panen", "Hasil jual TBS", "Rp8.200.000", R.color.plum_utama),
        KantongModel("Kantong Pupuk", "Untuk pemupukan berikutnya", "Rp2.750.000", R.color.ungu_aksen),
        KantongModel("Kantong Upah", "Gaji pemanen dan perawatan", "Rp1.500.000", R.color.lilac)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentKantongBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Kantong"
        }

        // Pasang CustomAdapter ke ListView
        val adapter = KantongAdapter(context = requireContext(), kantongList = kantongList)
        binding.listKantong.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
