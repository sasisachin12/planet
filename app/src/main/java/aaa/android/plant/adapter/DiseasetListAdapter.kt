package aaa.android.plant.adapter

import aaa.android.plant.R
import aaa.android.plant.entity.DiseaseInformation
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SignUpAcceptListAdapter internal constructor(
    context: Context, val sendMailClickListener: SendMailClickListener? = null
) : RecyclerView.Adapter<SignUpAcceptListAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var expenseList = emptyList<Any>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val description: TextView = itemView.findViewById(R.id.description)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = inflater.inflate(R.layout.row_accept_sign_up, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = expenseList[position]

        if (item is DiseaseInformation) {
            holder.name.text = " Plant Name:"+ item.status +" \n Disease Name : "+item.name
            holder.description.text = item.mobile + "\n  Solutions : \n "+item.password

        }

    }

    @SuppressLint("NotifyDataSetChanged")
    internal fun setAdapter(expenseInfo: List<Any>) {
        this.expenseList = expenseInfo
        this.notifyDataSetChanged()
    }


    override fun getItemCount() = expenseList.size

    interface SendMailClickListener {
        fun acceptMailClickListener(item: DiseaseInformation)
    }


}