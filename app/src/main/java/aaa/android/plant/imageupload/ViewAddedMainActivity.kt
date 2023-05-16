package aaa.android.plant.imageupload

import aaa.android.plant.adapter.AddedDiseaseListAdapter
import aaa.android.plant.databinding.ActivityImageViewAddedMainBinding
import aaa.android.plant.entity.DiseaseInformation
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


class ViewAddedMainActivity : AppCompatActivity(), AddedDiseaseListAdapter.EditDeleteClickListener {
    private lateinit var binding: ActivityImageViewAddedMainBinding

    private lateinit var expenseViewModel: ExpenseViewModel

    private val updateResponse: MutableLiveData<Int> = MutableLiveData()
    val newRequest = mutableListOf<DiseaseInformation>()
    var inputData: ByteArray? = null
    var adapter: AddedDiseaseListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageViewAddedMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val sharedPref: SharedPreferences =
            getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE)!!




        expenseViewModel = ViewModelProvider(this)[ExpenseViewModel::class.java]
        val adapter = AddedDiseaseListAdapter(this,this)
        binding.rvResults.adapter = adapter
        binding.rvResults.layoutManager = LinearLayoutManager(this)
        expenseViewModel.allSignUpRequest.observe(this) { expenseList ->
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

    override fun editClicked(item: DiseaseInformation) {
        val intent = Intent(this, AdminEditMainActivity::class.java)
        intent.putExtra("item", item)
        this.startActivity(intent)
    }

    override fun deleteClicked(item: DiseaseInformation) {

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to Delete?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                // Delete selected note from database
                lifecycleScope.launchWhenResumed {

                    item.id?.let { expenseViewModel.deleteByID(it) }
                }
            }
            .setNegativeButton("No") { dialog, id ->
                // Dismiss the dialog
                dialog.dismiss()
            }
        val alert = builder.create()
        alert.show()
    }
}