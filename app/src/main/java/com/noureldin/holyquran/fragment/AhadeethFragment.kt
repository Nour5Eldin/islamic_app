package com.noureldin.holyquran.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.noureldin.holyquran.adapters.AhadeethRecyclerAdapter
import com.noureldin.holyquran.data.Constant
import com.noureldin.holyquran.databinding.FragmentAhadeethBinding
import com.noureldin.holyquran.model.HadeethModel
import com.noureldin.holyquran.ui.AhadeethDetailsActivity
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader


class AhadeethFragment : Fragment() {

    private lateinit var binding: FragmentAhadeethBinding
    private var ahadeeth = mutableListOf<HadeethModel>()
    lateinit var ahadeethRecyclerAdapter: AhadeethRecyclerAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAhadeethBinding.inflate(inflater, container,false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        readAhadeeth()
        initRecyclerView()

    }

    private fun initRecyclerView() {
        ahadeethRecyclerAdapter = AhadeethRecyclerAdapter(ahadeeth)
        ahadeethRecyclerAdapter.onAhadeethClick = object : AhadeethRecyclerAdapter.OnItemAhadeethClickListener{
            override fun onItemAhadeethClick(hadeeth: HadeethModel, index: Int) {
                val intent = Intent(activity,AhadeethDetailsActivity::class.java)
                intent.putExtra(Constant.HADEETH,hadeeth)
                startActivity(intent)
            }

        }
        binding.hadithRecycleView.adapter = ahadeethRecyclerAdapter

    }

    private fun readAhadeeth(){
         try {
             val inputStream = requireActivity().assets.open("ahadeeth.txt")
             val reader= BufferedReader(InputStreamReader(inputStream))
             val hadeethFileContent = reader.readText()
             val ahdethList:List<String> = hadeethFileContent.split("#")
             for (hadeeth:String in ahdethList){
                val singleHadeethWithSpaces= hadeeth.trim()
                 val singleHadeethLines: MutableList<String> = singleHadeethWithSpaces.split("\n").toMutableList()
                val ahadeethTitles= singleHadeethLines[0]
                val content= singleHadeethLines.joinToString(separator = " ") {
                     return@joinToString it
                 }

                 ahadeeth.add(HadeethModel(title = ahadeethTitles , content = content ))

             }
             inputStream.close()
         }catch (e: IOException){
             e.printStackTrace()

         }




     }

}