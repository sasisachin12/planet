package aaa.android.organdonation.admin

import aaa.android.organdonation.R
import aaa.android.organdonation.databinding.ActivityAdminHomeBinding
import aaa.android.organdonation.databinding.FragmentHomeBinding
import aaa.android.organdonation.util.AppConstant
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.fragment.findNavController

class HospitialLoginActivity : AppCompatActivity() {
    private lateinit var binding: FragmentHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         //setContentView(R.layout.fragment_home)
        binding = FragmentHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding?.btnLogin?.setOnClickListener {
            val userName = binding?.edtUserName?.text.toString().trim()
            val userPassWord = binding?.edtUserPassword?.text.toString().trim()
            if (userName.lowercase() == AppConstant.ADMIN_USER_NAME.lowercase() &&
                userPassWord.lowercase() == AppConstant.ADMIN_USER_PASSWORD.lowercase()
            ) {
                val intent = Intent(this, HospitialHomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid Data", Toast.LENGTH_SHORT).show()
            }


        }
    }
}