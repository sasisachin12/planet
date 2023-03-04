package aaa.android.organdonation.adapter

import aaa.android.organdonation.R
import aaa.android.organdonation.entity.DonorFormDetails
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DonorFormAdapter internal constructor(
    context: Context, val donorItemClickListener: DonorItemClickListener
) : RecyclerView.Adapter<DonorFormAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var expenseList = emptyList<Any>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val mobile: TextView = itemView.findViewById(R.id.mobile)
        val city: TextView = itemView.findViewById(R.id.city)
        val accept: TextView = itemView.findViewById(R.id.accept)
        val phoneCall: ImageView = itemView.findViewById(R.id.phome_call)
        val btnApproved: Button = itemView.findViewById(R.id.donor_approved)
        val hospital: TextView = itemView.findViewById(R.id.hospital)
        val hospitalCity: TextView = itemView.findViewById(R.id.hospital_city)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = inflater.inflate(R.layout.row_donor_forms, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = expenseList[position]

        if (item is DonorFormDetails) {
            holder.name.text =
                position.toString() + ") " + item.name + " " + item.age + "(" + item.gender + ")"
            holder.mobile.text = item.city
            holder.city.text = item.donate
            holder.accept.text = item.contactpersonMobile
            holder.hospital.text = item.hospital
            holder.hospitalCity.text = item.hospital_city

            holder.phoneCall.setOnClickListener {
                donorItemClickListener.makeCall(item)
            }
            holder.btnApproved.setOnClickListener {
                donorItemClickListener.approved(item)
            }

        }

    }

    @SuppressLint("NotifyDataSetChanged")
    internal fun setAdapter(expenseInfo: List<Any>) {
        this.expenseList = expenseInfo
        this.notifyDataSetChanged()
    }


    override fun getItemCount() = expenseList.size

    interface DonorItemClickListener {

        fun approved(item: Any)
        fun makeCall(item: Any)
    }

}