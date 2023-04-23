package aaa.android.plant.util

import aaa.android.plant.util.AppConstant.DATE_FORMAT_ONE
import aaa.android.plant.util.AppConstant.TIME_FORMAT_ONE
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

object utils {

    fun getRandomString(length: Int) : String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
        return (1..length)
            .map { allowedChars.random() }
            .joinToString("")
    }


    fun getCurrentDate(): String {
        val calender = Calendar.getInstance()
        val sdf = SimpleDateFormat(DATE_FORMAT_ONE, Locale.getDefault())
        return sdf.format(calender.time)
    }

    fun getCurrentTime(): String {
        val calender = Calendar.getInstance()
        val sdf = SimpleDateFormat(TIME_FORMAT_ONE, Locale.getDefault())
        return sdf.format(calender.time)
    }


    fun TextInputEditText.transformIntoDatePicker(
        context: Context,
        format: String,
        maxDate: Date? = null
    ) {
        isFocusableInTouchMode = false
        isClickable = true
        isFocusable = false
        isCursorVisible = false

        val myCalendar = Calendar.getInstance()
        val datePickerOnDataSetListener =
            DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, monthOfYear)
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val sdf = SimpleDateFormat(format, Locale.getDefault())
                setText(sdf.format(myCalendar.time))
            }

        setOnClickListener {
            DatePickerDialog(
                context, datePickerOnDataSetListener, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            ).run {
                maxDate?.time?.also { datePicker.maxDate = it }
                show()
            }
        }
    }


    fun TextInputEditText.transformIntoTimePicker(
        context: Context,
        format: String,
        maxDate: Date? = null
    ) {
        isFocusableInTouchMode = false
        isClickable = true
        isFocusable = false
        isCursorVisible = false

        val myCalendar = Calendar.getInstance()

        val timePickerOnDataSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute ->
            myCalendar.set(Calendar.HOUR_OF_DAY, hour)
            myCalendar.set(Calendar.MINUTE, minute)

            setText(SimpleDateFormat(format, Locale.getDefault()).format(myCalendar.time))
        }


        setOnClickListener {
            TimePickerDialog(
                context,
                timePickerOnDataSetListener,
                myCalendar.get(Calendar.HOUR_OF_DAY),
                myCalendar.get(Calendar.MINUTE),
                true
            ).show()
        }
    }




    fun convertDateToLong(date: String, format: String): Long {
        val df = SimpleDateFormat(format, Locale.getDefault())
        return df.parse(date)?.time ?: 0
    }


    fun convertLongToTime (time: Long): String {
        val date = Date(time)
        val format = SimpleDateFormat("$DATE_FORMAT_ONE $TIME_FORMAT_ONE", Locale.getDefault())
        return format.format(date)
    }

    fun getCurrentMonthStart(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, 0)
        calendar[Calendar.DATE] = calendar.getActualMinimum(Calendar.DAY_OF_MONTH)
        val monthFirstDay = calendar.time
        val df = SimpleDateFormat(DATE_FORMAT_ONE, Locale.getDefault())
        return df.format(monthFirstDay)


    }

    fun getCurrentMonthEnd(): String {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, 0)
        calendar[Calendar.DATE] = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val monthLastDay = calendar.time
        val df = SimpleDateFormat(DATE_FORMAT_ONE, Locale.getDefault())
        return df.format(monthLastDay)


    }

    fun getCurrentMonthNAme():String
    {
        val calendar = Calendar.getInstance()
        val monthDate =  SimpleDateFormat("MMMM", Locale.getDefault())
        return monthDate.format(calendar.time)
    }
}