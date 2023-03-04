package aaa.android.organdonation.user

import aaa.android.organdonation.databinding.ActivityUserHomeBinding
import aaa.android.organdonation.databinding.FragmentUserLoginBinding
import aaa.android.organdonation.entity.UserSignUp
import aaa.android.organdonation.util.AppConstant.USER_NAME_KEY
import aaa.android.organdonation.util.AppConstant.USER_PASSWORD_KEY
import aaa.android.organdonation.util.Prefs
import aaa.android.organdonation.viewmodel.ExpenseViewModel
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope


class UserLoginNewActivity : AppCompatActivity() {



    private val updateResponse: MutableLiveData<List<UserSignUp>> = MutableLiveData()
    private lateinit var expenseViewModel: ExpenseViewModel
    private lateinit var binding: FragmentUserLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentUserLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        expenseViewModel = ViewModelProvider(this)[ExpenseViewModel::class.java]
        intent?.getStringExtra(USER_NAME_KEY)?.let {
            binding?.edtUserName?.setText(it)
            binding?.edtUserName?.isEnabled = false
        }
        intent?.getStringExtra(USER_PASSWORD_KEY)?.let {
            binding?.edtUserPassword?.setText(it)
            binding?.edtUserPassword?.isEnabled = false
        }

        updateResponse.observe(this) { expenseList ->
            expenseList?.let {
                if (it.isEmpty()) {
                    Toast.makeText(this, "Invalid Data", Toast.LENGTH_SHORT).show()
                } else {
                    Prefs.set("login_Id",it.first().id)
                    val intent = Intent(this, UserNewHomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

        }
        binding?.btnLogin?.setOnClickListener {
            val userName = binding?.edtUserName?.text.toString().trim()
            val userPassWord = binding?.edtUserPassword?.text.toString().trim()

            lifecycleScope.launchWhenResumed {
                updateResponse.postValue(
                    expenseViewModel.getuserLoginCheck(
                        userName,
                        userPassWord
                    )
                )
            }


        }
        binding?.textCreateAccount?.setOnClickListener {

            val intent = Intent(this, UserSignUpActivity::class.java)
            startActivity(intent)
        }
    }


}