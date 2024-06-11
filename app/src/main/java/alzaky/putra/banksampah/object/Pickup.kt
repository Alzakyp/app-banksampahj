package alzaky.putra.banksampah.`object`

data class Pickup(
    val id: Int,
    val fullname: String,
    val tipe: Int,
    val berat: Int,
    val harga: Int,
    val tanggal_penjemputan: String,
    val alamat: String,
    val catatan: String
)
