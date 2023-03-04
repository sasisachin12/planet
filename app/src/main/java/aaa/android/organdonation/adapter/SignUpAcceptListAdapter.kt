package aaa.android.organdonation.adapter

import aaa.android.organdonation.R
import aaa.android.organdonation.entity.UserSignUp
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
        val mobile: TextView = itemView.findViewById(R.id.mobile)
        val email: TextView = itemView.findViewById(R.id.email)
        val accept: TextView = itemView.findViewById(R.id.accept)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = inflater.inflate(R.layout.row_accept_sign_up, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = expenseList[position]

        if (item is UserSignUp) {
            holder.name.text = item.name
            holder.mobile.text = item.mobile
            holder.email.text = item.email
            holder.accept.setOnClickListener {
                sendMailClickListener?.acceptMailClickListener(item)

            }
            if (item.status == "PV") {
                holder.accept.visibility = View.VISIBLE
            } else {
                holder.accept.visibility = View.INVISIBLE
            }
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    internal fun setAdapter(expenseInfo: List<Any>) {
        this.expenseList = expenseInfo
        this.notifyDataSetChanged()
    }


    override fun getItemCount() = expenseList.size

    interface SendMailClickListener {
        fun acceptMailClickListener(item: UserSignUp)
    }


}