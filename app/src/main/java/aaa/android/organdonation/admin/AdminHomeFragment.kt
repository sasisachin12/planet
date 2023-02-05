package aaa.android.organdonation.admin

import aaa.android.organdonation.R
import aaa.android.organdonation.adapter.ListItemClickListener
import aaa.android.organdonation.adapter.SignUpAcceptListAdapter
import aaa.android.organdonation.databinding.FragmentAdminHomeBinding
import aaa.android.organdonation.entity.ExpenseInfo
import aaa.android.organdonation.entity.UserSignUp
import aaa.android.organdonation.viewmodel.ExpenseViewModel
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager


class AdminHomeFragment : Fragment(), ListItemClickListener,
    SignUpAcceptListAdapter.SendMailClickListener {

    private var _binding: FragmentAdminHomeBinding? = null

    private val binding get() = _binding!!
    private lateinit var expenseViewModel: ExpenseViewModel
    private var dateFilter = false
    private var expenseFilter = false
    private var amountFilter = false
    private var orderedList = emptyList<ExpenseInfo>()
    private val originalList = mutableListOf<ExpenseInfo>()
    private val updateResponse: MutableLiveData<Int> = MutableLiveData()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        expenseViewModel = ViewModelProvider(requireActivity())[ExpenseViewModel::class.java]
        val adapter = SignUpAcceptListAdapter(requireActivity(), this)
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireActivity())

        expenseViewModel.allSignUpRequest.observe(viewLifecycleOwner) { expenseList ->
            expenseList?.let {
                adapter.setAdapter(it)
            }

        }
        updateResponse.observe(
            viewLifecycleOwner
        ) {


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        /*(activity as AppCompatActivity?)?.supportActionBar?.setHomeButtonEnabled(true)
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity?)?.supportActionBar?.setDisplayOptions(ActionBar.DISPLAY_HOME_AS_UP)
        (activity as AppCompatActivity?)?.supportActionBar?.show()*/
    }

    override fun onDeleteItemClick(item: Any) {

    }

    override fun acceptMailClickListener(item: UserSignUp) {
        sendMail(item)
    }

    fun sendMail(item: UserSignUp) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "message/rfc822"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(item.email))
        intent.putExtra(Intent.EXTRA_SUBJECT, "Your Donor Account created Successfully")
        intent.putExtra(
            Intent.EXTRA_TEXT,
            "Your Donor Account created Successfully. Your User name : ${item.mobile} \n Password: ${item.password} \n +\n" +
                    " Login App : www.donorapp.com/signup?username=${item.mobile}&password=${item.password}"

        )
        activity?.startActivity(Intent.createChooser(intent, "Choose email..."))

        lifecycleScope.launchWhenResumed {
            updateResponse.postValue(item.id?.let {
                expenseViewModel.updateUserStatus(
                    "active",
                    it
                )
            })
        }

    }
}