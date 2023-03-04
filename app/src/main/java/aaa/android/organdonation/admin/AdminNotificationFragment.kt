package aaa.android.organdonation.admin

import aaa.android.organdonation.R
import aaa.android.organdonation.databinding.FragmentAdminNotificationBinding
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
import com.application.isradeleon.notify.Notify


class AdminNotificationFragment : Fragment() {

    private var _binding: FragmentAdminNotificationBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminNotificationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSave.setOnClickListener {
            val title = binding.ntTitle.text.toString()
            val titleMessage = binding.ntMessage.text.toString()

            Notify.build(requireActivity().application)
                .setTitle(title)
                .setContent(titleMessage)
                .setSmallIcon(R.drawable.admin_one)
                .setColor(R.color.app_primary)
                .largeCircularIcon()
                .show()

            Toast.makeText(requireContext(), "Notification Successfully send to All", Toast.LENGTH_SHORT).show()
            binding.ntTitle.text?.clear()
            binding.ntMessage.text?.clear()
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