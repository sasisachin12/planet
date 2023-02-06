package aaa.android.organdonation.admin

import aaa.android.organdonation.R
import aaa.android.organdonation.databinding.FragmentHomeBinding
import aaa.android.organdonation.util.AppConstant.ADMIN_USER_NAME
import aaa.android.organdonation.util.AppConstant.ADMIN_USER_PASSWORD
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController


class AdminLoginFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.btnLogin?.setOnClickListener {
            val userName = _binding?.edtUserName?.text.toString().trim()
            val userPassWord = _binding?.edtUserPassword?.text.toString().trim()
            if (userName.lowercase() == ADMIN_USER_NAME.lowercase() &&
                userPassWord.lowercase() == ADMIN_USER_PASSWORD.lowercase()
            ) {
                findNavController().navigate(R.id.adminHomeFragment)
            } else {
                Toast.makeText(requireContext(), "Invalid Data", Toast.LENGTH_SHORT).show()
            }


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