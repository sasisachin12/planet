package aaa.android.plant.imageupload

import aaa.android.plant.databinding.ActivityAdminMainBinding
import aaa.android.plant.entity.DiseaseInformation
import aaa.android.plant.viewmodel.ExpenseViewModel
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.nguyenhoanglam.imagepicker.ui.imagepicker.registerImagePicker


class AdminMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminMainBinding


    private lateinit var expenseViewModel: ExpenseViewModel

    private val insertResponse: MutableLiveData<Long> = MutableLiveData()
   var inputData:ByteArray? = null
    private val launcher = registerImagePicker { images ->
        // Selected images are ready to use
        if(images.isNotEmpty()){
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
        binding = ActivityAdminMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.imgPickBtn.setOnClickListener {

            launcher.launch()
        }

       expenseViewModel = ViewModelProvider(this)[ExpenseViewModel::class.java]

        insertResponse.observe(
            this
        ) {
            Toast.makeText(this, "Disease details added successfully ", Toast.LENGTH_SHORT).show()

        }
       binding.imgSave.setOnClickListener {
            val disease = binding?.tvDiseaseName?.text.toString().trim()
            val diseaseName = binding?.tvDiseaseDescription?.text.toString().trim()
            val diseaseStage = binding?.tvDiseaseStage?.text.toString().trim()



            val diseaseInformation = DiseaseInformation(
                null, disease, diseaseName, diseaseStage, "", "PV", inputData
            )

            lifecycleScope.launchWhenResumed {

                insertResponse.postValue(expenseViewModel.insertSignUp(diseaseInformation))
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