package aaa.android.organdonation.admin

import aaa.android.organdonation.R
import aaa.android.organdonation.SplashActivity
import aaa.android.organdonation.adapter.ListItemClickListener
import aaa.android.organdonation.adapter.SignUpAcceptListAdapter
import aaa.android.organdonation.databinding.FragmentAdminHomeBinding
import aaa.android.organdonation.entity.UserSignUp
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
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.application.isradeleon.notify.Notify


class AdminHomeFragment : Fragment(), ListItemClickListener,
    SignUpAcceptListAdapter.SendMailClickListener {

    private var _binding: FragmentAdminHomeBinding? = null

    private val binding get() = _binding!!
    private lateinit var expenseViewModel: ExpenseViewModel

    private val updateResponse: MutableLiveData<Int> = MutableLiveData()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAdminHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        expenseViewModel = ViewModelProvider(requireActivity())[ExpenseViewModel::class.java]
        val adapter = SignUpAcceptListAdapter(requireActivity(), this)
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireActivity())
        // showNotification("aaa")

        expenseViewModel.allSignUpRequest.observe(viewLifecycleOwner) { expenseList ->
            val newRequest = expenseList?.filter { it.status == "PV" }?.toMutableList()
            newRequest?.toMutableList()?.let { adapter.setAdapter(it) }
        }
        updateResponse.observe(
            viewLifecycleOwner
        ) {
            sendNotification()
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
        Notify.build(requireActivity().application)
            .setTitle("Organ Donor")
            .setContent("Your Account Activated By Admin")
            .setSmallIcon(R.drawable.admin_one)
            .setColor(R.color.app_primary)
            .largeCircularIcon()
            .show()

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