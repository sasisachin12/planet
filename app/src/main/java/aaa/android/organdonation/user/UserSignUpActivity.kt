package aaa.android.organdonation.user

import aaa.android.organdonation.R
import aaa.android.organdonation.SplashActivity
import aaa.android.organdonation.databinding.FragmentUserSignUpBinding
import aaa.android.organdonation.entity.UserSignUp
import aaa.android.organdonation.util.utils.getRandomString
import aaa.android.organdonation.viewmodel.ExpenseViewModel
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope


class UserSignUpActivity : AppCompatActivity() {


    private lateinit var binding: FragmentUserSignUpBinding

    private lateinit var expenseViewModel: ExpenseViewModel

    private val insertResponse: MutableLiveData<Long> = MutableLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = FragmentUserSignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        expenseViewModel = ViewModelProvider(this)[ExpenseViewModel::class.java]

        insertResponse.observe(
            this
        ) {
            Toast.makeText(
                this,
                R.string.added_success_msg,
                Toast.LENGTH_LONG
            ).show()
            binding?.edtUserName?.text?.clear()
            binding?.edtUserPhone?.text?.clear()
            binding?.edtUserEmail?.text?.clear()
            val intent = Intent(this, SplashActivity::class.java)
            startActivity(intent)

        }
        binding?.btnLogin?.setOnClickListener {
            val userName = binding?.edtUserName?.text.toString().trim()
            val userMobile = binding?.edtUserPhone?.text.toString().trim()
            val userEmail = binding?.edtUserEmail?.text.toString().trim()
            val userpassword = getRandomString(8)
            // findNavController().navigate(R.id.userHomeFragment)
            val userSignUp = UserSignUp(
                null, userName, userEmail, userMobile, userpassword, "PV"
            )

            lifecycleScope.launchWhenResumed {

                insertResponse.postValue(expenseViewModel.insertSignUp(userSignUp))
            }

        }
    }


}