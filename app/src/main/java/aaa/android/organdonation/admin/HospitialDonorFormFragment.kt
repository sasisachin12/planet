package aaa.android.organdonation.admin

import aaa.android.organdonation.R
import aaa.android.organdonation.databinding.FragmentHospitalDonationFormBinding
import aaa.android.organdonation.databinding.FragmentUserDonationFormBinding
import aaa.android.organdonation.entity.DonorFormDetails
import aaa.android.organdonation.util.Prefs
import aaa.android.organdonation.viewmodel.ExpenseViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.android.synthetic.main.row_hospital.*


class HospitialDonorFormFragment : Fragment() {

    private var _binding: FragmentHospitalDonationFormBinding? = null

    private val binding get() = _binding!!
    private val insertResponse: MutableLiveData<Long> = MutableLiveData()
    private lateinit var expenseViewModel: ExpenseViewModel
    var hospitialName = ""
    var hospitialCity = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHospitalDonationFormBinding.inflate(inflater, container, false)
        expenseViewModel = ViewModelProvider(requireActivity())[ExpenseViewModel::class.java]
        expenseViewModel.allHospitialRequest.observe(viewLifecycleOwner) { expenseList ->
            expenseList?.let {
                setupHospitalSpinner(it.map { it.name }.toMutableList())
                setupCitySpinner(it.map { it.city }.toMutableList())
            }

        }
        binding.fullBody.setOnCheckedChangeListener { compoundButton, b ->
            if (compoundButton.isChecked) {

            } else {
                binding.heart.isChecked = false
                binding.Kidneys.isChecked = false
                binding.Liver.isChecked = false
                binding.lungs.isChecked = false
            }
        }

        return binding.root
    }

    private fun getDonoteItem(): String {
        var donateItem = ""
        if (binding.fullBody.isChecked) {
            donateItem = binding.fullBody.text.toString()
        } else {

            if (binding.heart.isChecked) {
                donateItem.plus(", Heart")
            }
            if (binding.Kidneys.isChecked) {
                donateItem.plus(",Kidney")
            }
            if (binding.Liver.isChecked) {
                donateItem.plus(",Liver")
            }
            if (binding.lungs.isChecked) {
                donateItem.plus(",Lungs")
            }
        }
        return donateItem
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        expenseViewModel = ViewModelProvider(requireActivity())[ExpenseViewModel::class.java]

        insertResponse.observe(
            requireActivity()
        ) {
            Toast.makeText(
                requireContext(),
                R.string.request_success_msg,
                Toast.LENGTH_LONG
            ).show()

        }
        binding.btnSave.setOnClickListener {

            val name: String = binding.name.text.toString()
            val gender: String = getGender()
            val age: String = binding.age.text.toString()
            val bloodType: String = binding.bloodGroup.text.toString()
            val etMedicalCondition: String =
                binding.etMedicalCondition.text.toString().ifEmpty { "None" }
            val donate: String = getDonoteItem()
            val status: String = "Review"
            val contactPersonName: String = binding.emergencyName.text.toString()
            val contactPersonMobile: String = binding.emergencyPhone.text.toString()
            val city: String = binding.emergencyCity.text.toString()
            val state: String = binding.emergencyState.text.toString()
            val country: String = binding.emergencyCountry.text.toString()
            val address: String = binding.emergencyAddress.text.toString()
            val userloginid = Prefs.get("login_Id", 0)
            val formDetails = DonorFormDetails(
                null,
                name,
                gender,
                age,
                bloodType,
                donate,
                status,
                contactPersonName,
                contactPersonMobile,
                city,
                state,
                country,
                address,
                etMedicalCondition, userloginid, hospitialName, hospitialCity

            )
            lifecycleScope.launchWhenResumed {

                insertResponse.postValue(expenseViewModel.insertUserDonorForm(formDetails))
            }

        }


    }

    private fun getGender(): String {

        if (binding.male.isChecked) {
            return "Male"
        }

        if (binding.female.isChecked) {
            return "Male"
        }
        if (binding.others.isChecked) {
            return "Male"
        }
        return ""

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupHospitalSpinner(list: MutableList<String>) {

        val removeduplicate = list.distinct()
        val arrayAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            removeduplicate.toMutableList()
        )
        binding.hospitalSpinner.adapter = arrayAdapter

        binding.hospitalSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {
                    hospitialName = removeduplicate[position]
                    /* Toast.makeText(
                         requireContext(),
                         " " + list[position],
                         Toast.LENGTH_SHORT
                     ).show()*/
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    hospitialName = removeduplicate[0]
                    // Code to perform some action when nothing is selected
                }
            }
    }

    private fun setupCitySpinner(list: MutableList<String>) {
        val removeduplicate = list.distinct()
        val arrayAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            list.toMutableList()
        )
        binding.citySpinner.adapter = arrayAdapter

        binding.citySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                hospitialCity = removeduplicate[position]
                /* Toast.makeText(
                     requireContext(),
                     " " + list[position],
                     Toast.LENGTH_SHORT
                 ).show()*/
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                hospitialCity = removeduplicate[0]
                // Code to perform some action when nothing is selected
            }
        }
    }

    override fun onResume() {
        super.onResume()
        //  (activity as AppCompatActivity?)?.supportActionBar?.show()
    }
}