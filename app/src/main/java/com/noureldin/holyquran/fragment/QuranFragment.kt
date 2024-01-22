package com.noureldin.holyquran.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.noureldin.holyquran.adapters.ChaptersRecyclerAdapter
import com.noureldin.holyquran.data.Constant
import com.noureldin.holyquran.data.QuranItem
import com.noureldin.holyquran.databinding.ActivitySuraDetailsBinding
import com.noureldin.holyquran.databinding.FragmentQuranBinding
import com.noureldin.holyquran.ui.SuraDetailsActivity


class QuranFragment : Fragment() {
    private lateinit var binding: FragmentQuranBinding
    private lateinit var chapterAdapter: ChaptersRecyclerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuranBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initChaptersRecycler()

    }


    private fun initChaptersRecycler() {
        val chapters = Constant.CHAPTERS_NAMES.mapIndexed { index, chapterName ->
            QuranItem(chapterName, Constant.CHAPTERS_AYAT[index])
        }
        chapterAdapter = ChaptersRecyclerAdapter(chapters)
        chapterAdapter.onSuraClick = object :ChaptersRecyclerAdapter.OnItemClickListener{
            override fun onItemSuraClick(quranItemList: List<QuranItem>, index: Int) {
                val intent = Intent(activity,SuraDetailsActivity::class.java)

                intent.putExtra(Constant.FILE_NAME,"${index+1}.txt")
                intent.putExtra(Constant.SURA_NAME, quranItemList[index].chapterName)
                intent.putExtra(Constant.AyatCount,quranItemList[index].numberOfAyatt)

                startActivity(intent)
            }
        }
       binding.ChapterRecycleView.adapter = chapterAdapter
    }




}

