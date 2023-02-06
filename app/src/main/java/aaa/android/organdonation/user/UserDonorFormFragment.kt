package aaa.android.organdonation.user

import aaa.android.organdonation.databinding.FragmentGalleryBinding
import aaa.android.organdonation.databinding.FragmentUserDonationFormBinding
import aaa.android.organdonation.databinding.FragmentUserHomeBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class UserDonorFormFragment : Fragment() {

    private var _binding: FragmentUserDonationFormBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDonationFormBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)?.supportActionBar?.show()
    }
}