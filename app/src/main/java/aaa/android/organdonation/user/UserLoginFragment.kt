package aaa.android.organdonation.user

import aaa.android.organdonation.R
import aaa.android.organdonation.databinding.FragmentUserLoginBinding
import aaa.android.organdonation.entity.UserSignUp
import aaa.android.organdonation.util.AppConstant.ADMIN_USER_NAME
import aaa.android.organdonation.util.AppConstant.ADMIN_USER_PASSWORD
import aaa.android.organdonation.util.AppConstant.USER_ID_KEY
import aaa.android.organdonation.util.AppConstant.USER_NAME_KEY
import aaa.android.organdonation.util.AppConstant.USER_PASSWORD_KEY
import aaa.android.organdonation.viewmodel.ExpenseViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController


class UserLoginFragment : Fragment() {

    private var _binding: FragmentUserLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val updateResponse: MutableLiveData<List<UserSignUp>> = MutableLiveData()
    private lateinit var expenseViewModel: ExpenseViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserLoginBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        expenseViewModel = ViewModelProvider(requireActivity())[ExpenseViewModel::class.java]
        arguments?.getString(USER_NAME_KEY)?.let {
            _binding?.edtUserName?.setText(it)
            _binding?.edtUserName?.isEnabled = false
        }
        arguments?.getString(USER_PASSWORD_KEY)?.let {
            _binding?.edtUserPassword?.setText(it)
            _binding?.edtUserPassword?.isEnabled = false
        }

        updateResponse.observe(viewLifecycleOwner) { expenseList ->
            expenseList?.let {
                if (it.isEmpty()) {
                    Toast.makeText(requireContext(), "Invalid Data", Toast.LENGTH_SHORT).show()
                } else {
                    findNavController().navigate(R.id.userHomeFragment)
                }
            }

        }
        _binding?.btnLogin?.setOnClickListener {
            val userName = _binding?.edtUserName?.text.toString().trim()
            val userPassWord = _binding?.edtUserPassword?.text.toString().trim()

            lifecycleScope.launchWhenResumed {
                updateResponse.postValue(
                    expenseViewModel.getuserLoginCheck(
                        userName,
                        userPassWord
                    )
                )
            }


        }
        _binding?.textCreateAccount?.setOnClickListener {

            findNavController().navigate(R.id.userSignUpFragment)


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
       // (activity as AppCompatActivity?)?.supportActionBar?.hide()
    }
}