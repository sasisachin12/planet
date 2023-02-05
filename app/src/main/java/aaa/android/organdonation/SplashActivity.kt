package aaa.android.organdonation

import aaa.android.organdonation.admin.AdminHomeActivity
import aaa.android.organdonation.databinding.ActivitySpalshBinding
import aaa.android.organdonation.user.UserHomeActivity
import aaa.android.organdonation.util.AppConstant.USER_NAME_KEY
import aaa.android.organdonation.util.AppConstant.USER_PASSWORD_KEY
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySpalshBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContentView(R.layout.activity_spalsh)
        binding = ActivitySpalshBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val action: String? = intent?.action
        val data: Uri? = intent?.data
        val userName = data?.getQueryParameter(USER_NAME_KEY)
        val password = data?.getQueryParameter(USER_PASSWORD_KEY)
        if (userName != null) {
            val intent = Intent(this, UserHomeActivity::class.java)
            intent.putExtra(USER_NAME_KEY, userName.toString())
            intent.putExtra(USER_PASSWORD_KEY, password.toString())
            startActivity(intent)
            finish()
        }
        binding.btnAdmin.setOnClickListener {
            val intent = Intent(this, AdminHomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.btnUser.setOnClickListener {
            val intent = Intent(this, UserHomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}