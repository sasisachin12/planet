package aaa.android.organdonation.admin

import aaa.android.organdonation.R
import aaa.android.organdonation.adapter.DonorFormAdapter
import aaa.android.organdonation.adapter.ListItemClickListener
import aaa.android.organdonation.adapter.SignUpAcceptListAdapter
import aaa.android.organdonation.databinding.FragmentAdminHomeBinding
import aaa.android.organdonation.entity.DonorFormDetails
import aaa.android.organdonation.entity.ExpenseInfo
import aaa.android.organdonation.entity.UserSignUp
import aaa.android.organdonation.viewmodel.ExpenseViewModel
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.isradeleon.notify.Notify


class AdminDonorDetailsFragment : Fragment(), ListItemClickListener,
    SignUpAcceptListAdapter.SendMailClickListener, DonorFormAdapter.DonorItemClickListener {

    private var _binding: FragmentAdminHomeBinding? = null

    private val binding get() = _binding!!
    private lateinit var expenseViewModel: ExpenseViewModel
    private val updateResponse: MutableLiveData<Int> = MutableLiveData()
    val adapter: DonorFormAdapter? = null

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                val intent = Intent(Intent.ACTION_CALL)

                intent.setData(Uri.parse("tel:" + 12343434))
                requireActivity().startActivity(intent)
            } else {
                /*  ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.CALL_PHONE),
                      454545)*/
            }
        }

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
        val adapter = DonorFormAdapter(requireActivity(), this)
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireActivity())
        //makecall()
        expenseViewModel.allDonorRequest.observe(viewLifecycleOwner) { expenseList ->
            expenseList?.let {
                adapter.setAdapter(it)
            }

        }
        updateResponse.observe(
            viewLifecycleOwner
        ) {}
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        //(activity as AdminHomeActivity?)?.showHamburIcon()
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


    fun makecall(phone: String) {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Pass any permission you want while launching
            requestPermission.launch(Manifest.permission.CALL_PHONE)
        } else {
            val intent = Intent(Intent.ACTION_CALL)

            intent.setData(Uri.parse("tel:" + phone))
            requireActivity().startActivity(intent)
        }
    }

    override fun approved(item: Any) {
        val itemData = item as DonorFormDetails
        lifecycleScope.launchWhenResumed {
            updateResponse.postValue(itemData.id?.let {
                expenseViewModel.updateDonorStatusResponse(
                    "Verified",
                    it
                )
            })
        }

        lifecycleScope.launchWhenResumed {
            val donorList = expenseViewModel.getDonorAllFormResponse()
            donorList?.let {
                adapter?.setAdapter(it)
            }
        }
        Notify.build(requireActivity().application)
            .setTitle("Organ ")
            .setContent("Your request has been accepted By Admin")
            .setSmallIcon(R.drawable.admin_one)
            .setColor(R.color.app_primary)
            .largeCircularIcon()
            .show()

    }

    override fun makeCall(item: Any) {
        val item = item as DonorFormDetails
        makecall(item.contactpersonMobile)
    }
}