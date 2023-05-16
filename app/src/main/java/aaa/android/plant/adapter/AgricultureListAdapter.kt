package aaa.android.plant.adapter

import aaa.android.plant.R
import aaa.android.plant.entity.DiseaseInformation
import aaa.android.plant.entity.SearchHistoryDiseaseInformation
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class AgricultureListAdapter internal constructor(
    context: Context, val editDeleteClickListener: EditDeleteClickListener? = null
) : RecyclerView.Adapter<AgricultureListAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var expenseList = emptyList<Any>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val description: TextView = itemView.findViewById(R.id.description)
        val imageView: ImageView = itemView.findViewById(R.id.image_view)
        val imageViewEdit: TextView = itemView.findViewById(R.id.image_view_edit)
        val imageViewDelete: TextView = itemView.findViewById(R.id.image_view_delete)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = inflater.inflate(R.layout.row_added_disease_agriculture, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = expenseList[position]

        if (item is SearchHistoryDiseaseInformation) {
            holder.name.text = " Plant Name:" + item.status + " \n Disease Name : " + item.name
            holder.description.text = item.mobile + "\n  Solutions : \n " + item.password
            val bitmap = item.data?.size?.let { BitmapFactory.decodeByteArray(item.data, 0, it) }
            holder.imageView.setImageBitmap(bitmap)
            holder.imageViewDelete.setText("user"+item.username+"("+item.usermobile+")")
            holder.imageViewEdit.setText("searched on "+item.date)
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    internal fun setAdapter(expenseInfo: List<Any>) {
        this.expenseList = expenseInfo
        this.notifyDataSetChanged()
    }


    override fun getItemCount() = expenseList.size

    interface EditDeleteClickListener {
        fun editClicked(item: DiseaseInformation)
        fun deleteClicked(item: DiseaseInformation)
    }


}