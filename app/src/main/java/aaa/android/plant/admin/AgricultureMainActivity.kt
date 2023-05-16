package aaa.android.plant.admin

import aaa.android.plant.adapter.AgricultureListAdapter
import aaa.android.plant.databinding.ActivityImageViewAddedMainAgricultureBinding
import aaa.android.plant.databinding.ActivityImageViewAddedMainBinding
import aaa.android.plant.entity.DiseaseInformation
import aaa.android.plant.entity.SearchHistoryDiseaseInformation
import aaa.android.plant.viewmodel.ExpenseViewModel
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager


class AgricultureMainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityImageViewAddedMainAgricultureBinding

    private lateinit var expenseViewModel: ExpenseViewModel

    private val updateResponse: MutableLiveData<Int> = MutableLiveData()
    val newRequest = mutableListOf<SearchHistoryDiseaseInformation>()
    var inputData: ByteArray? = null
    var adapter: AgricultureListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageViewAddedMainAgricultureBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val sharedPref: SharedPreferences =
            getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE)!!

        binding.imgLogout.setOnClickListener {
            this.finish()
        }


        expenseViewModel = ViewModelProvider(this)[ExpenseViewModel::class.java]
        val adapter = AgricultureListAdapter(this,)
        binding.rvResults.adapter = adapter
        binding.rvResults.layoutManager = LinearLayoutManager(this)
        expenseViewModel.searchRequest.observe(this) { expenseList ->
            newRequest.addAll(expenseList)
            val newRequest = expenseList?.toMutableList()
            newRequest?.toMutableList()?.let { adapter.setAdapter(it) }
        }
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000

        //Permission code
        private val PERMISSION_CODE = 1001
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED
                ) {
                    //permission from popup granted
                    pickImageFromGallery()
                } else {
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

    }



}