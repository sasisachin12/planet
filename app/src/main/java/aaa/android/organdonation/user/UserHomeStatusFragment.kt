package aaa.android.organdonation.user

import aaa.android.organdonation.adapter.DonorSearchAdapter
import aaa.android.organdonation.adapter.DonorStatusAdapter
import aaa.android.organdonation.adapter.FilterSelectionAdapter
import aaa.android.organdonation.databinding.FragmentUserHomeBinding
import aaa.android.organdonation.databinding.FragmentUserStatusHomeBinding
import aaa.android.organdonation.entity.DonorFormDetails
import aaa.android.organdonation.util.FilterType
import aaa.android.organdonation.util.Prefs
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

class UserHomeStatusFragment : Fragment(), FilterSelectionAdapter.RecyclerViewItemClickListener {

    private var _binding: FragmentUserStatusHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var expenseViewModel: ExpenseViewModel
    val originalList = mutableListOf<DonorFormDetails>()
    val searchList = mutableListOf<DonorFormDetails>()
    internal var customDialog: CustomListViewDialog? = null
    var adapter: DonorStatusAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserStatusHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        expenseViewModel = ViewModelProvider(requireActivity())[ExpenseViewModel::class.java]
        adapter = DonorStatusAdapter(requireActivity())
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireActivity())
        expenseViewModel.allDonorRequest.observe(viewLifecycleOwner) { expenseList ->
            val list=expenseList?.filter { it.registeruser == Prefs.get("login_Id",0) }
            list?.toMutableList()?.let { adapter?.setAdapter(it) }
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