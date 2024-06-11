package alzaky.putra.banksampah

import alzaky.putra.banksampah.database.ApiClient
import alzaky.putra.banksampah.database.ApiService
import alzaky.putra.banksampah.`object`.PickupRiwayat
import alzaky.putra.banksampah.`object`.TipeProduk
import alzaky.putra.banksampah.databinding.ActivityRiwayatBinding
import alzaky.putra.banksampah.response.SumHargaResponse
import alzaky.putra.banksampah.response.TypeProductResponse
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RiwayatActivity : AppCompatActivity(), RiwayatAdapter.RiwayatAdapterCallback {

    private lateinit var binding: ActivityRiwayatBinding
    private lateinit var modelDatabaseList: MutableList<PickupRiwayat>
    private lateinit var riwayatAdapter: RiwayatAdapter
    private lateinit var typeProductList: List<TipeProduk>
    private var totalSaldo: Int = 0 // Variabel untuk menyimpan total saldo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRiwayatBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setStatusBar()
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
        fetchTypeProducts()
    }

    private fun fetchTypeProducts() {
        val apiService = ApiClient.getClient().create(ApiService::class.java)
        apiService.getTypeProducts().enqueue(object : Callback<TypeProductResponse> {
            override fun onResponse(
                call: Call<TypeProductResponse>,
                response: Response<TypeProductResponse>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.isSuccess()) {
                            typeProductList = it.data
                            initViews()
                        }
                    }
                } else {
                    Toast.makeText(this@RiwayatActivity, "Failed to fetch product types", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<TypeProductResponse>, t: Throwable) {
                Toast.makeText(this@RiwayatActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun initViews() {
        binding.tvNotFound.visibility = View.GONE
        modelDatabaseList = ArrayList()
        riwayatAdapter = RiwayatAdapter(this, modelDatabaseList, this, typeProductList)
        binding.rvHistory.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@RiwayatActivity)
            adapter = riwayatAdapter
        }
        fetchData()
    }

    private fun fetchData() {
        // Ambil fullname pengguna yang sedang login dari SharedPreferences
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val currentUserFullName = sharedPreferences.getString("fullname", "") ?: ""

        // Fetch data and set it to adapter
        val apiService = ApiClient.getClient().create(ApiService::class.java)
        apiService.getSumHarga().enqueue(object : Callback<SumHargaResponse> {
            override fun onResponse(call: Call<SumHargaResponse>, response: Response<SumHargaResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.status == "success") {
                            // Filter data berdasarkan fullname pengguna yang sedang login
                            val filteredData = it.data.filter { pickup -> pickup.fullname == currentUserFullName }
                            if (filteredData.isNotEmpty()) {
                                riwayatAdapter.setDataAdapter(filteredData)
                                calculateTotalSaldo(filteredData) // Hitung total saldo
                                binding.tvNotFound.visibility = View.GONE
                            } else {
                                binding.tvNotFound.visibility = View.VISIBLE
                            }
                        } else {
                            binding.tvNotFound.visibility = View.VISIBLE
                        }
                    }
                }
            }

            override fun onFailure(call: Call<SumHargaResponse>, t: Throwable) {
                Toast.makeText(this@RiwayatActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun calculateTotalSaldo(data: List<PickupRiwayat>) {
        totalSaldo = data.sumOf { it.pendapatan.toInt() } // Hitung total saldo
        binding.tvSaldo.text = "Rp. ${totalSaldo}" // Tampilkan total saldo di TextView
    }

    private fun setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        if (Build.VERSION.SDK_INT >= 21) {
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    override fun onDelete(modelDatabase: PickupRiwayat?) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("Hapus riwayat ini?")
        alertDialogBuilder.setPositiveButton("Ya, Hapus") { dialogInterface: DialogInterface?, i: Int ->
            val uid = modelDatabase?.id
            // Lakukan operasi penghapusan data di sini
            uid?.let {
                try {
                    // Hapus data dari modelDatabaseList
                    modelDatabaseList.removeIf { pickup -> pickup.id == it }
                    riwayatAdapter.setDataAdapter(modelDatabaseList)
                    calculateTotalSaldo(modelDatabaseList) // Hitung ulang total saldo setelah data dihapus
                    Toast.makeText(this@RiwayatActivity, "Data yang dipilih sudah dihapus", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Log.e(TAG, "Error while deleting data: ${e.message}", e)
                    Toast.makeText(this@RiwayatActivity, "Gagal menghapus data", Toast.LENGTH_SHORT).show()
                }
            }
        }

        alertDialogBuilder.setNegativeButton("Batal") { dialogInterface: DialogInterface, i: Int -> dialogInterface.cancel() }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        // Ketika activity di-resume, refresh data riwayat dengan nama pengguna terbaru
        fetchData()
    }

    companion object {
        private const val TAG = "RiwayatActivity"
    }
}
