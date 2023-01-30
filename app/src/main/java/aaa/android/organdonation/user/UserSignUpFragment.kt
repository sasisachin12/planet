package aaa.android.organdonation.user

import aaa.android.organdonation.R
import aaa.android.organdonation.databinding.FragmentUserSignUpBinding
import aaa.android.organdonation.entity.UserSignUp
import aaa.android.organdonation.util.utils.getRandomString
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


class UserSignUpFragment : Fragment() {

    private var _binding: FragmentUserSignUpBinding? = null

    private val binding get() = _binding!!
    private lateinit var expenseViewModel: ExpenseViewModel

    private val insertResponse: MutableLiveData<Long> = MutableLiveData()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserSignUpBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        expenseViewModel = ViewModelProvider(requireActivity())[ExpenseViewModel::class.java]

        insertResponse.observe(
            viewLifecycleOwner
        ) {
            Toast.makeText(
                requireActivity(),
                R.string.added_success_msg,
                Toast.LENGTH_LONG
            ).show()
            _binding?.edtUserName?.text?.clear()
            _binding?.edtUserPhone?.text?.clear()
            _binding?.edtUserEmail?.text?.clear()
            findNavController().navigate(R.id.userLoginFragment)
        }
        _binding?.btnLogin?.setOnClickListener {
            val userName = _binding?.edtUserName?.text.toString().trim()
            val userMobile = _binding?.edtUserPhone?.text.toString().trim()
            val userEmail = _binding?.edtUserEmail?.text.toString().trim()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)?.supportActionBar?.hide()
    }
}