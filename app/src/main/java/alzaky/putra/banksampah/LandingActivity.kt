package alzaky.putra.banksampah

import alzaky.putra.banksampah.databinding.ActivityLandingBinding
import alzaky.putra.banksampah.databinding.ContentMainBinding
import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import im.delight.android.location.SimpleLocation
import java.io.IOException
import java.util.Locale

class LandingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLandingBinding
    private lateinit var simpleLocation: SimpleLocation

    private var strCurrentLatitude: Double = 0.0
    private var strCurrentLongitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLandingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val fullname = sharedPreferences.getString("fullname", "")

        binding.NamaPelanggan.text = fullname

        setPermission()
        findViewById<View>(R.id.cvJemputSampah).setOnClickListener {
            navigateToJemputSampah()
        }
        findViewById<View>(R.id.cvEditProfile).setOnClickListener {
            navigateToProfile()
        }
        findViewById<View>(R.id.cvHistory).setOnClickListener { v: View? ->
            navigateToRiwayat()
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh the name of the customer
        val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
        val fullname = sharedPreferences.getString("fullname", "")
        binding.NamaPelanggan.text = fullname
    }

    private fun setLocation() {
        simpleLocation = SimpleLocation(this)
        if (!simpleLocation.hasLocationEnabled()) {
            SimpleLocation.openSettings(this)
        }

        // Get location
        strCurrentLatitude = simpleLocation.latitude
        strCurrentLongitude = simpleLocation.longitude

        Log.d(TAG, "Current Latitude: $strCurrentLatitude, Longitude: $strCurrentLongitude")

        // Set current location
        setCurrentLocation()
    }

    private fun setPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQ_PERMISSION)
        } else {
            setLocation()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQ_PERMISSION) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                setLocation()
            } else {
                Log.e(TAG, "Permission denied by user.")
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setCurrentLocation() {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addressList = geocoder.getFromLocation(strCurrentLatitude, strCurrentLongitude, 1)
            if (addressList != null && addressList.isNotEmpty()) {
                val strCurrentLocation = addressList[0].locality
                val tvCurrentLocation = findViewById<TextView>(R.id.tvCurrentLocation)
                tvCurrentLocation.text = strCurrentLocation
                tvCurrentLocation.isSelected = true
                Log.d(TAG, "Current Location: $strCurrentLocation")
            }
        } catch (e: IOException) {
            Log.e(TAG, "Geocoder error: ", e)
            e.printStackTrace()
        }
    }

    private fun navigateToJemputSampah() {
        val intent = Intent(this, JemputSampahActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToProfile() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToRiwayat() {
        val intent = Intent(this, RiwayatActivity::class.java)
        startActivity(intent)
    }

    companion object {
        private const val REQ_PERMISSION = 1001
        private const val TAG = "LandingActivity"
    }
}
