package aaa.android.organdonation.adapter

import aaa.android.organdonation.R
import aaa.android.organdonation.entity.ExpenseInfo
import aaa.android.organdonation.entity.UserSignUp
import aaa.android.organdonation.util.utils.convertLongToTime
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ExpenseListAdapter internal constructor(
    context: Context, val listItemClickListener: ListItemClickListener
) : RecyclerView.Adapter<ExpenseListAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var expenseList = emptyList<Any>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val vdate: TextView = itemView.findViewById(R.id.date)
        val expense: TextView = itemView.findViewById(R.id.expense)
        val expense_amount: TextView = itemView.findViewById(R.id.et_expense_amount)
        val delete: ImageView = itemView.findViewById(R.id.delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = inflater.inflate(R.layout.row_expense_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = expenseList[position]
        if(item is ExpenseInfo) {
            holder.vdate.text = convertLongToTime(item.date)
            holder.expense.text = item.expense
            holder.expense_amount.text = item.amount
            holder.vdate.setOnClickListener {
                listItemClickListener.onDeleteItemClick(item)

            }
        }
        if(item is UserSignUp) {
            holder.vdate.text = item.name
            holder.expense.text = item.mobile
            holder.expense_amount.text = item.email
            holder.vdate.setOnClickListener {
                listItemClickListener.onDeleteItemClick(item)

            }
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    internal fun setAdapter(expenseInfo: List<Any>) {
        this.expenseList = expenseInfo
        this.notifyDataSetChanged()
    }



    override fun getItemCount() = expenseList.size


}