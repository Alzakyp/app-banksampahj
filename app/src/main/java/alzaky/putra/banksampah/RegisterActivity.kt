package alzaky.putra.banksampah

import alzaky.putra.banksampah.database.ApiClient
import alzaky.putra.banksampah.database.ApiService
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import alzaky.putra.banksampah.databinding.ActivityRegisterBinding
import alzaky.putra.banksampah.model.RegisterRequest
import alzaky.putra.banksampah.response.RegisterResponse
import android.content.Intent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        apiService = ApiClient.getClient().create(ApiService::class.java)
        setupClickListener()
    }

    private fun setupClickListener() {
        binding.btnRegister.setOnClickListener {
            val username = binding.edtUsername.text.toString()
            val password = binding.edtPassword.text.toString()
            val confirmPassword = binding.edtRpaswword.text.toString()
            val fullname = binding.edtFullname.text.toString()
            val alamat = binding.edtAlamat.text.toString()
            val telepon = binding.edtTelepon.text.toString()
            val kota = binding.edtKota.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty() &&
                fullname.isNotEmpty() && alamat.isNotEmpty() && telepon.isNotEmpty() && kota.isNotEmpty()
            ) {
                if (password == confirmPassword) {
                    register(username, password, fullname, alamat, telepon, kota)
                } else {
                    Toast.makeText(this, "Password tidak sama", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Semua kolom harus diisi", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun register(username: String, password: String, fullname: String, alamat: String, telepon: String, kota: String) {
        val registerRequest = RegisterRequest(username, password, fullname, alamat, telepon, kota)
        val call = apiService.register(registerRequest)

        call.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful && response.body()?.isSuccess == true) {
                    // Register berhasil, lanjutkan ke LoginActivity
                    Toast.makeText(this@RegisterActivity, "Register berhasil", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Register gagal, tampilkan pesan kesalahan
                    Toast.makeText(this@RegisterActivity, response.body()?.message ?: "Register gagal", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                // Kesalahan jaringan atau server
                Toast.makeText(this@RegisterActivity, "Terjadi kesalahan: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
