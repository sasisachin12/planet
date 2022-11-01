package aaa.android.organdonation

import aaa.android.organdonation.databinding.ActivitySpalshBinding
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySpalshBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_spalsh)
        binding = ActivitySpalshBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnAdmin.setOnClickListener {
            val intent = Intent(this, AdminHomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}