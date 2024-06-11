package alzaky.putra.banksampah

import alzaky.putra.banksampah.databinding.ActivityProfileBinding
import alzaky.putra.banksampah.database.ApiClient
import alzaky.putra.banksampah.database.ApiService
import alzaky.putra.banksampah.model.PelanganModel
import alzaky.putra.banksampah.response.PelangganModel
import alzaky.putra.banksampah.response.PelangganResponse
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val TAG = "ProfileActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val id = sharedPreferences.getString("id", "")?.toIntOrNull() ?: 0
        val fullname = sharedPreferences.getString("fullname", "")
        val alamat = sharedPreferences.getString("alamat", "")
        val telepon = sharedPreferences.getString("telepon", "")
        val kota = sharedPreferences.getString("kota", "")

        binding.edtFullname.setText(fullname)
        binding.edtAlamat.setText(alamat)
        binding.edtTelepon.setText(telepon)
        binding.edtKota.setText(kota)

        binding.btnSimpan.setOnClickListener {
            val updatedFullname = binding.edtFullname.text.toString()
            val updatedAlamat = binding.edtAlamat.text.toString()
            val updatedTelepon = binding.edtTelepon.text.toString()
            val updatedKota = binding.edtKota.text.toString()

            val pelanganModel = PelanganModel(updatedFullname, updatedAlamat, updatedTelepon, updatedKota)
            val apiService = ApiClient.getClient().create(ApiService::class.java)
            val call = apiService.updatePelanggan(id, pelanganModel)

            Log.d(TAG, "Updating pelanggan with id: $id")
            Log.d(TAG, "PelangganModel: $pelanganModel")

            call.enqueue(object : Callback<PelangganResponse> {
                override fun onResponse(call: Call<PelangganResponse>, response: Response<PelangganResponse>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@ProfileActivity, "Update successful", Toast.LENGTH_SHORT).show()
                    } else {
                        Log.e(TAG, "Update failed: ${response.errorBody()?.string()}")
                        Toast.makeText(this@ProfileActivity, "Update failed", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<PelangganResponse>, t: Throwable) {
                    Log.e(TAG, "An error occurred", t)
                    Toast.makeText(this@ProfileActivity, "An error occurred", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
