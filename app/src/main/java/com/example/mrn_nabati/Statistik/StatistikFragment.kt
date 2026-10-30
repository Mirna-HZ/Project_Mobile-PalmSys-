package com.example.mrn_nabati.Statistik

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.mrn_nabati.PdfReportHelper
import com.example.mrn_nabati.TransaksiLaporan
import com.example.mrn_nabati.databinding.FragmentStatistikBinding

class StatistikFragment : Fragment() {
    private var _binding: FragmentStatistikBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStatistikBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Statistik"
        }

        // --- Fitur 2: Ekspor laporan ke PDF ---
        binding.btnUnduhPdf.setOnClickListener {
            // DATA CONTOH: nanti diganti dengan data transaksi asli
            val daftar = listOf(
                TransaksiLaporan("14 Okt", "Penjualan TBS 1,2 ton", 3780000),
                TransaksiLaporan("14 Okt", "Upah pemanen 3 orang", -900000),
                TransaksiLaporan("10 Okt", "Pupuk NPK 2 karung", -760000)
            )

            val file = PdfReportHelper.buatLaporan(requireContext(), daftar)
            val uri = FileProvider.getUriForFile(
                requireContext(), "${requireContext().packageName}.fileprovider", file
            )

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "application/pdf"
                putExtra(Intent.EXTRA_STREAM, uri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            startActivity(Intent.createChooser(intent, "Bagikan laporan"))
        }
        // --------------------------------------
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}