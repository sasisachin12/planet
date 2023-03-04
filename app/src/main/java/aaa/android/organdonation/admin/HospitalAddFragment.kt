package aaa.android.organdonation.admin

import aaa.android.organdonation.R
import aaa.android.organdonation.SplashActivity
import aaa.android.organdonation.adapter.ListItemClickListener
import aaa.android.organdonation.adapter.SignUpAcceptListAdapter
import aaa.android.organdonation.databinding.FragmentAddHospitalBinding
import aaa.android.organdonation.databinding.FragmentAdminHomeBinding
import aaa.android.organdonation.entity.Hospital
import aaa.android.organdonation.entity.UserSignUp
import aaa.android.organdonation.user.UserLoginNewActivity
import aaa.android.organdonation.util.utils
import aaa.android.organdonation.viewmodel.ExpenseViewModel
import android.annotation.SuppressLint
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.isradeleon.notify.Notify


class HospitalAddFragment : Fragment(), ListItemClickListener,
    SignUpAcceptListAdapter.SendMailClickListener {

    private var _binding: FragmentAddHospitalBinding? = null

    private val binding get() = _binding!!
    private lateinit var expenseViewModel: ExpenseViewModel

    private val updateResponse: MutableLiveData<Int> = MutableLiveData()
    private val insertResponse: MutableLiveData<Long> = MutableLiveData()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddHospitalBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        expenseViewModel = ViewModelProvider(requireActivity())[ExpenseViewModel::class.java]
        insertResponse.observe(
            requireActivity()
        ) {
            Toast.makeText(
                requireContext(),
                "Added success fully ",
                Toast.LENGTH_LONG
            ).show()
            binding.hospitalName.text?.clear()
            binding.availableSpecialities.text?.clear()
            binding.city.text?.clear()
            binding.emergencyState.text?.clear()
            binding.emergencyCountry.text?.clear()
            binding.emergencyAddress.text?.clear()
            binding.emergencyPhone.text?.clear()

        }
        binding?.btnSave?.setOnClickListener {
            val userName = binding?.hospitalName?.text.toString().trim()
            val specialities = binding?.availableSpecialities?.text.toString().trim()
            val city = binding?.city?.text.toString().trim()
            val state = binding?.emergencyState?.text.toString().trim()
            val country = binding?.emergencyCountry?.text.toString().trim()
            val address = binding?.emergencyAddress?.text.toString().trim()
            val phone = binding?.emergencyPhone?.text.toString().trim()
            //val userpassword = utils.getRandomString(8)
            // findNavController().navigate(R.id.userHomeFragment)
            val hospital = Hospital(
                null,
                userName,
                specialities,
                city,
                state,
                country,
                address,
                phone,
                "active"
            )

            lifecycleScope.launchWhenResumed {

                insertResponse.postValue(expenseViewModel.insertHospital(hospital))
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        //(activity as AdminHomeActivity?)?.showHamburIcon()
    }

    override fun onDeleteItemClick(item: Any) {

    }

    override fun acceptMailClickListener(item: UserSignUp) {
        sendMail(item)
    }

    fun sendMail(item: UserSignUp) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "message/rfc822"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(item.email))
        intent.putExtra(Intent.EXTRA_SUBJECT, "Your Donor Account created Successfully")
        intent.putExtra(
            Intent.EXTRA_TEXT,
            "Your Donor Account created Successfully. Your User name : ${item.mobile} \n Password: ${item.password} \n +\n" +
                    " Login App : www.donorapp.com/signup?username=${item.mobile}&password=${item.password}"

        )
        activity?.startActivity(Intent.createChooser(intent, "Choose email..."))

        lifecycleScope.launchWhenResumed {
            updateResponse.postValue(item.id?.let {
                expenseViewModel.updateUserStatus(
                    "active",
                    it
                )
            })
        }

    }


    @SuppressLint("MissingPermission")
    private fun sendNotification() {

        var builder = NotificationCompat.Builder(requireActivity(), "454545454")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Donor System")
            .setContentText("Congratulations your account has been created")
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("congratulations your account has been created")
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(requireActivity())) {

            notify(111, builder.build())
        }


    }


    private fun showNotification(message: String) {
        val intent = Intent(requireActivity(), SplashActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.putExtra("data", message)
        intent.putExtra("KEY", "YOUR VAL")
        val pendingIntent =
            PendingIntent.getActivity(
                requireActivity(),
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(requireActivity())
            .setAutoCancel(true)
            .setContentTitle("Registry")
            .setContentText(message)
            .setSmallIcon(R.drawable.admin_one)
            .setContentIntent(pendingIntent)
        val manager = activity?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager?.notify(0, builder.build())
    }
}