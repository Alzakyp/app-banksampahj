package alzaky.putra.banksampah

import alzaky.putra.banksampah.database.ApiClient
import alzaky.putra.banksampah.database.ApiService
import alzaky.putra.banksampah.databinding.ActivityLoginBinding
import alzaky.putra.banksampah.model.LoginRequest
import alzaky.putra.banksampah.response.LoginResponse
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)

        setupClickListener()
    }

    private fun setupClickListener() {
        binding.btnLogin.setOnClickListener {
            val username = binding.edtUsername.text.toString()
            val password = binding.edtPassword.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                login(username, password)
            } else {
                Toast.makeText(this, "Username dan Password harus diisi", Toast.LENGTH_SHORT).show()
            }


        }

        binding.tvRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun login(username: String, password: String) {
        val apiService = ApiClient.getClient().create(ApiService::class.java)
        val loginRequest = LoginRequest(username, password)
        val call = apiService.login(loginRequest)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                response.body()?.let { loginResponse ->
                    if (response.isSuccessful && loginResponse.isSuccess) {
                        // Save user data in SharedPreferences
                        val editor = sharedPreferences.edit()
                        editor.putString("id", loginResponse.pelanggan?.id.toString())
                        editor.putString("username", loginResponse.pelanggan?.username)
                        editor.putString("fullname", loginResponse.pelanggan?.fullname)
                        editor.putString("alamat", loginResponse.pelanggan?.alamat)
                        editor.putString("telepon", loginResponse.pelanggan?.telepon)
                        editor.putString("kota", loginResponse.pelanggan?.kota)
                        editor.apply()

                        Toast.makeText(this@LoginActivity, "Login berhasil", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@LoginActivity, LandingActivity::class.java)
                        startActivity(intent)
                        // finish() // Uncomment this line if you want to finish the LoginActivity
                    } else {
                        Toast.makeText(this@LoginActivity, loginResponse.message ?: "Login gagal", Toast.LENGTH_SHORT).show()
                    }
                } ?: run {
                    Toast.makeText(this@LoginActivity, "Response body is null", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("LoginActivity", "Terjadi kesalahan: ${t.message}")
                Toast.makeText(this@LoginActivity, "Terjadi kesalahan: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
