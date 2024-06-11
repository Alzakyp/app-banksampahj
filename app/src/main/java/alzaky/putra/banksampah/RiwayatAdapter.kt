package alzaky.putra.banksampah

import alzaky.putra.banksampah.`object`.PickupRiwayat
import alzaky.putra.banksampah.`object`.TipeProduk
import alzaky.putra.banksampah.databinding.ListItemRiwayatBinding
import alzaky.putra.banksampah.utils.FunctionHelper
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Calendar

class RiwayatAdapter(
    private val mContext: Context,
    private val modelInputList: MutableList<PickupRiwayat>,
    private val adapterCallback: RiwayatAdapterCallback,
    private val typeProductList: List<TipeProduk>
) : RecyclerView.Adapter<RiwayatAdapter.ViewHolder>() {

    private var modelDatabase: MutableList<PickupRiwayat> = modelInputList

    fun setDataAdapter(items: List<PickupRiwayat>) {
        modelDatabase.clear()
        modelDatabase.addAll(items)
        notifyDataSetChanged()
        Log.d(TAG, "Data successfully set in adapter: Size ${items.size}")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemRiwayatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            val data: PickupRiwayat = modelDatabase[position]
            holder.bind(data)
        } catch (e: Exception) {
            Log.e(TAG, "Error binding data at position $position: ${e.message}", e)
        }
    }

    override fun getItemCount(): Int {
        return modelDatabase.size
    }

    inner class ViewHolder(private val binding: ListItemRiwayatBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: PickupRiwayat) {
            binding.apply {
                tvNama.text = data.fullname
                tvDate.text = formatDate(data.tanggal_penjemputan)
                tvKategori.text = "Sampah " + getProductName(data.tipe)
                tvBerat.text = "Berat : " + data.berat.toString() + " Kg"
                tvSaldo.text = "Pendapatan : " + FunctionHelper.rupiahFormat(data.pendapatan)

                if (data.berat < 5) {
                    tvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.red_500))
                    tvStatus.text = "Masih dalam proses"
                } else {
                    tvStatus.setTextColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
                    tvStatus.text = "Sudah di konfirmasi"
                }
            }
        }

        private fun formatDate(dateString: String): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val calendar = Calendar.getInstance()
            calendar.time = sdf.parse(dateString)
            val dateFormat = SimpleDateFormat("dd-MM-yyyy")
            return dateFormat.format(calendar.time)
        }

        private fun getProductName(productType: Int): String {
            for (product in typeProductList) {
                if (product.id == productType) {
                    return product.name
                }
            }
            return "Tipe Produk Tidak Ditemukan"
        }
    }

    interface RiwayatAdapterCallback {
        fun onDelete(modelDatabase: PickupRiwayat?)
    }

    companion object {
        private const val TAG = "RiwayatAdapter"
    }
}
