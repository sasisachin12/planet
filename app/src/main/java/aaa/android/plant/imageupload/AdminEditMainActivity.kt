package aaa.android.plant.imageupload

import aaa.android.plant.databinding.ActivityAdminEditMainBinding
import aaa.android.plant.entity.DiseaseInformation
import aaa.android.plant.viewmodel.ExpenseViewModel
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.nguyenhoanglam.imagepicker.ui.imagepicker.registerImagePicker


class AdminEditMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminEditMainBinding


    private lateinit var expenseViewModel: ExpenseViewModel

    private val insertResponse: MutableLiveData<Long> = MutableLiveData()
    var inputData: ByteArray? = null
    private val launcher = registerImagePicker { images ->
        // Selected images are ready to use
        if (images.isNotEmpty()) {
            val sampleImage = images[0]
            Glide.with(this)
                .load(sampleImage.uri)
                .into(binding.imageView)
            inputData = contentResolver.openInputStream(sampleImage.uri)?.readBytes()
        }
    }

    //private val expenseViewModel: ExpenseViewModel by viewModelFactory {  }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminEditMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.imgPickBtn.setOnClickListener {

            launcher.launch()
        }

        val item: DiseaseInformation =
            intent.getParcelableExtra<DiseaseInformation>("item")!! as DiseaseInformation
        binding.tvDiseaseName.setText(item.name)
        binding.tvDiseaseDescription.setText(item.email)
        binding.tvDiseaseStage.setText(item.mobile)
        binding.tvDiseaseSolutions.setText(item.password)
        binding.tvPlantName.setText(item.status)
        val bitmap = item.data?.size?.let { BitmapFactory.decodeByteArray(item.data, 0, it) }
        binding.imageView.setImageBitmap(bitmap)
        inputData = item.data
        val sharedPref: SharedPreferences =
            getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE)!!
        val oldParentMobileNumber = sharedPref.getString("PARENT_MOBILE", "")

        expenseViewModel = ViewModelProvider(this)[ExpenseViewModel::class.java]

        insertResponse.observe(
            this
        ) {
            Toast.makeText(this, "Disease details Updated successfully ", Toast.LENGTH_SHORT).show()
            finish()
        }
        binding.imgSave.setOnClickListener {
            val disease = binding?.tvDiseaseName?.text.toString().trim()
            val diseaseName = binding?.tvDiseaseDescription?.text.toString().trim()
            val diseaseStage = binding?.tvDiseaseStage?.text.toString().trim()
            val diseasesolutions = binding?.tvDiseaseSolutions?.text.toString().trim()
            val plantName = binding?.tvPlantName?.text.toString().trim()


            val diseaseInformation = DiseaseInformation(
                item.id, disease, diseaseName, diseaseStage, diseasesolutions, plantName, inputData
            )

            lifecycleScope.launchWhenResumed {

                insertResponse.postValue(
                    expenseViewModel.updateUserStatus(diseaseInformation).toLong()
                )
            }

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