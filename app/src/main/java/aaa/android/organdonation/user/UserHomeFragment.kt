package aaa.android.organdonation.user

import aaa.android.organdonation.adapter.DonorSearchAdapter
import aaa.android.organdonation.adapter.FilterSelectionAdapter
import aaa.android.organdonation.databinding.FragmentUserHomeBinding
import aaa.android.organdonation.entity.DonorFormDetails
import aaa.android.organdonation.util.FilterType
import aaa.android.organdonation.viewmodel.ExpenseViewModel
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_user_home.*
import java.text.SimpleDateFormat
import java.util.*

class UserHomeFragment : Fragment(), FilterSelectionAdapter.RecyclerViewItemClickListener {

    private var _binding: FragmentUserHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var expenseViewModel: ExpenseViewModel
    val originalList = mutableListOf<DonorFormDetails>()
    val searchList = mutableListOf<DonorFormDetails>()
    internal var customDialog: CustomListViewDialog? = null
    var adapter: DonorSearchAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        expenseViewModel = ViewModelProvider(requireActivity())[ExpenseViewModel::class.java]
        adapter = DonorSearchAdapter(requireActivity())
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireActivity())
        expenseViewModel.allDonorRequest.observe(viewLifecycleOwner) { expenseList ->
            expenseList?.let {
                adapter?.setAdapter(it)
                originalList.clear()
                originalList.addAll(it)
                tv_search_result.text = originalList.size.toString() + " items found"
            }
        }

        binding.filterByCity.setOnClickListener {
            showCityFilterOptions()
        }
        binding.filterByBloodType.setOnClickListener {
            showBloodTypeFilterOptions()
        }
        binding.filterByMedicalCondition.setOnClickListener {
            showMedicalConditionFilterOptions()
        }
        binding.filterClear.setOnClickListener {
            originalList.let {
                adapter?.setAdapter(it)
            }
            binding.tvSearchResult.text = originalList.size.toString() + " items found"
        }
        binding.etSearchText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(editable: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val search = s.toString()
                val itemsList =
                    originalList.filter { it.donate.lowercase().contains(search.lowercase()) }
                itemsList.let {
                    adapter?.setAdapter(it)
                }
                binding.tvSearchResult.text = itemsList.size.toString() + " items found"

            }
        })
        binding.ivSearch.setOnClickListener {
            val search = binding.etSearchText.text.toString()
            if (search.isNotEmpty()) {
                val itemsList =
                    originalList.filter { it.donate.lowercase().contains(search.lowercase()) }
                itemsList.let {
                    adapter?.setAdapter(it)
                }
                binding.tvSearchResult.text = itemsList.size.toString() + " items found"
            } else {
                Toast.makeText(requireContext(), "Please Type Organ Name", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun Date.convertCurrentDateToChartDate(dataFormat: String): String {
        var day: String
        Calendar.getInstance().apply {
            time = this@convertCurrentDateToChartDate
            day = SimpleDateFormat(dataFormat, Locale.ENGLISH).apply {
                timeZone = TimeZone.getDefault()
            }.format(time)
        }
        return day
    }

    companion object {
        private const val LINEAR_CHART_DATE_FORMAT = "yyyy/MM/dd"
        private const val LINE_CHART_DATE_FORMAT = "yyyy-MM-dd"
        private const val CHART_ANIMATION_DURATION = 2500L
    }

    override fun clickOnItem(data: String, type: FilterType) {
        customDialog?.dismiss()
        when (type) {
            FilterType.CITY -> {
                val cityFilter = originalList.filter { it.city.lowercase() == data.lowercase() }
                adapter?.setAdapter(cityFilter)
                binding.tvSearchResult.text =
                    "Result of " + " $data :" + cityFilter.size.toString() + " items found"
            }
            FilterType.BLOOD_TYPE -> {
                val cityFilter =
                    originalList.filter { it.bloodtype.lowercase() == data.lowercase() }
                adapter?.setAdapter(cityFilter)
                binding.tvSearchResult.text =
                    "Result of " + " $data :" + cityFilter.size.toString() + " items found"
            }
            FilterType.MEDICAL_CONDITION -> {
                val cityFilter =
                    originalList.filter { it.etMedicalCondition.lowercase() == data.lowercase() }
                adapter?.setAdapter(cityFilter)
                binding.tvSearchResult.text =
                    "Result of " + " $data :" + cityFilter.size.toString() + " items found"
            }
        }
    }

    private fun showCityFilterOptions() {
        val items = originalList.map { it.city.lowercase() }.distinct().map { it.capitalize() }
        val dataAdapter = FilterSelectionAdapter(items.toMutableList(), this, FilterType.CITY)
        customDialog = CustomListViewDialog(requireActivity(), dataAdapter)

        //if we know that the particular variable not null any time ,we can assign !! (not null operator ), then  it won't check for null, if it becomes null, it willthrow exception
        customDialog!!.show()
        customDialog!!.setCanceledOnTouchOutside(false)
    }

    private fun showBloodTypeFilterOptions() {
        val items = originalList.map { it.bloodtype.lowercase() }.distinct().map { it.capitalize() }
        val dataAdapter = FilterSelectionAdapter(items.toMutableList(), this, FilterType.BLOOD_TYPE)
        customDialog = CustomListViewDialog(requireActivity(), dataAdapter)

        //if we know that the particular variable not null any time ,we can assign !! (not null operator ), then  it won't check for null, if it becomes null, it willthrow exception
        customDialog!!.show()
        customDialog!!.setCanceledOnTouchOutside(false)
    }

    private fun showMedicalConditionFilterOptions() {
        val items = originalList.map { it.etMedicalCondition.lowercase() }.distinct()
            .map { it.capitalize() }
        val dataAdapter =
            FilterSelectionAdapter(items.toMutableList(), this, FilterType.MEDICAL_CONDITION)
        customDialog = CustomListViewDialog(requireActivity(), dataAdapter)

        //if we know that the particular variable not null any time ,we can assign !! (not null operator ), then  it won't check for null, if it becomes null, it willthrow exception
        customDialog!!.show()
        customDialog!!.setCanceledOnTouchOutside(false)
    }
}