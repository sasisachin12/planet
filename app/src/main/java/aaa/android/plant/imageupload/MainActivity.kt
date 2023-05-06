package aaa.android.plant.imageupload

import aaa.android.plant.adapter.SignUpAcceptListAdapter
import aaa.android.plant.databinding.ActivityImageMainBinding
import aaa.android.plant.entity.DiseaseInformation
import aaa.android.plant.viewmodel.ExpenseViewModel
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.nguyenhoanglam.imagepicker.ui.imagepicker.registerImagePicker


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityImageMainBinding

    private lateinit var expenseViewModel: ExpenseViewModel

    private val updateResponse: MutableLiveData<Int> = MutableLiveData()
    val newRequest = mutableListOf<DiseaseInformation>()
    var inputData: ByteArray? = null
    var adapter: SignUpAcceptListAdapter? = null
    private val launcher = registerImagePicker { images ->
        // Selected images are ready to use
        if (images.isNotEmpty()) {
            val sampleImage = images[0]
            Glide.with(this)
                .load(sampleImage.uri)
                .into(binding.imageView)
            inputData = contentResolver.openInputStream(sampleImage.uri)?.readBytes()
            Log.e(" aaa ", "" + newRequest?.size)
            val dataresult = newRequest.filter { it.data.contentEquals(inputData) }.toMutableList()
            Log.e(" aaa ", "" + dataresult?.size)
            adapter = SignUpAcceptListAdapter(this)
            binding.rvResults.adapter = adapter
            binding.rvResults.layoutManager = LinearLayoutManager(this)
            if (dataresult.isNullOrEmpty()) {
                adapter?.setAdapter(mutableListOf())
                binding.rvResults.visibility = View.GONE
                binding.tvResults.text = " Found " + dataresult?.size
            } else {
                newRequest.toMutableList().let { adapter?.setAdapter(it) }
                binding.rvResults.visibility = View.VISIBLE
                binding.tvResults.text = " Found " + dataresult?.size
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val sharedPref: SharedPreferences =
            getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE)!!


        val alertNumber = sharedPref.getString("PARENT_MOBILE", "")
        binding.imgSms.setOnClickListener {
            val modalBottomSheet = ModalBottomSheet()
            modalBottomSheet.show(supportFragmentManager, ModalBottomSheet.TAG)
        }

        binding.imgPickBtn.setOnClickListener {
            launcher.launch()
            /*          //check runtime permission
                      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                          if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                              PackageManager.PERMISSION_DENIED
                          ) {
                              //permission denied
                              val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                              //show popup to request runtime permission
                              requestPermissions(permissions, PERMISSION_CODE);
                          } else {
                              //permission already granted
                              pickImageFromGallery()
                          }
                      } else {
                          //system OS is < Marshmallow
                          pickImageFromGallery()
                      }*/
        }

        expenseViewModel = ViewModelProvider(this)[ExpenseViewModel::class.java]
        val adapter = SignUpAcceptListAdapter(this)
        binding.rvResults.adapter = adapter
        binding.rvResults.layoutManager = LinearLayoutManager(this)
        expenseViewModel.allSignUpRequest.observe(this) { expenseList ->
            newRequest.addAll(expenseList)
            val newRequest = expenseList?.filter { it.data == inputData }?.toMutableList()
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
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            binding.imageView.setImageURI(data?.data)
        }
    }
}