package aaa.android.organdonation.user

import aaa.android.organdonation.databinding.FragmentUserFaqBinding
import aaa.android.organdonation.databinding.FragmentUserHealthyBinding
import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import java.util.*


class UserHealthyProxyFragment : Fragment() {

    private var _binding: FragmentUserHealthyBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserHealthyBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        //  (activity as AppCompatActivity?)?.supportActionBar?.show()
    }


}