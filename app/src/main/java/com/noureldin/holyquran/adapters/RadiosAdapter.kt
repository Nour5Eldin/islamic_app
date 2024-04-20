package com.noureldin.holyquran.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.noureldin.holyquran.R
import com.noureldin.holyquran.databinding.ItemRadioBinding
import com.noureldin.holyquran.model.Radio

class RadioAdapter : RecyclerView.Adapter<RadioAdapter.MyViewHolder>(){
    private var radiosList:MutableList<Radio>?= mutableListOf()
    fun setRadiosList(radiosList:MutableList<Radio>){
        this.radiosList =radiosList
        notifyDataSetChanged()
    }
    class MyViewHolder(val binding:ItemRadioBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(radio: Radio){
            binding.QuranRadio.text = radio.name

            if (radio.isPlayed)
                binding.iconPlay.setImageResource(R.drawable.icon_pause)
            else
                binding.iconPlay.setImageResource(R.drawable.icon_play)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemRadioBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int = radiosList?.size ?: 0

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.QuranRadio.text = radiosList!![position].name
        holder.bind(radiosList!![position])

        if (OnPlayPauseClickListener!=null) {
            holder.binding.iconPlay.setOnClickListener {

                OnPlayPauseClickListener?.onClick(position,radiosList!![position])

            }
        }

    }

    fun changeRadioChannelStatus(position: Int,isPlayed: Boolean) {


        radiosList?.get(position)?.isPlayed = isPlayed
        notifyItemChanged(position)
    }
    fun changeRadioChannelStatus(isPlayed: Boolean,id: Int) {

        val position = radiosList?.indexOf( radiosList?.find { it.id==id })?:0
        radiosList?.find { it.id==id }?.isPlayed=isPlayed
        notifyItemChanged(position)
    }



    var OnPlayPauseClickListener:OnItemClickListener?=null
}

fun interface OnItemClickListener{
    fun onClick(position: Int,radio: Radio)
}