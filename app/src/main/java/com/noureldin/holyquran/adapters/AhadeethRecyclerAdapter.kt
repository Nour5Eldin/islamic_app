package com.noureldin.holyquran.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.noureldin.holyquran.R
import com.noureldin.holyquran.model.HadeethModel

class AhadeethRecyclerAdapter(var ahadeeth: MutableList<HadeethModel>):Adapter<AhadeethRecyclerAdapter.AhadeethViewHolder>() {
    class AhadeethViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val AhadeethNameTv = itemView.findViewById<TextView>(R.id.AhadeethNameTv)


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AhadeethViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_ahadeeth_list,parent,false)
        return AhadeethViewHolder(view)
    }

    override fun getItemCount(): Int = ahadeeth.size

    override fun onBindViewHolder(holder: AhadeethViewHolder, position: Int) {
        val title = ahadeeth[position].title
        holder.AhadeethNameTv.text = title
        if (onAhadeethClick != null){
            holder.itemView.setOnClickListener {
                onAhadeethClick!!.onItemAhadeethClick(ahadeeth[position],position)
            }
        }
    }

    var onAhadeethClick: OnItemAhadeethClickListener? = null
    interface OnItemAhadeethClickListener{
        fun onItemAhadeethClick(hadeeth: HadeethModel, index: Int )
    }
}