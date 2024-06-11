package alzaky.putra.banksampah.response

import alzaky.putra.banksampah.`object`.PickupRiwayat


data class SumHargaResponse(
    val success: Boolean,
    val status: String,
    val message: String,
    val data: List<PickupRiwayat>
)


