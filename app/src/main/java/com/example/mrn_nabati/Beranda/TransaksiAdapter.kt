package com.example.mrn_nabati.Beranda

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.mrn_nabati.R
import com.example.mrn_nabati.databinding.ItemTransaksiBinding

class TransaksiAdapter(
    private val transaksiList: List<TransaksiModel>,
    private val onItemClick: (TransaksiModel) -> Unit
) : RecyclerView.Adapter<TransaksiAdapter.TransaksiViewHolder>() {

    inner class TransaksiViewHolder(val binding: ItemTransaksiBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransaksiViewHolder {
        val binding = ItemTransaksiBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TransaksiViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TransaksiViewHolder, position: Int) {
        val item = transaksiList[position]
        with(holder.binding) {
            textJudul.text = item.judul
            textKeterangan.text = item.keterangan
            textNominal.text = item.nominal

            // Pemasukan diberi warna ungu, pengeluaran warna teks biasa
            if (item.pemasukan) {
                textNominal.setTextColor(ContextCompat.getColor(root.context, R.color.ungu_aksen))
            } else {
                textNominal.setTextColor(ContextCompat.getColor(root.context, R.color.teks_utama))
            }

            root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    override fun getItemCount(): Int = transaksiList.size
}
