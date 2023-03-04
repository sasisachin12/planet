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
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DonorStatusAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<DonorStatusAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var expenseList = emptyList<Any>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val mobile: TextView = itemView.findViewById(R.id.mobile)
        val city: TextView = itemView.findViewById(R.id.city)
        val accept: TextView = itemView.findViewById(R.id.accept)
        val hospital: TextView = itemView.findViewById(R.id.hospital)
        val hospitalCity: TextView = itemView.findViewById(R.id.hospital_city)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = inflater.inflate(R.layout.row_donor_search_forms, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = expenseList[position]

        if (item is DonorFormDetails) {
            holder.name.text =
                position.toString() + ") " + item.name + " " + item.age + "(" + item.gender + ")"
            holder.mobile.text = item.city + "\n" + item.contactpersonMobile
            holder.city.text = item.donate
            holder.accept.text = item.etMedicalCondition

            if (item.status == "Verified")
                holder.hospital.text = " Donor request Accepted"
            else
                holder.hospital.text = " Waiting for admin approval"

        }

    }

    @SuppressLint("NotifyDataSetChanged")
    internal fun setAdapter(expenseInfo: List<Any>) {
        this.expenseList = expenseInfo
        this.notifyDataSetChanged()
    }


    override fun getItemCount() = expenseList.size

}