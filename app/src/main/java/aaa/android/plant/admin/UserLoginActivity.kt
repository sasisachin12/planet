package aaa.android.plant.admin

import aaa.android.plant.databinding.FragmentUserLoginBinding
import aaa.android.plant.imageupload.MainActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class UserLoginActivity : AppCompatActivity() {
    private lateinit var binding: FragmentUserLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.fragment_home)
        binding = FragmentUserLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding?.btnLogin?.setOnClickListener {
            val userName = binding?.edtUserName?.text.toString().trim()
            val userPassWord = binding?.edtUserPassword?.text.toString().trim()


            val sharedPreferences = getSharedPreferences("LoginInfo", MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("userName", userName)
            editor.putString("password", userName)
            editor.apply()


            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()


        }
    }

    fun callAPIDemo(mobile: String, message: String) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url =
            "http://smsserver9.creativepoint.in/api.php?username=fantasy&password=596692&to=$mobile&from=FSSMSS&message=Dear user  your msg is $message  Sent By FSMSG FSSMSS&PEID=1501563800000030506&templateid=1507162882948811640"
        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                Toast.makeText(this, "" + response, Toast.LENGTH_SHORT).show()
                // Display the first 500 characters of the response string.
                // textView.text = "Response is: ${response.substring(0, 500)}"
            },
            Response.ErrorListener {
                //textView.text = "That didn't work!"
                Toast.makeText(this, "" + it.message, Toast.LENGTH_SHORT).show()
            })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }
}