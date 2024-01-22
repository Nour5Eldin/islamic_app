package com.noureldin.holyquran.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.noureldin.holyquran.R
import com.noureldin.holyquran.data.QuranItem


class ChaptersRecyclerAdapter(var ChapterList: List<QuranItem>): Adapter<ChaptersRecyclerAdapter.ChapterViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChapterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chapter_names_item_list, parent,false)
        return ChapterViewHolder(view)
    }



    override fun onBindViewHolder(holder: ChapterViewHolder, position: Int) {
        val cahpterList = ChapterList[position]
        holder.SuraNameTv.text = cahpterList.chapterName
        holder.AyatNumberTv.text = cahpterList.numberOfAyatt.toString()
        if (onSuraClick !=null){
            holder.itemView.setOnClickListener {
                onSuraClick!!.onItemSuraClick(ChapterList,position)
            }
        }



    }

    override fun getItemCount(): Int  = ChapterList.size
    class ChapterViewHolder(itemView: View  ): RecyclerView.ViewHolder(itemView) {
        val SuraNameTv: TextView = itemView.findViewById(R.id.SuraNameTv)
        val AyatNumberTv: TextView = itemView.findViewById(R.id.AyaNumberTv)
    }

    var onSuraClick: OnItemClickListener? = null
    interface OnItemClickListener{
        fun onItemSuraClick(quranItemList: List<QuranItem>, index: Int )
    }


}