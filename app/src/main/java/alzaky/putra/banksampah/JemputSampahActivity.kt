package alzaky.putra.banksampah

import alzaky.putra.banksampah.database.ApiClient
import alzaky.putra.banksampah.database.ApiService
import alzaky.putra.banksampah.databinding.ActivityJemputsampahBinding
import alzaky.putra.banksampah.model.PickupRequest
import alzaky.putra.banksampah.response.PickupResponse
import alzaky.putra.banksampah.response.TypeProductResponse
import alzaky.putra.banksampah.`object`.TipeProduk
import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar
import android.content.Context

class JemputSampahActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJemputsampahBinding
    private lateinit var apiService: ApiService
    private var productTypes: List<TipeProduk>? = null
    private val hargaPerKg = 3000 // Harga per kilogram

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJemputsampahBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiService = ApiClient.getClient().create(ApiService::class.java)
        setupClickListener()

        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val fullname = sharedPreferences.getString("fullname", "")
        binding.edtNp.setText(fullname)
        binding.edtNp.isEnabled = false


        binding.edtTanggal.setOnClickListener {
            // Get the current date
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            // Create a new DatePickerDialog
            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                // The month is 0-indexed, so add 1 to get the correct month
                val selectedDate = "${selectedDay}/${selectedMonth + 1}/${selectedYear}"

                // Set the selected date in the edt_tanggal EditText
                binding.edtTanggal.setText(selectedDate)
            }, year, month, day)

            // Show the DatePickerDialog
            datePickerDialog.show()
        }

        apiService.getTypeProducts().enqueue(object : Callback<TypeProductResponse> {
            override fun onResponse(call: Call<TypeProductResponse>, response: Response<TypeProductResponse>) {
                if (response.isSuccessful) {
                    val typeProductResponse = response.body()
                    if (typeProductResponse != null && typeProductResponse.success) {
                        productTypes = typeProductResponse.data

                        // Extract the product type names and convert them into an array
                        val productTypeNames = productTypes?.map { it.name }?.toTypedArray()
                        if (productTypeNames != null) {
                            // Create an ArrayAdapter with the product type names and set it to the Spinner
                            val adapter = ArrayAdapter(this@JemputSampahActivity, android.R.layout.simple_spinner_item, productTypeNames)
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                            binding.spKategori.adapter = adapter
                        }
                    } else {
                        Toast.makeText(this@JemputSampahActivity, "Failed to fetch product types: " + typeProductResponse?.message, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@JemputSampahActivity, "Failed to fetch product types: " + response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<TypeProductResponse>, t: Throwable) {
                Toast.makeText(this@JemputSampahActivity, "Failed to fetch product types: " + t.message, Toast.LENGTH_SHORT).show()
                Log.e("JemputSampahActivity", "Error fetching product types", t)
            }
        })

        // Add TextWatcher to edtBerat
        binding.edtBerat.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val beratStr = s.toString().trim()
                if (beratStr.isNotEmpty()) {
                    try {
                        val berat = beratStr.toInt()
                        val harga = berat * hargaPerKg
                        binding.edtHarga.setText(harga.toString())
                    } catch (e: NumberFormatException) {
                        binding.edtHarga.setText("")
                    }
                } else {
                    binding.edtHarga.setText("")
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupClickListener() {
        binding.btnJemput.setOnClickListener {
            val fullname = binding.edtNp.text.toString().trim()
            val beratStr = binding.edtBerat.text.toString().trim()
            val hargaStr = binding.edtHarga.text.toString().trim()
            val tanggal_penjemputan = binding.edtTanggal.text.toString().trim()
            val alamat = binding.edtAlamat.text.toString().trim()
            val catatan = binding.edtCatatan.text.toString().trim()

            // Get the selected TipeProduk object
            val selectedTypeProduct = productTypes?.get(binding.spKategori.selectedItemPosition)

            if (fullname.isNotEmpty() && beratStr.isNotEmpty() && hargaStr.isNotEmpty() &&
                tanggal_penjemputan.isNotEmpty() && alamat.isNotEmpty() && catatan.isNotEmpty() && selectedTypeProduct != null
            ) {
                try {
                    val berat = beratStr.toInt()
                    val harga = hargaStr.toInt()
                    // Use the id of the selected TipeProduk object
                    submitPickupRequest(fullname, selectedTypeProduct.id, berat, harga, tanggal_penjemputan, alamat, catatan)
                } catch (e: NumberFormatException) {
                    Toast.makeText(this, "Berat dan Harga harus berupa angka", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Semua kolom harus diisi", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun submitPickupRequest(fullname: String, tipe: Int, berat: Int, harga: Int, tanggal_penjemputan: String, alamat: String, catatan: String) {
        val pickupRequest = PickupRequest(fullname, tipe, berat, harga, tanggal_penjemputan, alamat, catatan)
        val call = apiService.createPickup(pickupRequest)

        call.enqueue(object : Callback<PickupResponse> {
            override fun onResponse(call: Call<PickupResponse>, response: Response<PickupResponse>) {
                if (response.isSuccessful) {
                    val pickupResponse = response.body()
                    Toast.makeText(this@JemputSampahActivity, "Success: " + pickupResponse?.message, Toast.LENGTH_SHORT).show()
                } else {
                    // Extract the error message from the server response
                    val errorMessage = response.errorBody()?.string()
                    Toast.makeText(this@JemputSampahActivity, "Request failed: " + (errorMessage ?: response.message()), Toast.LENGTH_SHORT).show()
                    Log.e("FragJemput", "Request failed: " + (errorMessage ?: response.message()))
                }
            }

            override fun onFailure(call: Call<PickupResponse>, t: Throwable) {
                Toast.makeText(this@JemputSampahActivity, "Request failed: " + t.message, Toast.LENGTH_SHORT).show()
                Log.e("FragJemput", "Request failed", t)
            }
        })
    }


}