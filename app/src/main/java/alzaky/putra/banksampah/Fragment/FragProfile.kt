
package alzaky.putra.banksampah.Fragment

import alzaky.putra.banksampah.databinding.ActivityProfileBinding
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class FragProfile : Fragment() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ActivityProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = requireActivity().getSharedPreferences("user_prefs", Context.MODE_PRIVATE)

        // Retrieve user data from SharedPreferences
        val fullname = sharedPreferences.getString("fullname", "")
        val alamat = sharedPreferences.getString("alamat", "")
        val telepon = sharedPreferences.getString("telepon", "")
        val kota = sharedPreferences.getString("kota", "")

        // Set data to EditTexts
        binding.edtFullname.setText(fullname)
        binding.edtAlamat.setText(alamat)
        binding.edtTelepon.setText(telepon)
        binding.edtKota.setText(kota)
    }
}
