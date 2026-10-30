package com.example.mrn_nabati

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import android.os.Environment
import java.io.File
import java.io.FileOutputStream
import java.text.NumberFormat
import java.util.Locale

data class TransaksiLaporan(val tanggal: String, val keterangan: String, val jumlah: Long)

object PdfReportHelper {
    private const val LEBAR = 595      // ukuran A4 dalam poin
    private const val TINGGI = 842
    private const val MARGIN = 40f

    private val judul = Paint().apply { textSize = 18f; typeface = Typeface.DEFAULT_BOLD }
    private val teks = Paint().apply { textSize = 12f }
    private val tebal = Paint().apply { textSize = 12f; typeface = Typeface.DEFAULT_BOLD }
    private val kanan = Paint().apply { textSize = 12f; textAlign = Paint.Align.RIGHT }

    private fun rupiah(angka: Long): String {
        val f = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        f.maximumFractionDigits = 0
        return f.format(angka)
    }

    private fun gambarHeader(canvas: Canvas, yAwal: Float): Float {
        var y = yAwal
        canvas.drawText("Tanggal", MARGIN, y, tebal)
        canvas.drawText("Keterangan", MARGIN + 90f, y, tebal)
        canvas.drawText("Jumlah", LEBAR - MARGIN, y, Paint(tebal).apply { textAlign = Paint.Align.RIGHT })
        y += 8f
        canvas.drawLine(MARGIN, y, LEBAR - MARGIN, y, teks)
        return y + 18f
    }

    fun buatLaporan(context: Context, daftar: List<TransaksiLaporan>): File {
        val doc = PdfDocument()
        var nomor = 1
        var page = doc.startPage(PdfDocument.PageInfo.Builder(LEBAR, TINGGI, nomor).create())
        var canvas = page.canvas

        var y = MARGIN + 10f
        canvas.drawText("Laporan Keuangan Kebun", MARGIN, y, judul)
        y += 35f
        y = gambarHeader(canvas, y)

        for (t in daftar) {
            if (y > TINGGI - MARGIN - 60f) {          // halaman penuh, pindah halaman baru
                doc.finishPage(page)
                nomor++
                page = doc.startPage(PdfDocument.PageInfo.Builder(LEBAR, TINGGI, nomor).create())
                canvas = page.canvas
                y = gambarHeader(canvas, MARGIN + 10f)
            }
            canvas.drawText(t.tanggal, MARGIN, y, teks)
            canvas.drawText(t.keterangan.take(32), MARGIN + 90f, y, teks)
            canvas.drawText(rupiah(t.jumlah), LEBAR - MARGIN, y, kanan)
            y += 20f
        }

        val masuk = daftar.filter { it.jumlah > 0 }.sumOf { it.jumlah }
        val keluar = daftar.filter { it.jumlah < 0 }.sumOf { it.jumlah }
        y += 10f
        canvas.drawText("Total pemasukan: ${rupiah(masuk)}", MARGIN, y, tebal)
        y += 18f
        canvas.drawText("Total pengeluaran: ${rupiah(-keluar)}", MARGIN, y, tebal)
        y += 18f
        canvas.drawText("Saldo bersih: ${rupiah(masuk + keluar)}", MARGIN, y, tebal)
        doc.finishPage(page)

        val folder = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        val file = File(folder, "laporan_keuangan.pdf")
        FileOutputStream(file).use { doc.writeTo(it) }
        doc.close()
        return file
    }
}