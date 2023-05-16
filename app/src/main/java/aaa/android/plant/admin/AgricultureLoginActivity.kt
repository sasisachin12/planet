package aaa.android.plant.admin

import aaa.android.plant.databinding.FragmentHomeAgricultureBinding
import aaa.android.plant.databinding.FragmentHomeBinding
import aaa.android.plant.imageupload.AdminMainActivity
import aaa.android.plant.imageupload.ViewAddedMainActivity
import aaa.android.plant.util.AppConstant
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class AgricultureLoginActivity : AppCompatActivity() {
    private lateinit var binding: FragmentHomeAgricultureBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         //setContentView(R.layout.fragment_home)
        binding = FragmentHomeAgricultureBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding?.btnLogin?.setOnClickListener {
            val userName = binding?.edtUserName?.text.toString().trim()
            val userPassWord = binding?.edtUserPassword?.text.toString().trim()
            if (userName.lowercase() == AppConstant.ADMIN_USER_NAME.lowercase() &&
                userPassWord.lowercase() == AppConstant.ADMIN_USER_PASSWORD.lowercase()
            ) {
                val intent = Intent(this, AgricultureMainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid Data", Toast.LENGTH_SHORT).show()
            }


        }
    }
}