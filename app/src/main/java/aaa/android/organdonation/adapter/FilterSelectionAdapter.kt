package aaa.android.organdonation.adapter

import aaa.android.organdonation.R
import aaa.android.organdonation.adapter.FilterSelectionAdapter.FruitViewHolder
import aaa.android.organdonation.util.FilterType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FilterSelectionAdapter(
    private val mDataset: MutableList<String>,
    var recyclerViewItemClickListener: RecyclerViewItemClickListener, val type: FilterType
) : RecyclerView.Adapter<FruitViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, i: Int): FruitViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_filter_text, parent, false)
        return FruitViewHolder(v)
    }

    override fun onBindViewHolder(fruitViewHolder: FruitViewHolder, i: Int) {
        fruitViewHolder.mTextView.text = mDataset[i]
    }

    override fun getItemCount(): Int {
        return mDataset.size
    }

    inner class FruitViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        var mTextView: TextView

        init {
            mTextView = v.findViewById<View>(R.id.textView) as TextView
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            recyclerViewItemClickListener.clickOnItem(mDataset[this.adapterPosition], type)
        }
    }

    interface RecyclerViewItemClickListener {
        fun clickOnItem(data: String, type: FilterType)
    }
}