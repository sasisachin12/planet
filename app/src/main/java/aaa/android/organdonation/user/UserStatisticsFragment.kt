package aaa.android.organdonation.user

import aaa.android.organdonation.databinding.FragmentUserStatisticsBinding
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.straiberry.android.charts.extenstions.toChartData
import java.text.SimpleDateFormat
import java.util.*


class UserStatisticsFragment : Fragment() {

    private var _binding: FragmentUserStatisticsBinding? = null

    private val binding get() = _binding!!

    // variable for our bar chart
    var barChart: BarChart? = null

    // variable for our bar data.
    var barData: BarData? = null

    // variable for our bar data set.
    var barDataSet: BarDataSet? = null

    // array list for storing entries.
    var barEntriesArrayList: ArrayList<BarEntry>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserStatisticsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBarChart()

        // calling method to get bar entries.
        // calling method to get bar entries.
        getBarEntries()
        barChart = binding.idBarChart
        // creating a new bar data set.

        // creating a new bar data set.
        barDataSet = BarDataSet(barEntriesArrayList, "Organ donor")

        // creating a new bar data and
        // passing our bar data set.

        // creating a new bar data and
        // passing our bar data set.
        barData = BarData(barDataSet)

        // below line is to set data
        // to our bar chart.

        // below line is to set data
        // to our bar chart.
        barChart!!.data = barData

        // adding color to our bar data set.

        // adding color to our bar data set.
        barDataSet!!.setColors(*ColorTemplate.MATERIAL_COLORS)

        // setting text color.

        // setting text color.
        barDataSet!!.valueTextColor = Color.BLACK

        // setting text size

        // setting text size
        barDataSet!!.valueTextSize = 16f
        barChart!!.description.isEnabled = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        //  (activity as AppCompatActivity?)?.supportActionBar?.show()
    }

    private fun setupBarChart() {


        val chartData = (12 downTo 1).map { (10..100).random() }.toMutableList()
        val intervalData = (12 downTo 1).map { it }.toMutableList()

        binding.simpleBarChart.setChartData(chartData, intervalData)
        binding.simpleBarChart.setMaxValue(110)
        binding.simpleBarChart.setMinValue(0)


        val currentDate = Date()
        val calendar = Calendar.getInstance()
        calendar.time = currentDate
        val data: ArrayList<HashMap<String, Int?>> = arrayListOf()

        repeat(7) {
            data.add(
                hashMapOf(
                    Pair(
                        calendar.time.convertCurrentDateToChartDate(
                            LINE_CHART_DATE_FORMAT
                        ), it * 10 + 1
                    )
                )
            )
            calendar.add(Calendar.DATE, -1)
        }

        binding.barChartViewBrushing.animate(data.toChartData())
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

    private fun getBarEntries() {
        // creating a new array list
        barEntriesArrayList = ArrayList()

        // adding new entry to our array list with bar
        // entry and passing x and y axis value to it.
        barEntriesArrayList?.add(BarEntry(1f, (10..100).random().toFloat()))
        barEntriesArrayList?.add(BarEntry(2f, (10..100).random().toFloat()))
        barEntriesArrayList?.add(BarEntry(3f, (10..100).random().toFloat()))
        barEntriesArrayList?.add(BarEntry(4f, (10..100).random().toFloat()))
        barEntriesArrayList?.add(BarEntry(5f, (10..100).random().toFloat()))
        barEntriesArrayList?.add(BarEntry(6f, (10..100).random().toFloat()))
    }
}