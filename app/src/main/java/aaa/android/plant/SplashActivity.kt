package aaa.android.plant

import aaa.android.plant.admin.AdminLoginActivity
import aaa.android.plant.databinding.ActivitySpalshBinding
import aaa.android.plant.imageupload.MainActivity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySpalshBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySpalshBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.btnAdmin.setOnClickListener {
            val intent = Intent(this, AdminLoginActivity::class.java)
            startActivity(intent)

        }


        binding.btnUser.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)

        }
    }
}