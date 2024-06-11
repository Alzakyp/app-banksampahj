package alzaky.putra.banksampah.response

class PelangganModel(
    val pelanggan: String
){
    data class Data(
        val id: Int,
        val username: String,
        val password: String,
        val fullName: String,
        val alamat: String,
        val telepon: String,
        val kota: String
    )
}
