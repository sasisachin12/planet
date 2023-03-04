package aaa.android.organdonation.adapter

import aaa.android.organdonation.R
import aaa.android.organdonation.entity.DonorFormDetails
import aaa.android.organdonation.entity.Hospital
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

class HospitalAdapter internal constructor(
    context: Context, val donorItemClickListener: DonorItemClickListener
) : RecyclerView.Adapter<HospitalAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var expenseList = emptyList<Any>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val mobile: TextView = itemView.findViewById(R.id.mobile)
        val city: TextView = itemView.findViewById(R.id.city)
        val accept: TextView = itemView.findViewById(R.id.accept)
        val phoneCall: ImageView = itemView.findViewById(R.id.phome_call)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = inflater.inflate(R.layout.row_hospital, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = expenseList[position]

        if (item is Hospital) {
            holder.name.text = item.name
            holder.mobile.text = item.speciality
            holder.city.text = item.city
            holder.accept.text = item.address

            holder.phoneCall.setOnClickListener {
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
    }

}