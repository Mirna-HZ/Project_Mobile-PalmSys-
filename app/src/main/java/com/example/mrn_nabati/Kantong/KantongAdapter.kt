package com.example.mrn_nabati.Kantong

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import com.example.mrn_nabati.databinding.ItemKantongBinding
import com.google.android.material.snackbar.Snackbar

class KantongAdapter(
    context: Context,
    private val kantongList: List<KantongModel>
) : ArrayAdapter<KantongModel>(context, 0, kantongList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemKantongBinding = ItemKantongBinding.inflate(LayoutInflater.from(context), parent, false)
        val view = binding.root

        val data = kantongList[position]

        binding.textNama.text = data.nama
        binding.textKeterangan.text = data.keterangan
        binding.textSaldo.text = data.saldo
        binding.cardKantong.setCardBackgroundColor(ContextCompat.getColor(context, data.warna))

        view.setOnClickListener {
            Snackbar.make(
                parent,
                "${data.nama}: ${data.saldo}",
                Snackbar.LENGTH_SHORT
            ).show()
        }

        return view
    }
}
