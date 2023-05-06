package aaa.android.plant.imageupload

import aaa.android.plant.R
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ModalBottomSheet : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.modal_bottom_sheet_content, container, false)

    companion object {
        const val TAG = "ModalBottomSheet"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val description = view.findViewById<EditText>(R.id.tv_disease_description)
        val save = view.findViewById<Button>(R.id.img_save)
        save.setOnClickListener {
            val sharedPref: SharedPreferences =
                activity?.getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE)!!


            val alertNumber = sharedPref.getString("PARENT_MOBILE", "")
            callAPIDemo(alertNumber.toString(), description.text.toString())
        }
    }

    fun callAPIDemo(mobile: String, message: String) {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(activity)
        val url =
            "http://smsserver9.creativepoint.in/api.php?username=fantasy&password=596692&to=$mobile&from=FSSMSS&message=Dear user  your msg is $message  Sent By FSMSG FSSMSS&PEID=1501563800000030506&templateid=1507162882948811640"
        // Request a string response from the provided URL.
        val stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener<String> { response ->
                Toast.makeText(requireContext(), "" + response, Toast.LENGTH_SHORT).show()
                // Display the first 500 characters of the response string.
                // textView.text = "Response is: ${response.substring(0, 500)}"
            },
            Response.ErrorListener {
                //textView.text = "That didn't work!"
                Toast.makeText(requireContext(), "" + it.message, Toast.LENGTH_SHORT).show()
            })

        // Add the request to the RequestQueue.
        queue.add(stringRequest)
    }

}